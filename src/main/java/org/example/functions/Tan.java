package org.example.functions;

import org.example.base_functions.Sin;
import org.example.interfaces.BaseTrigFunction;
import org.example.validation.Validator;

public class Tan implements BaseTrigFunction {

    private final Validator validator = new Validator();

    private Sin sin;

    private Cos cos;

    public Tan(Sin sin, Cos cos) {
        this.sin = sin;
        this.cos = cos;
    }

    @Override
    public double calculate(double x, double precision) {
        validator.validateTrig(x, precision);
        return sin.calculate(x, precision) / cos.calculate(x, precision);
    }

    @Override
    public double etalon(double x) {
        return Math.tan(x);
    }
}
