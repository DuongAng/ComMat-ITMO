package functions;

import java.util.Collections;
import java.util.List;

// y = 4x^3 - 5x^2 + 6x -7
public class Function1 extends Function {

    public Function1() {
        super(List.of(new double[] {Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY}), Collections.emptyList());
    }

    public double compute(double x) {
        return 4*Math.pow(x, 3) - 5 * Math.pow(x, 2) + 6 * x - 7;
    }

    @Override
    public double computeAntiderivative(double x) {
        return Math.pow(x, 4) - 5/3 * Math.pow(x, 3) + 3 * Math.pow(x, 2) - 7 * x;
    }

    @Override
    public String toString() {
        return "y = 4x^3 - 5x^2 + 6x -7";
    }
}
