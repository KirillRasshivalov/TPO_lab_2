package org.example.base_functions;

import org.example.interfaces.BaseTrigFunction;
import org.example.validation.Validator;

public class Sin implements BaseTrigFunction {

    private Validator validator = new Validator();
    @Override
    public double calculate(double x, double precision) {
        validator.validateTrig(x, precision);
        x = x % (2 * Math.PI);
        double sumNew, sumOld, sum;
        int i = 1;
        sum = sumNew = x;
        do {
            sumOld = sumNew;
            i++;
            sum = sum * x * x / i;
            i++;
            sum = sum / i;
            sum = -sum;
            sumNew = sumOld + sum;
        } while (Math.abs(sumNew - sumOld) > precision);
        return sumNew;
    }

    @Override
    public double etalon(double x) {
        return Math.sin(x);
    }
}
