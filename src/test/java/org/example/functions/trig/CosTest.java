package org.example.functions.trig;

import org.example.base_functions.Sin;
import org.example.util.CsvMockUtil;
import org.example.util.CsvSaver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CosTest {

    private static final double PI = Math.PI;

    private static final double DELTA = 1e-9;

    private static final double LOW_LIMIT = PI / 2 - 100;

    private static final double HIGH_LIMIT = PI / 2 + 100;

    private static final double STEP = 0.25;

    private Cos cosCalculator;

    @BeforeEach
    void setUp() {
        cosCalculator = new Cos(new Sin());
    }

    @Test
    @DisplayName("Тест с данными синуса.")
    void testSinMock() throws Exception {
        Sin sinMock = CsvMockUtil.mockFromCsv("sinValues", Sin.class);
        Sin sin = new Sin();
        Cos cosWithMock = new Cos(sinMock);
        Cos cos = new Cos(sin);
        for (double x = LOW_LIMIT; x <= HIGH_LIMIT; x += STEP){
            assertEquals(cosWithMock.calculate(x, DELTA), cos.calculate(x, DELTA), DELTA, ((Double) x).toString());
        }
    }

    @Test
    @DisplayName("Тест с данными синуса и сравнение с эталоном.")
    void testSinMockEtalon() throws Exception {
        Sin sinMock = CsvMockUtil.mockFromCsv("sinValues", Sin.class);
        Sin sin = new Sin();
        Cos cosWithMock = new Cos(sinMock);
        Cos cos = new Cos(sin);
        for (double x = LOW_LIMIT; x <= HIGH_LIMIT; x += STEP){
            assertEquals(cosWithMock.calculate(x, DELTA), cos.etalon(x), DELTA, ((Double) x).toString());
        }
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

    @AfterAll
    static void fillCos() throws IOException {
        CsvSaver.saveTrigFunction(new Cos(new Sin()), "Cos", -100, 100, 0.25, DELTA, "cosValues");
    }
}
