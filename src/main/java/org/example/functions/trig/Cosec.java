package org.example.functions.trig;

import org.example.base_functions.Sin;
import org.example.interfaces.BaseTrigFunction;
import org.example.validation.Validator;

public class Cosec implements BaseTrigFunction {

    private final Validator validator = new Validator();
    private Sin sin;

    public Cosec(Sin sin) {
        this.sin = sin;
    }

    @Override
    public double calculate(double x, double precision) {
        validator.validateCosec(x, precision);
        return 1 / sin.calculate(x, precision);
    }

    @Override
    public double etalon(double x) {
        return 1 / Math.sin(x);
    }

}
