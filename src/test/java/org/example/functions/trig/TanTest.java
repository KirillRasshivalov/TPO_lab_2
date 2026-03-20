package org.example.functions.trig;

import org.example.base_functions.Sin;
import org.example.util.CsvMockUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TanTest  {
    private static final double DELTA = 1e-4;

    private static final double HIGH_PRECISION = 1e-10;

    private static final double PI = Math.PI;

    private static final double LOW_LIMIT = -100;

    private static final double HIGH_LIMIT = 100;

    private static final double STEP = 0.25;

    private Tan tanCalculator;

    @BeforeEach
    void setUp() {
        this.tanCalculator = new Tan(new Sin(), new Cos(new Sin()));
    }

    @Test
    @DisplayName("Тест с замоканными данными синуса и косинуса.")
    void testSinCosMock() throws Exception {
        Sin sinMock = CsvMockUtil.mockFromCsv("sinValues", Sin.class);
        Cos cosMock = CsvMockUtil.mockFromCsv("cosValues", Cos.class);
        Sin sin = new Sin();
        Cos cos = new Cos(sin);
        Tan tanMock = new Tan(sinMock, cosMock);
        Tan tan = new Tan(sin, cos);
        for (double x = LOW_LIMIT; x <= HIGH_LIMIT; x += STEP) {
            try {
                tan.calculate(x, HIGH_PRECISION);
                assertEquals(tanMock.calculate(x, HIGH_PRECISION), tan.calculate(x, HIGH_PRECISION), DELTA);
            } catch (Exception e) {
                assertEquals("Значение косинуса не должно быть равно нулю.", e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Тест с замоканными данными синуса и косинуса и сравнение с эталоном.")
    void testSinCosMockEtalon() throws Exception {
        Sin sinMock = CsvMockUtil.mockFromCsv("sinValues", Sin.class);
        Cos cosMock = CsvMockUtil.mockFromCsv("cosValues", Cos.class);
        Sin sin = new Sin();
        Cos cos = new Cos(sin);
        Tan tanMock = new Tan(sinMock, cosMock);
        Tan tan = new Tan(sin, cos);
        for (double x = LOW_LIMIT; x <= HIGH_LIMIT; x += STEP) {
            try {
                tan.calculate(x, HIGH_PRECISION);
                assertEquals(tanMock.calculate(x, HIGH_PRECISION), tan.etalon(x), DELTA);
            } catch (Exception e) {
                assertEquals("Значение косинуса не должно быть равно нулю.", e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Проверка значений из таблицы.")
    void testOnTable() {
        assertEquals(0, tanCalculator.calculate(0, HIGH_PRECISION), DELTA);
        assertEquals(1, tanCalculator.calculate(PI / 4, HIGH_PRECISION), DELTA);
        assertEquals(0, tanCalculator.calculate(PI, HIGH_PRECISION), DELTA);
        assertEquals(0, tanCalculator.calculate(2 * PI, HIGH_PRECISION), DELTA);
    }

    @Test
    @DisplayName("Проверка достаточности предыдущего теста через период тангенса.")
    void testPeriodTan() {
        assertEquals(tanCalculator.calculate(0, HIGH_PRECISION), tanCalculator.calculate(0 - PI, HIGH_PRECISION), DELTA);
        assertEquals(tanCalculator.calculate(0, HIGH_PRECISION), tanCalculator.calculate(0 + PI, HIGH_PRECISION), DELTA);
        assertEquals(tanCalculator.calculate(PI / 4, HIGH_PRECISION), tanCalculator.calculate(PI / 4 - PI, HIGH_PRECISION), DELTA);
        assertEquals(tanCalculator.calculate(PI / 4, HIGH_PRECISION), tanCalculator.calculate(PI / 4 + PI, HIGH_PRECISION), DELTA);
        assertEquals(tanCalculator.calculate(PI, HIGH_PRECISION), tanCalculator.calculate(PI - PI, HIGH_PRECISION), DELTA);
        assertEquals(tanCalculator.calculate(PI, HIGH_PRECISION), tanCalculator.calculate(PI + PI, HIGH_PRECISION), DELTA);
        assertEquals(tanCalculator.calculate(2 * PI, HIGH_PRECISION), tanCalculator.calculate(2 * PI - PI, HIGH_PRECISION), DELTA);
        assertEquals(tanCalculator.calculate(2 * PI, HIGH_PRECISION), tanCalculator.calculate(2 * PI + PI, HIGH_PRECISION), DELTA);
    }

    @Test
    @DisplayName("Сравнение с эталонным тангесом на некоторых значениях.")
    void testCompWithEt() {
        double x1 = PI / 6;
        double x2 = 2 * PI / 8;
        double x3 = PI * 4 / 15;
        assertEquals(tanCalculator.etalon(x1), tanCalculator.calculate(x1, HIGH_PRECISION), DELTA);
        assertEquals(tanCalculator.etalon(x2), tanCalculator.calculate(x2, HIGH_PRECISION), DELTA);
        assertEquals(tanCalculator.etalon(x3), tanCalculator.calculate(x3, HIGH_PRECISION), DELTA);
    }

    @Test
    @DisplayName("Проверка точек выходящих за ОДЗ.")
    void testIncorrectValues() {
        Throwable error =  assertThrows(ArithmeticException.class, () -> {tanCalculator.calculate(PI / 2, HIGH_PRECISION);});
        assertThrows(ArithmeticException.class, () -> {
            tanCalculator.calculate(PI / 2, HIGH_PRECISION);
        });
        assertThrows(ArithmeticException.class, () -> {
            tanCalculator.calculate((3 * PI) / 2, HIGH_PRECISION);
        });
        assertEquals("Значение косинуса не должно быть равно нулю.", error.getMessage());
    }

    @Test
    @DisplayName("Проверка нечетности функции.")
    void testOddFunction() {
        double x = PI / 3;
        double cosec = tanCalculator.calculate(x, HIGH_PRECISION);
        double cosecMinusX = tanCalculator.calculate(-x, HIGH_PRECISION);
        assertEquals(cosecMinusX, -cosec, DELTA);
    }
}
