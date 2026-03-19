package org.example.interfaces;

public interface BaseLogFunction {

    double calculate(double x, double precision, double osn);

    double etalon(double x, double osn);

    default double calculate(double x, double precision) {
        return calculate(x, precision, Math.E);
    }

    default double etalon(double x) {
        return etalon(x, Math.E);
    }
}
