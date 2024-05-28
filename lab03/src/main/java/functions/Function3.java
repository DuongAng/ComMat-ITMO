package functions;

import java.util.Collections;
import java.util.List;

// y = 2x
public class Function3 extends Function {

    public Function3() {
        super(List.of(new double[] {Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY}), Collections.emptyList());
    }

    @Override
    public double compute(double x) {
        return 2*x;
    }

    @Override
    public double computeAntiderivative(double x) {
        return Math.pow(x, 2);
    }

    @Override
    public String toString() {
        return "y = 2x";
    }
}
