package org.example.functions.log;

import org.example.base_functions.Ln;
import org.example.util.CsvMockUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogTest {

    private static final double DELTA = 1e-4;

    private static final double HIGH_PRECISION = 1e-10;

    private static final double LOW_LIMIT = -100;

    private static final double HIGH_LIMIT = 100;

    private static final double STEP = 0.25;

    private static final double BAZE_OSN = 2.0;

    private Log logCalculator;

    @BeforeEach
    void setUp() {
        this.logCalculator = new Log(new Ln());
    }

    @Test
    @DisplayName("Тест с замоканным натуральным логарифмом.")
    void testLnMock() throws Exception {
        Ln lnMock = CsvMockUtil.mockFromCsv("lnValues", Ln.class);
        Log logMock = new Log(lnMock);
        Ln ln = new Ln();
        Log log = new Log(ln);
        for (double x = LOW_LIMIT; x <= HIGH_LIMIT; x += STEP) {
            try {
                log.calculate(x, HIGH_PRECISION, BAZE_OSN);
                assertEquals(logMock.calculate(x, HIGH_PRECISION, BAZE_OSN), log.calculate(x, HIGH_PRECISION, BAZE_OSN), DELTA, ((Double) x).toString());
            } catch (Exception e) {
                assertEquals("Аргумент должен быть положительным.", e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Тест с замоканным натуральным логарифмом и сравнение с эталоном.")
    void testLnMockEtalon() throws Exception {
        Ln lnMock = CsvMockUtil.mockFromCsv("lnValues", Ln.class);
        Log logMock = new Log(lnMock);
        Ln ln = new Ln();
        Log log = new Log(ln);
        for (double x = LOW_LIMIT; x <= HIGH_LIMIT; x += STEP) {
            try {
                log.calculate(x, HIGH_PRECISION, BAZE_OSN);
                assertEquals(logMock.calculate(x, HIGH_PRECISION, BAZE_OSN), log.etalon(x, BAZE_OSN), DELTA, ((Double) x).toString());
            } catch (Exception e) {
                assertEquals("Аргумент должен быть положительным.", e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Проверка базовых значений.")
    void testBaseValues() {
        double base = 2.0;
        assertEquals(1.0, logCalculator.calculate(2.0, HIGH_PRECISION, base), DELTA);
        assertEquals(2.0, logCalculator.calculate(4.0, HIGH_PRECISION, base), DELTA);
        assertEquals(3.0, logCalculator.calculate(8.0, HIGH_PRECISION, base), DELTA);
        assertEquals(4.0, logCalculator.calculate(16.0, HIGH_PRECISION, base), DELTA);

        base = 10.0;
        assertEquals(1.0, logCalculator.calculate(10.0, HIGH_PRECISION, base), DELTA);
        assertEquals(2.0, logCalculator.calculate(100.0, HIGH_PRECISION, base), DELTA);
        assertEquals(3.0, logCalculator.calculate(1000.0, HIGH_PRECISION, base), DELTA);

        base = Math.E;
        double e = Math.E;
        assertEquals(1.0, logCalculator.calculate(e, HIGH_PRECISION, base), DELTA);
        assertEquals(2.0, logCalculator.calculate(e * e, HIGH_PRECISION, base), DELTA);
        assertEquals(-1.0, logCalculator.calculate(1.0 / e, HIGH_PRECISION, base), DELTA);
    }


    @Test
    @DisplayName("Проверка loga(1) = 0 для разных оснований")
    void testLogOfOne() {
        double[] bases = {2.0, 3.0, 5.0, 10.0, Math.E};
        for (double base : bases) {
            assertEquals(0.0, logCalculator.calculate(1.0, HIGH_PRECISION, base), DELTA);
        }
    }

    @Test
    @DisplayName("Проверка loga(a) = 1 для разных оснований")
    void testLogOfBase() {
        double[] bases = {2.0, 3.0, 5.0, 10.0, Math.E};
        for (double base : bases) {
            assertEquals(1.0, logCalculator.calculate(base, HIGH_PRECISION, base), DELTA);
        }
    }

    @ParameterizedTest
    @DisplayName("Проверка log2 для дробных значений")
    @CsvSource({
            "0.5, -1.0",
            "0.25, -2.0",
            "0.125, -3.0",
            "1.5, 0.5849625007211562",
            "3.0, 1.5849625007211563"
    })
    void testFractionalValues(double x, double expected) {
        assertEquals(expected, logCalculator.calculate(x, HIGH_PRECISION, 2), DELTA);
    }

    @Test
    @DisplayName("Проверка свойства логарифма: loga(x*y) = loga(x) + loga(y)")
    void testLogarithmProductProperty() {
        double base = 3.0;
        double x = 5.0;
        double y = 7.0;
        double logProduct = logCalculator.calculate(x * y, HIGH_PRECISION, base);
        double sumLogs = logCalculator.calculate(x, HIGH_PRECISION, base) +
                logCalculator.calculate(y, HIGH_PRECISION, base);
        assertEquals(logProduct, sumLogs, DELTA);
    }

    @Test
    @DisplayName("Проверка свойства логарифма: loga(x/y) = loga(x) - loga(y)")
    void testLogarithmQuotientProperty() {
        double base = 4.0;
        double x = 16.0;
        double y = 4.0;
        double logQuotient = logCalculator.calculate(x / y, HIGH_PRECISION, base);
        double diffLogs = logCalculator.calculate(x, HIGH_PRECISION, base) -
                logCalculator.calculate(y, HIGH_PRECISION, base);
        assertEquals(logQuotient, diffLogs, DELTA);
    }

    @Test
    @DisplayName("Проверка свойства логарифма: loga(x^p) = p * loga(x)")
    void testLogarithmPowerProperty() {
        double base = 5.0;
        double x = 3.0;
        double p = 2.5;
        double logPower = logCalculator.calculate(Math.pow(x, p), HIGH_PRECISION, base);
        double powerLog = p * logCalculator.calculate(x, HIGH_PRECISION, base);
        assertEquals(logPower, powerLog, DELTA * 10);
    }

    @Test
    @DisplayName("Проверка смены основания: loga(x) = logb(x) / logb(a)")
    void testChangeOfBase() {
        double x = 8.0;
        double baseA = 2.0;
        double baseB = Math.E;
        double logA = logCalculator.calculate(x, HIGH_PRECISION, baseA);
        double logB_x = logCalculator.calculate(x, HIGH_PRECISION, baseB);
        double logB_a = logCalculator.calculate(baseA, HIGH_PRECISION, baseB);
        assertEquals(logA, logB_x / logB_a, DELTA);
    }

    @Test
    @DisplayName("Проверка выбрасывания исключения при x <= 0")
    void testInvalidArgumentThrowsException() {
        double[] invalidArgs = {0.0, -1.0, -5.5, -100.0};
        for (double invalidArg : invalidArgs) {
            assertThrows(IllegalArgumentException.class, () -> {
                logCalculator.calculate(invalidArg, HIGH_PRECISION, 2.0);
            }, "Аргумент должен быть положительным.");
        }
    }

    @Test
    @DisplayName("Проверка выбрасывания исключения при недопустимом основании")
    void testInvalidBaseThrowsException() {
        double x = 2.0;
        double[] invalidBases = {0.0, -1.0, 1.0, -5.0};
        for (double invalidBase : invalidBases) {
            assertThrows(IllegalArgumentException.class, () -> {
                logCalculator.calculate(x, HIGH_PRECISION, invalidBase);
            }, "Основание логарифма должно быть положительным и не равным единице.");
        }
    }

    @Test
    @DisplayName("Проверка выбрасывания исключения при недопустимой точности")
    void testInvalidPrecisionThrowsException() {
        double[] invalidPrecisions = {0.0, 1.0, -0.1, 1.5, 2.0};
        for (double invalidPrecision : invalidPrecisions) {
            assertThrows(IllegalArgumentException.class, () -> {
                logCalculator.calculate(2.0, invalidPrecision, 2.0);
            }, "Значения погрешности должны быть в пределе (0, 1)");
        }
    }

    @Test
    @DisplayName("Проверка сравнения с эталонной функцией")
    void testCompareWithEtalon() {
        double[][] testCases = {
                {2.0, 2.0},
                {4.0, 2.0},
                {10.0, 10.0},
                {100.0, 10.0},
                {Math.E, Math.E},
                {8.0, 2.0},
                {0.5, 2.0},
                {3.0, 5.0}
        };
        for (double[] testCase : testCases) {
            double x = testCase[0];
            double base = testCase[1];
            double expected = logCalculator.etalon(x, base);
            double actual = logCalculator.calculate(x, HIGH_PRECISION, base);
            assertEquals(expected, actual, DELTA);
        }
    }

    @Test
    @DisplayName("Проверка монотонности логарифма")
    void testMonotonicity() {
        double base = 2.0;
        double x1 = 1.5;
        double x2 = 2.5;
        double x3 = 3.5;
        double log1 = logCalculator.calculate(x1, HIGH_PRECISION, base);
        double log2 = logCalculator.calculate(x2, HIGH_PRECISION, base);
        double log3 = logCalculator.calculate(x3, HIGH_PRECISION, base);
        assertTrue(log1 < log2);
        assertTrue(log2 < log3);
    }

}
