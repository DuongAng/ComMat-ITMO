package angg;

import angg.exceptions.NumberPointException;
import angg.exceptions.ReadDataException;

public class Main {

    public static void main(String[] args) {
        Solver solver =  new Solver();
        try {
            solver.run();
        } catch (NumberPointException | ReadDataException e) {
            System.out.println(e.getMessage());
        }
    }
}
