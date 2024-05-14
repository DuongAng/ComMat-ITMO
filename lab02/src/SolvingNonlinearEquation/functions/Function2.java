package SolvingNonlinearEquation.functions;

/*
function: y = x^3 + 2.28x^2 - 1.934x - 3.907
solutions of equation y = 0 and their isolation intervals:
    x1 = -2.41d, [-inf, -1.998d]
    x2 = -1.21d, [-1.998d, 0d]
    x3 = 1.34d, [0d, +inf]
 */
public class Function2 extends Function {

    public Function2() {
        super(new Solution[]{
                new Solution(-2.41d, 0, -1.998d, false, true),
                new Solution(-1.21d, -1.998d, 0d, true, true),
                new Solution(1.34d, 0d, 0, true, false)
        });
    }

    @Override
    public double compute(double argument) {
        return 1 * Math.pow(argument, 3) + 2.28 * Math.pow(argument, 2) - 1.934 * argument - 3.907;
    }

    @Override
    public double computeDerivative(double argument) {
        return  3 * Math.pow(argument, 2) + 2.28 * 2 * argument - 1.934;
    }

    @Override
    public double computeSecondDerivative(double argument) {
        return 6 * argument + 2.28 * 2;
    }

    @Override
    public String toString() {
        return "x^3 + 2.28x^2 - 1.934x - 3.907 = 0";
    }
}
