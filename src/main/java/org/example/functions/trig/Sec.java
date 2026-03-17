package org.example.functions.log;

import org.example.functions.log.Cos;
import org.example.interfaces.BaseTrigFunction;
import org.example.validation.Validator;

public class Sec implements BaseTrigFunction {

    private final Validator validator = new Validator();

    private Cos cos;

    public Sec(Cos cos) {
        this.cos = cos;
    }

    @Override
    public double calculate(double x, double precision) {
        validator.validateSec(x, precision);
        return 1 / cos.calculate(x, precision);
    }

    @Override
    public double etalon(double x) {
        return 1 / Math.cos(x);
    }
}
