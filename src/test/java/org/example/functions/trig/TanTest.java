package org.example.functions.trig;

import org.example.base_functions.Sin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TanTest  {
    private static final double DELTA = 1e-8;

    private static final double HIGH_PRECISION = 1e-10;

    private static final double PI = Math.PI;

    private Tan tanCalculator;

    @BeforeEach
    void setUp() {
        this.tanCalculator = new Tan(new Sin(), new Cos(new Sin()));
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
