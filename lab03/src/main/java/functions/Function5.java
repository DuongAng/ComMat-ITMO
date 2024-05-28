package functions;

import java.util.List;

// y = e^x
public class Function5 extends Function {

    public Function5() {
        super(List.of(new double[] {Double.NEGATIVE_INFINITY, 0d}, new double[] {0d, Double.POSITIVE_INFINITY}), List.of(0d));
    }

    @Override
    public double compute(double x) {
        return Math.exp(x);
    }

    @Override
    public double computeAntiderivative(double x) {
        return Math.exp(x);
    }

    @Override
    public String toString() {
        return "y = e^x";
    }
}
