package org.example.system;

import lombok.AllArgsConstructor;
import org.example.base_functions.Sin;
import org.example.functions.Cosec;
import org.example.functions.Sec;
import org.example.functions.Tan;
import org.example.interfaces.BaseSystem;

@AllArgsConstructor
public class SystemRealisation implements BaseSystem {

    private Sin sin;
    private Cosec cosec;
    private Sec sec;
    private Tan tan;

    @Override
    public double calculateAnswer(double x, double precision) {
        if (x < 0) {
            double sinX = sin.calculate(x, precision);
            double cosecX = cosec.calculate(x, precision);
            double secX = sec.calculate(x, precision);
            double tanX = tan.calculate(x, precision);
            return ((((((secX + tanX) * sinX) - secX) * tanX) * secX)*(cosecX - sinX)) + sinX;
        }
        return 0.0;
    }
}
// (((((((sec(x)+tan(x))⋅sin(x))−sec(x))⋅tan(x))⋅sec(x))⋅(csc(x)−sin(x)))+sin(x))
