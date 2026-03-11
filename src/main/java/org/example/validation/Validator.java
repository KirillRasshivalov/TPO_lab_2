package org.example.validation;

public class Validator {
    public boolean validate_trig(double x, double precision) {
        if (precision <= 0 || precision >= 1) {
            throw new IllegalArgumentException("Значения погрешности должны быть в пределе (0, 1)");
        }
        return true;
    }

    public boolean validate_log(double x, double precision) {
        if (precision <= 0 || precision >= 1) {
            throw new IllegalArgumentException("Значения погрешности должны быть в пределе (0, 1)");
        }
        if (x < -1 || x > 1) {
            throw new IllegalArgumentException("На таком значение x ряд будет расходиться, x должен быть в переделе от [-1, 1]");
        }
        return true;
    }
}
