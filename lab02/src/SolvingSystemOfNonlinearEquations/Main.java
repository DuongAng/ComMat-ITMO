package SolvingSystemOfNonlinearEquations;

import SolvingSystemOfNonlinearEquations.functions.*;
import SolvingSystemOfNonlinearEquations.methods.Method;
import SolvingSystemOfNonlinearEquations.methods.NewtonsMethod;
import SolvingSystemOfNonlinearEquations.systems.SystemOfNonlinearEquations;

public class Main {

    private static final SystemOfNonlinearEquations[] systems = new SystemOfNonlinearEquations[] {
            new SystemOfNonlinearEquations(
                    new Function[]{
                            new Function1(),
                            new Function2()
                    }
            ),
            new SystemOfNonlinearEquations(
                    new Function[]{
                            new Function3(),
                            new Function4()
                    }
            )
    };

    public static void main(String[] args) {
        InputReader inputReader = new InputReader();

        System.out.println("List of available systems of nonlinear equations:");

        for (int i = 0; i < systems.length; i++)
            System.out.println((i + 1) + ")\n" + systems[i].toString());

        int systemIndex = inputReader.readIndex("Enter system number: ", "There is no this number.", systems.length);
        SystemOfNonlinearEquations system = systems[systemIndex];
        GraphPrinter graphPrinter = new GraphPrinter(systems[systemIndex]);
        graphPrinter.setVisible(true);

        double x0 = inputReader.readDouble("Enter zero approximation x0: ");
        double y0 = inputReader.readDouble("Enter zero approximation y0: ");
        double accuracy = inputReader.readPositiveDouble("Enter precision: ");
        int digitsAfterComma = inputReader.readPositiveInt("Enter the number of decimal places: ");

        Method method = new NewtonsMethod();

        TableGenerator tableGenerator = new TableGenerator();
        String resultTable = tableGenerator.generate(method.compute(system, x0, y0, accuracy, digitsAfterComma));
        String result = "System:\n" + system.toString() + "\nMethod: " + method.toString() + "\n" + resultTable;

        OutputWriter outputWriter = new OutputWriter();
        outputWriter.output(result);

        System.exit(0);
    }
}
