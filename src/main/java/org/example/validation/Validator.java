package org.example.validation;

public class Validator {
    public boolean validateTrig(double x, double precision) {
        if (precision <= 0 || precision >= 1) {
            throw new IllegalArgumentException("Значения погрешности должны быть в пределе (0, 1)");
        }
        return true;
    }

    public boolean validateLog(double x, double precision, double osn) {
        if (precision <= 0 || precision >= 1) {
            throw new IllegalArgumentException("Значения погрешности должны быть в пределе (0, 1)");
        }
        if (x <= 0) {
            throw new IllegalArgumentException("Аргумент должен быть положительным.");
        }
        if (osn <= 0 || osn == 1) {
            throw new IllegalArgumentException("Основание логарифма должно быть положительным и не равным единице.");
        }
        return true;
    }

    public boolean validateCosec(double x, double precision) {
        validateTrig(x, precision);
        if (Math.abs(Math.sin(x)) < precision) {
            throw new ArithmeticException("Значение синуса не должно быть равно нулю.");
        }
        return true;
    }

    public boolean validateSec(double x, double precision) {
        validateTrig(x, precision);
        if (Math.abs(Math.cos(x)) < precision) {
            throw new ArithmeticException("Значение косинуса не должно быть равно нулю.");
        }
        return true;
    }

    public boolean validateTan(double x, double precision) {
        validateSec(x, precision);
        return true;
    }
}
