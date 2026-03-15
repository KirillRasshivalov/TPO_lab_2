package org.example.functions.log;

import org.example.base_functions.Sin;
import org.example.functions.log.Cos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CosTest {

    private static final double PI = Math.PI;
    private static final double DELTA = 1e-9;

    private Cos cosCalculator;

    @BeforeEach
    void setUp() {
        cosCalculator = new Cos(new Sin());
    }

    @Test
    @DisplayName("Значения из кружка.")
    void testBasicSin() {
        assertEquals(1.0, cosCalculator.calculate(0, DELTA), DELTA);
        assertEquals(0.0, cosCalculator.calculate(PI / 2, DELTA), DELTA);
        assertEquals(-1.0, cosCalculator.calculate(PI, DELTA), DELTA);
        assertEquals(0.0, cosCalculator.calculate((3 * PI) / 2, DELTA), DELTA);
    }

    @Test
    @DisplayName("Доказательство что набор тестов выше достаточный.")
    void testProve() {
        assertEquals(cosCalculator.calculate(0, DELTA), cosCalculator.calculate(0 + 2 * PI, DELTA), DELTA);
        assertEquals(cosCalculator.calculate(0, DELTA), cosCalculator.calculate(0 - 2 * PI, DELTA), DELTA);
        assertEquals(cosCalculator.calculate(PI / 2, DELTA), cosCalculator.calculate(PI / 2 + 2 * PI, DELTA), DELTA);
        assertEquals(cosCalculator.calculate(PI / 2, DELTA), cosCalculator.calculate(PI / 2 - 2 * PI, DELTA), DELTA);
        assertEquals(cosCalculator.calculate(PI, DELTA), cosCalculator.calculate(PI + 2 * PI, DELTA), DELTA);
        assertEquals(cosCalculator.calculate(PI, DELTA), cosCalculator.calculate(PI - 2 * PI, DELTA), DELTA);
        assertEquals(cosCalculator.calculate((3 * PI) / 2, DELTA), cosCalculator.calculate((3 * PI) / 2 + 2 * PI, DELTA), DELTA);
        assertEquals(cosCalculator.calculate((3 * PI) / 2, DELTA), cosCalculator.calculate((3 * PI) / 2 - 2 * PI, DELTA), DELTA);
    }

    @Test
    @DisplayName("Неверные значения на входе функции.")
    void testIncorrectInput() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {cosCalculator.calculate(0, -1);});
        assertThrows(IllegalArgumentException.class, () -> {cosCalculator.calculate(0, 10);});
        assertThrows(IllegalArgumentException.class, () -> {cosCalculator.calculate(0, -100);});
        assertThrows(IllegalArgumentException.class, () -> {cosCalculator.calculate(0, 1000);});
        assertThrows(IllegalArgumentException.class, () -> {cosCalculator.calculate(0, -10000);});
        assertEquals("Значения погрешности должны быть в пределе (0, 1)", exception.getMessage());
    }

    @Test
    @DisplayName("Тест с некоторыми значениями синуса и сравнения их с эталоном.")
    void testSomeInput() {
        assertEquals(cosCalculator.etalon(12), cosCalculator.calculate(12, DELTA), DELTA);
        assertEquals(cosCalculator.etalon(-16), cosCalculator.calculate(-16, DELTA), DELTA);
        assertEquals(cosCalculator.etalon(15), cosCalculator.calculate(15, DELTA), DELTA);
        assertEquals(cosCalculator.etalon(-11), cosCalculator.calculate(-11, DELTA), DELTA);
    }
}
