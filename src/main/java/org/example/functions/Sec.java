package org.example.functions;

import org.example.interfaces.BaseFunction;
import org.example.validation.Validator;

public class Sec implements BaseFunction {

    private final Validator validator = new Validator();

    private Cos cos;

    public Sec(Cos cos) {
        this.cos = cos;
    }

    @Override
    public double calculate(double x, double precision) {
        validator.validate_data(x, precision);
        return 1 / cos.calculate(x, precision);
    }

    @Override
    public double etalon(double x) {
        return 1 / Math.cos(x);
    }
}
