package SolvingSystemOfNonlinearEquations.functions;

import java.util.ArrayList;
import java.util.List;

// function: y = x
public class Function3 extends Function {

    @Override
    public double compute(double x, double y) {
        return y - 2*x -1;
    }

    @Override
    public List<List<Double>> computePoints() {
        List<Double> xPoints = new ArrayList<>();
        List<Double> yPoints = new ArrayList<>();

        for (double x = -10d; x <= 10d; x += 0.1d) {
            xPoints.add(x);
            yPoints.add(2*x+1);
        }

        return List.of(xPoints, yPoints);
    }

    @Override
    public double computeDerivativeX(double x, double y) {
        return -2;
    }

    @Override
    public double computeDerivativeY(double x, double y) {
        return 1;
    }

    @Override
    public String toString() {
        return "y = 2x + 1";
    }
}
