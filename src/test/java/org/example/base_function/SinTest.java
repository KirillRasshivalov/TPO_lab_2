package org.example.base_function;

import org.example.base_functions.Sin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SinTest {
    private static final double DELTA = 1e-9;
    private static final double PI = Math.PI;

    private Sin sinCalculator;

    @BeforeEach
    void setUp() {
        sinCalculator = new Sin();
    }

    @Test
    @DisplayName("Значения из кружка.")
    void testBasicSin() {
        assertEquals(0.0, sinCalculator.calculate(0, DELTA), DELTA);
        assertEquals(1.0, sinCalculator.calculate(PI / 2, DELTA), DELTA);
        assertEquals(0.0, sinCalculator.calculate(PI, DELTA), DELTA);
        assertEquals(-1.0, sinCalculator.calculate(PI + PI / 2, DELTA), DELTA);
        assertEquals(0.0, sinCalculator.calculate(2 * PI, DELTA), DELTA);
    }

    @Test
    @DisplayName("Проверка на то что наша функция действительно периодична и тесты выше достаточные.")
    void testCorrectRealisation() {
        assertEquals(sinCalculator.calculate(0, DELTA), sinCalculator.calculate(0 + 2 * PI, DELTA), DELTA);
        assertEquals(sinCalculator.calculate(0, DELTA), sinCalculator.calculate(0 - 2 * PI, DELTA), DELTA);
        assertEquals(sinCalculator.calculate(PI / 2, DELTA), sinCalculator.calculate(PI / 2 + 2 * PI, DELTA), DELTA);
        assertEquals(sinCalculator.calculate(PI / 2, DELTA), sinCalculator.calculate(PI / 2 - 2 * PI, DELTA), DELTA);
        assertEquals(sinCalculator.calculate(PI, DELTA), sinCalculator.calculate(PI + PI * 2, DELTA), DELTA);
        assertEquals(sinCalculator.calculate(PI, DELTA), sinCalculator.calculate(PI - PI * 2, DELTA), DELTA);
        assertEquals(sinCalculator.calculate((3 * PI) / 2, DELTA), sinCalculator.calculate((3 * PI) / 2 + 2 * PI, DELTA), DELTA);
        assertEquals(sinCalculator.calculate((3 * PI) / 2, DELTA), sinCalculator.calculate((3 * PI) / 2 - 2 * PI, DELTA), DELTA);
        assertEquals(sinCalculator.calculate(2 * PI, DELTA), sinCalculator.calculate(2 * PI + 2 * PI, DELTA), DELTA);
        assertEquals(sinCalculator.calculate(2 * PI, DELTA), sinCalculator.calculate(2 * PI - 2 * PI, DELTA), DELTA);
    }

    @Test
    @DisplayName("Симмметричность фнукции (sin(-x) = -sin(x)).")
    void testSymmetricInput() {
        double x1 = 10;
        assertEquals(sinCalculator.calculate(-x1, DELTA), -1 * sinCalculator.calculate(x1, DELTA), DELTA);
        double x2 = -10;
        assertEquals(sinCalculator.calculate(-x2, DELTA), -1 * sinCalculator.calculate(x2, DELTA), DELTA);
        double x3 = 0.5;
        assertEquals(sinCalculator.calculate(-x3, DELTA), -1 * sinCalculator.calculate(x3, DELTA), DELTA);
        double x4 = -0.5;
        assertEquals(sinCalculator.calculate(-x4, DELTA), -1 * sinCalculator.calculate(x4, DELTA), DELTA);
    }

    @Test
    @DisplayName("Неверные значения на входе функции.")
    void testIncorrectInput() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {sinCalculator.calculate(0, -1);});
        assertThrows(IllegalArgumentException.class, () -> {sinCalculator.calculate(0, 10);});
        assertThrows(IllegalArgumentException.class, () -> {sinCalculator.calculate(0, -100);});
        assertThrows(IllegalArgumentException.class, () -> {sinCalculator.calculate(0, 1000);});
        assertThrows(IllegalArgumentException.class, () -> {sinCalculator.calculate(0, -10000);});
        assertEquals("Значения погрешности должны быть в пределе (0, 1)", exception.getMessage());
    }

    @Test
    @DisplayName("Тест с некоторыми значениями синуса и сравнения их с эталоном.")
    void testSomeInput() {
        assertEquals(sinCalculator.etalon(12), sinCalculator.calculate(12, DELTA), DELTA);
        assertEquals(sinCalculator.etalon(-16), sinCalculator.calculate(-16, DELTA), DELTA);
        assertEquals(sinCalculator.etalon(15), sinCalculator.calculate(15, DELTA), DELTA);
        assertEquals(sinCalculator.etalon(-11), sinCalculator.calculate(-11, DELTA), DELTA);
    }

}
