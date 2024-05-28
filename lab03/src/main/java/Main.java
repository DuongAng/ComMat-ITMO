import functions.*;
import methods.*;
import util.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static final Function[] functions = {
            new Function1(),
            new Function2(),
            new Function3(),
            new Function4(),
            new Function5()
    };

    private static final Method[] methods = {
            new MethodOfLeftRectangles(),
            new MethodOfRightRectangles(),
            new MethodOfCenterRectangles(),
            new MethodOfTrapezoids(),
            new MethodOfSimpson()
    };

    public static void main(String[] args) {
        int count = 1;
        System.out.println("""
                Program for calculating definite integrals.
                To exit the program, write exit
                """);

        System.out.println("List of available functions:");
        for (Function function : functions) {
            System.out.println("Function " + count + ": " + function);
            count++;
        }

        System.out.println(" ");
        InputReader inputReader = new InputReader();
        Function function = functions[inputReader.readIndex("Enter feature number: ", "There is no function with this number.", functions.length)];

        DomainChecker domainChecker = new DomainChecker();
        double a, b;
        while (true) {
            a = inputReader.readDouble("Enter the left border of the interval: ");
            b = inputReader.readDouble("Enter the right boundary of the interval: ");
            if (a < b) {
                if (domainChecker.checkDomain(function, a, b))
                    break;
                else
                    System.out.println("The specified interval is not within the scope of the function.");
            } else
                System.out.println("The left border must be smaller than the right.");
        }

        ConvergenceChecker convergenceChecker = new ConvergenceChecker();
        List<double[]> integrationIntervals = convergenceChecker.checkConvergence(function, a, b);
        if (integrationIntervals.isEmpty()) {
            System.out.println(" ");
            System.out.println("This integral does not converge.");
            return;
        }

        double accuracy;
        int digitsAfterComma;
        while (true) {
            accuracy = inputReader.readPositiveDouble("Enter precision: ");
            digitsAfterComma = String.valueOf(accuracy - (double)((long) accuracy) + 1).length() - 2;
            if (inputReader.readYesNo("""
                Please note that with high accuracy and a long integration interval, the calculation can take quite a long time.
                Do you want to leave the current precision value?
                (Please enter yes) :-))))
                """))
                break;
        }

        List<String> headers = List.of("Method", "Result", "Number of splits");
        List<List<String>> data = new ArrayList<>();

        List<String> row;
        for (Method method : methods) {
            row = new ArrayList<>();
            double result = 0;
            long partition = 0;
            for (double[] integrationInterval : integrationIntervals) {
                Result res = method.compute(function, integrationInterval[0], integrationInterval[1], accuracy);
                result += res.getResult();
                partition += res.getPartition();
            }
            row.add(method.toString());
            row.add(String.format("%." + digitsAfterComma + "f", result));
            row.add(String.format("%d", partition));
            data.add(row);
        }

        System.out.println(" ");
        TableGenerator tableGenerator = new TableGenerator();
        StringBuilder builder = new StringBuilder();
        builder
                .append("Function: ").append(function.toString()).append("\n")
                .append("Interval: [").append(a).append("; ").append(b).append("]\n")
                .append("Accuracy: ").append(String.format("%f", accuracy)).append("\n")
                .append(tableGenerator.generate(headers, data));
        System.out.println(builder.toString());
    }

}
