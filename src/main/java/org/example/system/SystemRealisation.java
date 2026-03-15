package org.example.system;

import lombok.AllArgsConstructor;
import org.example.base_functions.Ln;
import org.example.base_functions.Sin;
import org.example.functions.log.Cosec;
import org.example.functions.trig.Log;
import org.example.functions.log.Sec;
import org.example.functions.log.Tan;
import org.example.interfaces.BaseSystem;

public class SystemRealisation implements BaseSystem {

    private Sin sin;
    private Cosec cosec;
    private Sec sec;
    private Tan tan;

    private Ln ln;

    private Log log;

    public SystemRealisation(Sin sin, Cosec cosec, Sec sec, Tan tan, Ln ln, Log log) {
        this.sin = sin;
        this.cosec = cosec;
        this.sec = sec;
        this.tan = tan;
        this.ln = ln;
        this.log = log;
    }

    @Override
    public double calculateAnswer(double x, double precision) {
        if (x < 0) {
            double sinX = sin.calculate(x, precision);
            double cosecX = cosec.calculate(x, precision);
            double secX = sec.calculate(x, precision);
            double tanX = tan.calculate(x, precision);
            return ((((((secX + tanX) * sinX) - secX) * tanX) * secX) * (cosecX - sinX)) + sinX;
        } else {
            double logN = ln.calculate(x, precision);
            double log2 = log.calculate(x, precision, 2);
            double log3 = log.calculate(x, precision, 3);
            double log5 = log.calculate(x, precision, 5);
            return ((((Math.pow(logN, 2)) + log3) * log5) - (log5 - log5)) /
                    ((log5 + (log2 - (Math.pow(log5, 2)))) + log5);
        }
    }
}
// (((((((sec(x)+tan(x))⋅sin(x))−sec(x))⋅tan(x))⋅sec(x))⋅(csc(x)−sin(x)))+sin(x))
// ((((ln(x)2)+log3(x))⋅log5(x))−(log5(x)−log5(x))(log5(x)+(log2(x)−(log5(x)2)))+log5(x))