package SolvingNonlinearEquation.methods;

import SolvingNonlinearEquation.functions.Function;

import java.util.ArrayList;
import java.util.List;

public class Secantmethod implements Method {

    @Override
    public Result compute(Function function, double left, double right, double accuracy, int digitsAfterComma) {
        double x0, f, f1, f2, d, x1, x2=0;

        if (function.compute(left) * function.computeSecondDerivative(left) > 0) {
            x0 = left;
            x1 = x0+0.2;
        }
        else {
            x0 = right;
            x1 = x0-0.2;
        }
        List<String> headers = List.of("№ iterations", "x_k-1", "x_k", "x_k+1", "f(x_k+1)", "|x_k+1-x_k|");
        List<List<String>> data = new ArrayList<>();

        List<String> row;
        int counter = 0;
        while (true) {
            row = new ArrayList<>();
            counter++;
            row.add(String.format("%d", counter));
            row.add(String.format("%." + digitsAfterComma + "f", x0));
            f = function.compute(x0);
            row.add(String.format("%." + digitsAfterComma + "f", x1));
            d = function.computeDerivative(x0);
            f1 = function.compute(x1);
            x2 = x1 - (x1-x0)/(f1-f)*f1;
            row.add(String.format("%." + digitsAfterComma + "f", x2));
            f2 = function.compute(x2);
            row.add(String.format("%." + digitsAfterComma + "f", f2));
            data.add(row);
            row.add(String.format("%." + digitsAfterComma + "f", Math.abs(x2-x1)));
            if (Math.abs(x2-x1) <= accuracy)
                break;
            x0=x1;
            x1=x2;
        }

        return new Result(headers, data);
    }

    @Override
    public String toString() {
        return "Secant method";
    }
}
