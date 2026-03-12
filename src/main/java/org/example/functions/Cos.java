package org.example.functions;

import org.example.base_functions.Sin;
import org.example.interfaces.BaseTrigFunction;
import org.example.validation.Validator;

public class Cos implements BaseTrigFunction {

    private final static double PI = Math.PI;

    private Sin sin;

    private Validator validator = new Validator();

    public Cos(Sin sin) {
        this.sin = sin;
    }

    @Override
    public double calculate(double x, double precision) {
        validator.validateTrig(x, precision);
        return sin.calculate(PI / 2 - x, precision);
    }

    @Override
    public double etalon(double x) {
        return Math.cos(x);
    }
}
