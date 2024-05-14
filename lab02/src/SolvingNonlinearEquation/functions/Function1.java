package SolvingNonlinearEquation.functions;


/*
function: y = x^2 - x - 8
solutions of equation y = 0 and their isolation intervals:
    x1 = -2.372, [-inf, -1]
    x2 = 3.372, [1, +inf]
 */
public class Function1 extends Function {

    public Function1() {
        super(new Solution[]{
                new Solution(-2.372, 0d, -0.577d, false, true),
                new Solution(3.372, 1d, 0d, true, false),

        });
    }

    @Override
    public double compute(double argument) {
        return Math.pow(argument, 2) - argument - 8;
    }

    @Override
    public double computeDerivative(double argument) {
        return 2 * Math.pow(argument, 1) - 1;
    }

    @Override
    public double computeSecondDerivative(double argument) {
        return 2 ;
    }

    @Override
    public String toString() {
        return "x^2 - x - 8 = 0";
    }

}
