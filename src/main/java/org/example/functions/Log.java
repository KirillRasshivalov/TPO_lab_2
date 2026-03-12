package org.example.functions;

import org.example.base_functions.Ln;
import org.example.interfaces.BaseLogFunction;
import org.example.validation.Validator;

public class Log implements BaseLogFunction {

    private final Validator validator = new Validator();

    private Ln ln;

    public Log() {
        this.ln = new Ln();
    }

    @Override
    public double calculate(double x, double precision, double osn) {
        validator.validateLog(x, precision, osn);
        return ln.calculate(x, precision) / ln.calculate(osn, precision);
    }

    @Override
    public double etalon(double x, double osn) {
        return Math.log(x) / Math.log(osn);
    }
}
