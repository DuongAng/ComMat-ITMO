package angg.approximation;

import angg.Result;
import angg.interfaces.Function;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;

public class Square_function {
    private double eps = 0;
    private double[] x, y, f, e;
    private double[][] function_Table;
    private double sum_x = 0, sum_x2 = 0, sum_x3 = 0, sum_x4 = 0, sum_y = 0, sum_x_y = 0, sum_x2_y = 0;
    private Result result;

    public void init(double[][] function_Table) {
        this.function_Table = function_Table;
        for (int i = 0; i < function_Table.length; i++) {
            sum_x += function_Table[i][0];
            sum_x2 += Math.pow(function_Table[i][0], 2);
            sum_x3 += Math.pow(function_Table[i][0], 3);
            sum_x4 += Math.pow(function_Table[i][0], 4);
            sum_y += function_Table[i][1];
            sum_x_y += function_Table[i][0] * function_Table[i][1];
            sum_x2_y += Math.pow(function_Table[i][0], 2) * function_Table[i][1];
        }
        x = new double[function_Table.length];
        y = new double[function_Table.length];
        f = new double[function_Table.length];
        e = new double[function_Table.length];
    }

    public void solve() {
        double[][] matrix = new double[][]{
                {function_Table.length, sum_x, sum_x2},
                {sum_x, sum_x2, sum_x3},
                {sum_x2, sum_x3, sum_x4}
        };

        double[] constants = new double[]{sum_y, sum_x_y, sum_x2_y};
        DecompositionSolver solver = new LUDecomposition(new Array2DRowRealMatrix(matrix)).getSolver();
        double[] ress = solver.solve(new ArrayRealVector(constants)).toArray();

        Function function = x -> (ress[2] * Math.pow(x, 2) + ress[1] * x + ress[0]);

        result = new Result();
        result.setname_Function("Polynomial of degree 2");
        result.setstr_Function(ress[2] + "x^2 + " + ress[1] + "x + " + ress[0]);

        for (int i = 0; i < function_Table.length; i++) {
            x[i] = function_Table[i][0];
            y[i] = function_Table[i][1];
            f[i] = function.calculate(x[i]);
            e[i] = f[i] - y[i];
            eps += Math.pow(e[i], 2);
        }
        eps = eps / function_Table.length;
        eps = Math.sqrt(eps);
        result.setX(x);
        result.setY(y);
        result.setF(f);
        result.setE(e);
        result.setEps(eps);
        result.setFunction(function);
    }

    public Result getResult() {
        return result;
    }
}
