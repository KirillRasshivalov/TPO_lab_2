package org.example.functions.trig;

import org.example.base_functions.Sin;
import org.example.util.CsvMockUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SecTest {
    private static final double DELTA = 1e-4;

    private static final double HIGH_PRECISION = 1e-10;

    private static final double PI = Math.PI;

    private Sec secCalculator;

    private static final double LOW_LIMIT = -100;

    private static final double HIGH_LIMIT = 100;

    private static final double STEP = 0.25;

    @BeforeEach
    void setUp() {
        this.secCalculator = new Sec(new Cos(new Sin()));
    }

    @Test
    @DisplayName("Тест с замоканным косинусом.")
    void testSinMock() throws Exception {
        Cos cosMock = CsvMockUtil.mockFromCsv("cosValues", Cos.class);
        Cos cos = new Cos(new Sin());
        Sec secMock = new Sec(cosMock);
        Sec sec = new Sec(cos);
        for (double x = LOW_LIMIT; x <= HIGH_LIMIT; x += STEP) {
            try {
                sec.calculate(x, HIGH_PRECISION);
                assertEquals(secMock.calculate(x, HIGH_PRECISION), sec.calculate(x, HIGH_PRECISION), DELTA, ((Double) x).toString());
            } catch (Exception e) {
                assertEquals("Значение косинуса не должно быть равно нулю.", e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Тест с замоканным косинусом и сравнением с эталоном.")
    void testSinMockEtalon() throws Exception {
        Cos cosMock = CsvMockUtil.mockFromCsv("cosValues", Cos.class);
        Cos cos = new Cos(new Sin());
        Sec secMock = new Sec(cosMock);
        Sec sec = new Sec(cos);
        for (double x = LOW_LIMIT; x <= HIGH_LIMIT; x += STEP) {
            try {
                sec.calculate(x, HIGH_PRECISION);
                assertEquals(secMock.calculate(x, HIGH_PRECISION), sec.etalon(x), DELTA);
            } catch (Exception e) {
                assertEquals("Значение косинуса не должно быть равно нулю.", e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Проверка работаспособности на значениях синуса из таблицы.")
    void testOnTableSin() {
        assertEquals(1, secCalculator.calculate(0, HIGH_PRECISION), DELTA);
        assertEquals(-1, secCalculator.calculate(PI, HIGH_PRECISION), DELTA);
    }

    @Test
    @DisplayName("Проверка достаточности предыдущих тестов с помощью периода.")
    void testProvePrev() {
        assertEquals(secCalculator.calculate(0, HIGH_PRECISION), secCalculator.calculate(0 + 2 * PI, HIGH_PRECISION), DELTA);
        assertEquals(secCalculator.calculate(0, HIGH_PRECISION), secCalculator.calculate(0 - 2 * PI, HIGH_PRECISION), DELTA);
        assertEquals(secCalculator.calculate(PI, HIGH_PRECISION), secCalculator.calculate(PI + 2 * PI, HIGH_PRECISION), DELTA);
        assertEquals(secCalculator.calculate(PI, HIGH_PRECISION), secCalculator.calculate(PI - 2 * PI, HIGH_PRECISION), DELTA);
    }

    @Test
    @DisplayName("Проверка точек выходящих за ОДЗ.")
    void testIncorrectValues() {
        Throwable error =  assertThrows(ArithmeticException.class, () -> {secCalculator.calculate(PI / 2, HIGH_PRECISION);});
        assertThrows(ArithmeticException.class, () -> {
            secCalculator.calculate(PI / 2, HIGH_PRECISION);
        });
        assertThrows(ArithmeticException.class, () -> {
            secCalculator.calculate((3 * PI) / 2, HIGH_PRECISION);
        });
        assertEquals("Значение косинуса не должно быть равно нулю.", error.getMessage());
    }

    @Test
    @DisplayName("Сравнение с оргинальным секансом.")
    void testOriginal() {
        double x1 = PI / 4;
        double x2 = 12;
        double x3 = 2 * PI / 6;
        assertEquals(secCalculator.etalon(x1), secCalculator.calculate(x1, HIGH_PRECISION), DELTA);
        assertEquals(secCalculator.etalon(x2), secCalculator.calculate(x2, HIGH_PRECISION), DELTA);
        assertEquals(secCalculator.etalon(x3), secCalculator.calculate(x3, HIGH_PRECISION), DELTA);
    }
}
