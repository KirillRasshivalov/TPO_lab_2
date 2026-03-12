package org.example.base_functions;

import org.example.interfaces.BaseLogFunction;
import org.example.validation.Validator;

public class Ln implements BaseLogFunction {

    private final Validator validator = new Validator();

    @Override
    public double calculate(double x, double precision, double osn) {
        validator.validateLog(x, precision, osn);
        double temp = (x - 1) / (x + 1);
        double step = 1;
        double sumPrev = 0, sumCurr = 0;
        do {
            sumPrev = sumCurr;
            sumCurr += (1 / step) * (Math.pow(temp, step));
            step += 2;
        } while (2 * Math.abs(sumCurr - sumPrev) > precision * 0.1);
        return sumCurr * 2;
    }

    @Override
    public double etalon(double x, double osn) {
        return Math.log(x);
    }
}
