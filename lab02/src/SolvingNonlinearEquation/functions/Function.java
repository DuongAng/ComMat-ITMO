package SolvingNonlinearEquation.functions;

public abstract class Function {

    private final Solution[] solutions;

    public Function(Solution[] solutions) {
        this.solutions = solutions;
    }

    public abstract double compute(double argument);

    public abstract double computeDerivative(double argument);

    public abstract double computeSecondDerivative(double argument);

    public double computeEquivalent(double argument, double left, double right) {
        double lambda;

        double dLeft = computeDerivative(left);
        double dRight = computeDerivative(right);

        if (Math.abs(dLeft) > Math.abs(dRight))
            lambda = -1 / Math.abs(dLeft);
        else
            lambda = -1 / Math.abs(dRight);

        return argument + lambda * compute(argument);
    }

    public boolean checkIsolationInterval(double left, double right) {

        if (left >= right) {
            System.out.println("The left border must be before the right border and not equal to it.");
            return false;
        }

        for (Solution solution : solutions) {
            if (solution.isHasLeft() && solution.isHasRight()) {
                if (solution.getLeft() < left && left < solution.getValue() && solution.getRight() > right && right > solution.getValue() && computeDerivative(left) * computeDerivative(right) > 0)
                    return true;
                break;
            }
            if (solution.isHasLeft() && solution.getLeft() < left && left < solution.getValue() && right > solution.getValue())
                return true;
            if (solution.isHasRight() && solution.getRight() > right && right > solution.getValue() && left < solution.getValue())
                return true;
            if (!solution.isHasLeft() && !solution.isHasRight() && left <= solution.getValue() && right >= solution.getValue())
                return true;
        }

        System.out.println("""
                The root isolation interval was entered incorrectly.
                Check that:
                    1) The function at the ends of the entered interval takes values of different signs
                    2) Derivative of a function of the same sign over the entire entered interval
                """);

        return false;
    }

    public Solution[] getIsolationIntervals() {
        return solutions;
    }

}
