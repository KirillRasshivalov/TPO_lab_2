package org.example.system;

import org.example.base_functions.Ln;
import org.example.base_functions.Sin;
import org.example.functions.trig.Cos;
import org.example.functions.trig.Cosec;
import org.example.functions.log.Log;
import org.example.functions.trig.Sec;
import org.example.functions.trig.Tan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class SystemRealisationTest {

    private static final double DELTA = 1e-6;
    private static final double HIGH_PRECISION = 1e-7;

    private Sin sin;
    private Cosec cosec;
    private Sec sec;
    private Tan tan;
    private Ln ln;
    private Log log;
    private SystemRealisation system;

    @BeforeEach
    void setUp() {
        this.sin = new Sin();
        this.cosec = new Cosec(sin);
        this.sec = new Sec(new Cos(new Sin()));
        this.tan = new Tan(sin , new Cos(new Sin()));
        this.ln = new Ln();
        this.log = new Log(ln);
        this.system = new SystemRealisation(sin, cosec, sec, tan, ln, log);
    }

    @ParameterizedTest
    @DisplayName("Тестирование тригонометрической ветки (x <= 0)")
    @CsvSource({
            "-0.5",
            "-1.0",
            "-1.5",
            "-2.0",
            "-2.5",
            "-3.0",
            "-3.5",
            "-4.0",
            "-5.0",
            "-10.0"
    })
    void testTrigonometricBranch(double x) {
        double result = system.calculateAnswer(x, HIGH_PRECISION);
        assertFalse(Double.isNaN(result));
        assertFalse(Double.isInfinite(result));
        double sinX = sin.calculate(x, HIGH_PRECISION);
        double cosecX = cosec.calculate(x, HIGH_PRECISION);
        double secX = sec.calculate(x, HIGH_PRECISION);
        double tanX = tan.calculate(x, HIGH_PRECISION);
        double expected = ((((((secX + tanX) * sinX) - secX) * tanX) * secX) * (cosecX - sinX)) + sinX;
        assertEquals(expected, result, DELTA);
    }

    @ParameterizedTest
    @DisplayName("Тестирование логарифмической ветки (x > 0)")
    @CsvSource({
            "0.5",
            "1.0",
            "1.5",
            "2.0",
            "2.5",
            "3.0",
            "3.5",
            "4.0",
            "5.0",
            "10.0"
    })
    void testLogarithmBranch(double x) {
        double result = system.calculateAnswer(x, HIGH_PRECISION);
        double logN = ln.calculate(x, HIGH_PRECISION);
        double log2 = log.calculate(x, HIGH_PRECISION, 2);
        double log3 = log.calculate(x, HIGH_PRECISION, 3);
        double log5 = log.calculate(x, HIGH_PRECISION, 5);
        double expected = ((((logN * logN) + log3) * log5) - (log5 - log5)) /
                ((log5 + (log2 - (log5 * log5))) + log5);
        assertEquals(expected, result, DELTA);
    }

}