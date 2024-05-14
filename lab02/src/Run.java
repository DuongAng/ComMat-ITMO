public class Run {

    public static void main(String[] args) {

        System.out.println("""
                To exit the program, type exit.
                Select mode:
                    1) Solution of a nonlinear equation
                    2) Solution of a system of nonlinear equations""");

        InputReader inputReader = new InputReader();
        int index = inputReader.readIndex("Enter mode number: ", "There is no this number.", 2);
        System.out.println(" ");

        switch (index) {
            case 0:
                SolvingNonlinearEquation.Main.main(args);
                break;
            case 1:
                SolvingSystemOfNonlinearEquations.Main.main(args);
        }
    }
}
