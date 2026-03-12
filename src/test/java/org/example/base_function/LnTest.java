package org.example.base_function;

import org.example.base_functions.Ln;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LnTest {

    private static final double DELTA = 1e-8;

    private static final double HIGH_PRECISION = 1e-10;

    private Ln lnCalculator;

    @BeforeEach
    void setUp() {
        this.lnCalculator = new Ln();
    }

    @Test
    @DisplayName("Сравнение самописного натурального логарифма с эталонным.")
    void testOriginalLn() {
        assertEquals(lnCalculator.etalon(12), lnCalculator.calculate(12, HIGH_PRECISION), DELTA);
        assertEquals(lnCalculator.etalon(2), lnCalculator.calculate(2, HIGH_PRECISION), DELTA);
        assertEquals(lnCalculator.etalon(56), lnCalculator.calculate(56, HIGH_PRECISION), DELTA);
        assertEquals(lnCalculator.etalon(215), lnCalculator.calculate(215, HIGH_PRECISION), DELTA);
        assertEquals(lnCalculator.etalon(104), lnCalculator.calculate(104, HIGH_PRECISION), DELTA);
    }

    @Test
    @DisplayName("Тест ln(1) = 0")
    void testLnOfOne() {
        double expected = 0.0;
        double actual = lnCalculator.calculate(1.0, HIGH_PRECISION);
        assertEquals(expected, actual, DELTA);
    }

    @Test
    @DisplayName("Тест ln(e) = 1")
    void testLnOfE() {
        double e = Math.E;
        double expected = 1.0;
        double actual = lnCalculator.calculate(e, HIGH_PRECISION);
        assertEquals(expected, actual, DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.5, 2.0, 5.0, 10.0, 0.1, 100.0})
    @DisplayName("Параметризованный тест для разных значений x")
    void testLnVariousValues(double x) {
        double expected = Math.log(x);
        double actual = lnCalculator.calculate(x, HIGH_PRECISION);
        assertEquals(expected, actual, DELTA);
    }

    @Test
    @DisplayName("Тест ны неверный ввод данных.")
    void testIncorrectInput() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {lnCalculator.calculate(0, -1);});
        Throwable exception1 = assertThrows(IllegalArgumentException.class, () -> {lnCalculator.calculate(0, DELTA);});
        assertThrows(IllegalArgumentException.class, () -> {lnCalculator.calculate(0, 10);});
        assertThrows(IllegalArgumentException.class, () -> {lnCalculator.calculate(0, -100);});
        assertEquals("Значения погрешности должны быть в пределе (0, 1)", exception.getMessage());
        assertEquals("Аргумент должен быть положительным.", exception1.getMessage());
    }
}
