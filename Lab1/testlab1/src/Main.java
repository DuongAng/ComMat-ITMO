public class Main {

    public static void main(String[] args) {
        System.out.println("Use 'exit' to exit the program if an error occurs.");

        InputReader inputReader = new InputReader();
        Calculation calculation = new Calculation();

        Data data = null;
        switch (args.length) {
            case 0:
                data = inputReader.readFromTerminal();
                break;
            case 1:
                data = inputReader.readFromFile(args[0]);
                break;
            default:
                System.out.println("Path to the file.");
                System.exit(1);
        }

        if (calculation.getDeterminant(data.getA()) == 0d) {
            System.out.println("""
                    Matrix A has determinant 0 so the program does not work. 
                    Please check the matrix again and restart the program.""");
            System.exit(1);
        }

        calculation.toConvergence(data);


        calculation.iterate(data);

    }
}

