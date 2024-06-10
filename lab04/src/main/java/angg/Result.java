package angg;

import angg.interfaces.Function;

import java.util.Arrays;
import java.util.Objects;

public class Result {
    private String name_Function;
    private String str_Function;
    private Function function;
    private double r;
    private double eps;
    private double[] x,y,f,e;

    public void setname_Function(String name_Function) {
        this.name_Function = name_Function;
    }


    public void setstr_Function(String str_Function) {
        this.str_Function = str_Function;
    }

    public void setEps(double eps) {
        this.eps = eps;
    }

    public void setY(double[] y) {
        this.y = y;
    }

    public void setX(double[] x) {
        this.x = x;
    }

    public void setE(double[] e) {
        this.e = e;
    }

    public void setF(double[] f) {
        this.f = f;
    }

    public void setR(double r) {
        this.r = r;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public String getstr_Function() {
        return str_Function;
    }

    public String getname_Function() {
        return name_Function;
    }


    public double getEps() {
        return eps;
    }

    public double[] getE() {
        return e;
    }

    public double[] getF() {
        return f;
    }

    public double[] getX() {
        return x;
    }

    public double[] getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public Function getFunction() {
        return function;
    }

    @Override
    public String toString() {
        if(Objects.equals(name_Function, "Linear")){
            return
                    "Function : " + name_Function + "\n" +
                            "Pearson coefficient: " + r + "\n" +
                            "F(x) = " + str_Function + "\n" +
                            "Standard deviation: " + eps + "\n" +
                            "x = " + Arrays.toString(x) + "\n" +
                            "y = " + Arrays.toString(y) + "\n" +
                            "f = " + Arrays.toString(f) + "\n" +
                            "e = " + Arrays.toString(e)+"\n";
        }
        else {
            return
                    "Function : " + name_Function + "\n" +
                            "F(x) = " + str_Function + "\n" +
                            "Standard deviation: " + eps + "\n" +
                            "x = " + Arrays.toString(x) + "\n" +
                            "y = " + Arrays.toString(y) + "\n" +
                            "f = " + Arrays.toString(f) + "\n" +
                            "e = " + Arrays.toString(e)+"\n";
        }
    }
}
