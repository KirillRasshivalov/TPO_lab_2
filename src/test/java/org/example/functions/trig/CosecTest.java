package org.example.functions.trig;

import org.example.base_functions.Sin;
import org.example.util.CsvMockUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CosecTest {

    private static final double DELTA = 1e-4;

    private static final double HIGH_PRECISION = 1e-10;

    private static final double PI = Math.PI;

    private Cosec cosecCalculator;

    private static final double LOW_LIMIT = -100;

    private static final double HIGH_LIMIT = 100;

    private static final double STEP = 0.25;

    @BeforeEach
    void setUp() {
        this.cosecCalculator = new Cosec(new Sin());
    }

    @Test
    @DisplayName("Тест с замоканным синусом.")
    void testSinMock() throws Exception {
        Sin sinMock = CsvMockUtil.mockFromCsv("sinValues", Sin.class);
        Sin sin = new Sin();
        Cosec cosecMock = new Cosec(sinMock);
        Cosec cosec = new Cosec(sin);
        for (double x = LOW_LIMIT; x <= HIGH_LIMIT; x += STEP) {
            try {
                cosecMock.calculate(x, HIGH_PRECISION);
                assertEquals(cosecMock.calculate(x, HIGH_PRECISION), cosec.calculate(x, HIGH_PRECISION), DELTA);
            } catch (Exception e) {
                assertEquals("Значение синуса не должно быть равно нулю.", e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Тест с замоканным синусом и сравнением с эталоном.")
    void testSinMockEtalon() throws Exception {
        Sin sinMock = CsvMockUtil.mockFromCsv("sinValues", Sin.class);
        Sin sin = new Sin();
        Cosec cosecMock = new Cosec(sinMock);
        Cosec cosec = new Cosec(sin);
        for (double x = LOW_LIMIT; x <= HIGH_LIMIT; x += STEP) {
            try {
                cosecMock.calculate(x, HIGH_PRECISION);
                assertEquals(cosecMock.calculate(x, HIGH_PRECISION), cosec.etalon(x), DELTA);
            } catch (Exception e) {
                assertEquals("Значение синуса не должно быть равно нулю.", e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Проверка работаспособности на значениях синуса из таблицы.")
    void testOnTableSin() {
        assertEquals(1, cosecCalculator.calculate(PI / 2, HIGH_PRECISION), DELTA);
        assertEquals(-1, cosecCalculator.calculate((PI * 3) / 2, HIGH_PRECISION), DELTA);
    }

    @Test
    @DisplayName("Проверка достаточности предыдущих тестов с помощью периода.")
    void testProvePrev() {
        assertEquals(cosecCalculator.calculate(PI / 2, HIGH_PRECISION), cosecCalculator.calculate(PI / 2 + 2 * PI, HIGH_PRECISION), DELTA);
        assertEquals(cosecCalculator.calculate(PI / 2, HIGH_PRECISION), cosecCalculator.calculate(PI / 2 - 2 * PI, HIGH_PRECISION), DELTA);
        assertEquals(cosecCalculator.calculate((PI * 3) / 2, HIGH_PRECISION), cosecCalculator.calculate((PI * 3) / 2 + 2 * PI, HIGH_PRECISION), DELTA);
        assertEquals(cosecCalculator.calculate((PI * 3) / 2, HIGH_PRECISION), cosecCalculator.calculate((PI * 3) / 2 - 2 * PI, HIGH_PRECISION), DELTA);
    }

    @Test
    @DisplayName("Проверка точек выходящих за ОДЗ.")
    void testIncorrectValues() {
        Throwable error =  assertThrows(ArithmeticException.class, () -> {cosecCalculator.calculate(0, HIGH_PRECISION);});
        assertThrows(ArithmeticException.class, () -> {
            cosecCalculator.calculate(0, HIGH_PRECISION);
        });
        assertThrows(ArithmeticException.class, () -> {
            cosecCalculator.calculate(PI, HIGH_PRECISION);
        });
        assertEquals("Значение синуса не должно быть равно нулю.", error.getMessage());
    }

    @Test
    @DisplayName("Проверка нечетности функции.")
    void testOddFunction() {
        double x = PI / 3;
        double cosec = cosecCalculator.calculate(x, HIGH_PRECISION);
        double cosecMinusX = cosecCalculator.calculate(-x, HIGH_PRECISION);
        assertEquals(cosecMinusX, -cosec, DELTA);
    }

    @Test
    @DisplayName("Сравнение с оргинальным косекансом.")
    void testOriginal() {
        double x1 = PI / 4;
        double x2 = 12;
        double x3 = 2 * PI / 6;
        assertEquals(cosecCalculator.etalon(x1), cosecCalculator.calculate(x1, HIGH_PRECISION), DELTA);
        assertEquals(cosecCalculator.etalon(x2), cosecCalculator.calculate(x2, HIGH_PRECISION), DELTA);
        assertEquals(cosecCalculator.etalon(x3), cosecCalculator.calculate(x3, HIGH_PRECISION), DELTA);
    }
}
