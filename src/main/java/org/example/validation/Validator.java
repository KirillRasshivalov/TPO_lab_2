package org.example.validation;

import lombok.Getter;

public class Validator {
    public boolean validate_data(double x, double precision) {
        if (precision <= 0 || precision >= 1) {
            throw new IllegalArgumentException("Значения погрешности должны быть в пределе (0, 1)");
        }
        return true;
    }
}
