package org.example.functions;

import org.example.base_functions.Sin;
import org.example.interfaces.BaseFunction;
import org.example.validation.Validator;

public class Cosec implements BaseFunction {

    private final Validator validator = new Validator();
    private Sin sin;

    public Cosec(Sin sin) {
        this.sin = sin;
    }

    @Override
    public double calculate(double x, double precision) {
        validator.validate_data(x,precision);
        return 1 / sin.calculate(x, precision);
    }

    @Override
    public double etalon(double x) {
        return Math.cosh(x);
    }

}
