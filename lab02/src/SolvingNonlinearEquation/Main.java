package SolvingNonlinearEquation;

import SolvingNonlinearEquation.functions.Function;
import SolvingNonlinearEquation.functions.Function1;
import SolvingNonlinearEquation.functions.Function2;
import SolvingNonlinearEquation.functions.Function3;
import SolvingNonlinearEquation.methods.*;

public class Main {

    private static final Function[] functions = {
            new Function1(),
            new Function2(),
            new Function3()
    };

    private static final Method[] methods = {
            new HalvesMethod(),
            new Secantmethod(),
            new SimpleIterationMethod()
    };

    public static void main(String[] args) {
        InputReader inputReader = new InputReader();

        System.out.println("List of available functions:");

        for (int i = 0; i < functions.length; i++)
            System.out.println((i + 1) + ") " + functions[i].toString());

        int functionIndex = inputReader.readIndex("Enter feature number: ", "There is no function with this number.", functions.length);

        Function function = functions[functionIndex];

        GraphPrinter graphPrinter = new GraphPrinter(function);
        graphPrinter.setVisible(true);

        double left;
        double right;
        while (true) {
            left = inputReader.readDouble("Enter the left border of the interval: ");
            right = inputReader.readDouble("Enter the right boundary of the interval: ");
            if (function.checkIsolationInterval(left, right))
                break;
        }

        System.out.println("List of available methods: ");
        for (int i = 0; i < methods.length; i++)
            System.out.println((i + 1) + ") " + methods[i].toString());

        int methodIndex = inputReader.readIndex("Enter method number: ", "There is no method with this number.", methods.length);
        Method method = methods[methodIndex];

        double accuracy = inputReader.readPositiveDouble("Enter precision: ");
        int digitsAfterComma = inputReader.readPositiveInt("Enter the number of decimal places: ");

        Result result = method.compute(function, left, right, accuracy, digitsAfterComma);
        TableGenerator generator = new TableGenerator();
        String tableResult = generator.generate(result);
        String stringBuilder = "The equation: " +
                function.toString() +
                "\n" +
                "Method: " +
                method.toString() +
                "\n" +
                tableResult;
        OutputWriter outputWriter = new OutputWriter();
        outputWriter.output(stringBuilder);

        System.exit(0);
    }
}
