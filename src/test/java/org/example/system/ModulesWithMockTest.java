package org.example.system;

import org.example.base_functions.Ln;
import org.example.base_functions.Sin;
import org.example.functions.trig.Cosec;
import org.example.functions.trig.Sec;
import org.example.functions.trig.Tan;
import org.example.functions.log.Log;
import org.example.util.CsvSaver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ModulesWithMockTest {

    private static final double DELTA = 1e-4;

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("Тригонометрическая ветка системы.")
    void testTrigonometricBranchWithMocks() throws IOException {
        Sin sinMock = mock(Sin.class);
        Cosec cosecMock = mock(Cosec.class);
        Sec secMock = mock(Sec.class);
        Tan tanMock = mock(Tan.class);
        Ln lnMock = mock(Ln.class);
        Log logMock = mock(Log.class);

        when(sinMock.calculate(eq(-2.0), anyDouble())).thenReturn(-0.9092974268256817);
        when(cosecMock.calculate(eq(-2.0), anyDouble())).thenReturn(-1.0997501702946164);
        when(secMock.calculate(eq(-2.0), anyDouble())).thenReturn(-2.4030023964876135);
        when(tanMock.calculate(eq(-2.0), anyDouble())).thenReturn(2.185039863261519);

        when(sinMock.calculate(eq(-1.5), anyDouble())).thenReturn(-0.9974949866040544);
        when(cosecMock.calculate(eq(-1.5), anyDouble())).thenReturn(-1.0025113042407244);
        when(secMock.calculate(eq(-1.5), anyDouble())).thenReturn(14.136832902969034);
        when(tanMock.calculate(eq(-1.5), anyDouble())).thenReturn(-14.101419947171719);

        when(sinMock.calculate(eq(-1.0), anyDouble())).thenReturn(-0.8414709848078965);
        when(cosecMock.calculate(eq(-1.0), anyDouble())).thenReturn(-1.1883951057781212);
        when(secMock.calculate(eq(-1.0), anyDouble())).thenReturn(1.8508157176809255);
        when(tanMock.calculate(eq(-1.0), anyDouble())).thenReturn(-1.5574077246549023);

        when(sinMock.calculate(eq(-0.5), anyDouble())).thenReturn(-0.479425538604203);
        when(cosecMock.calculate(eq(-0.5), anyDouble())).thenReturn(-2.085829642933488);
        when(secMock.calculate(eq(-0.5), anyDouble())).thenReturn(1.1394939273205492);
        when(tanMock.calculate(eq(-0.5), anyDouble())).thenReturn(-0.5463024898437905);

        SystemRealisation systemWithMocks = new SystemRealisation(
                sinMock, cosecMock, secMock, tanMock, lnMock, logMock
        );
        String actualFile = tempDir.resolve("actual_output.csv").toString();
        CsvSaver.saveSystem(systemWithMocks, -2.0, -0.5, 0.5, DELTA, actualFile);

        String[][] expectedData = {
                {"X", "Результаты модуля (X)"},
                {"-2.0", "1.6918892876"},
                {"-1.5", "-15.169650451"},
                {"-1.0", "-2.93918101"},
                {"-0.5", "-1.9033105935"}
        };
        List<String[]> actualData = readCsv(actualFile);
        assertEquals(expectedData.length, actualData.size());
        assertEquals(expectedData[0][0], actualData.get(0)[0]);
        assertEquals(expectedData[0][1], actualData.get(0)[1]);

        for (int i = 1; i < expectedData.length; i++) {
            String[] expectedRow = expectedData[i];
            String[] actualRow = actualData.get(i);
            double expectedX = Double.parseDouble(expectedRow[0]);
            double actualX = Double.parseDouble(actualRow[0]);
            assertEquals(expectedX, actualX, DELTA);
            double expectedVal = Double.parseDouble(expectedRow[1]);
            double actualVal = Double.parseDouble(actualRow[1]);
            assertEquals(expectedVal, actualVal, DELTA);
        }
        verify(sinMock, atLeastOnce()).calculate(anyDouble(), anyDouble());
        verify(cosecMock, atLeastOnce()).calculate(anyDouble(), anyDouble());
    }

    @Test
    @DisplayName("Логарифмическая ветка системы.")
    void testLogarithmicBranchWithMocks() throws IOException {
        Sin sinMock = mock(Sin.class);
        Cosec cosecMock = mock(Cosec.class);
        Sec secMock = mock(Sec.class);
        Tan tanMock = mock(Tan.class);
        Ln lnMock = mock(Ln.class);
        Log logMock = mock(Log.class);

        when(lnMock.calculate(eq(1.0), anyDouble(), anyDouble())).thenReturn(0.0);
        when(logMock.calculate(eq(1.0), anyDouble(), eq(2.0))).thenReturn(0.0);
        when(logMock.calculate(eq(1.0), anyDouble(), eq(3.0))).thenReturn(0.0);
        when(logMock.calculate(eq(1.0), anyDouble(), eq(5.0))).thenReturn(0.0);

        when(lnMock.calculate(eq(2.0), anyDouble(), anyDouble())).thenReturn(0.6931471805599453);
        when(logMock.calculate(eq(2.0), anyDouble(), eq(2.0))).thenReturn(1.0);
        when(logMock.calculate(eq(2.0), anyDouble(), eq(3.0))).thenReturn(0.6309297535714574);
        when(logMock.calculate(eq(2.0), anyDouble(), eq(5.0))).thenReturn(0.43067655807339306);

        when(lnMock.calculate(eq(3.0), anyDouble(), anyDouble())).thenReturn(1.0986122886681098);
        when(logMock.calculate(eq(3.0), anyDouble(), eq(2.0))).thenReturn(1.5849625007211563);
        when(logMock.calculate(eq(3.0), anyDouble(), eq(3.0))).thenReturn(1.0);
        when(logMock.calculate(eq(3.0), anyDouble(), eq(5.0))).thenReturn(0.6826061944859854);

        SystemRealisation systemWithMocks = new SystemRealisation(
                sinMock, cosecMock, secMock, tanMock, lnMock, logMock
        );

        String actualFile = tempDir.resolve("log_actual_output.csv").toString();
        CsvSaver.saveSystem(systemWithMocks, 1.0, 3.0, 1.0, DELTA, actualFile);

        String[][] expectedData = {
                {"X", "Результаты модуля (X)"},
                {"1.0", "NaN"},
                {"2.0", "0.1621405"},
                {"3.0", "0.2747760"}
        };
        List<String[]> actualData = readCsv(actualFile);
        assertEquals(expectedData.length, actualData.size());
        assertEquals(expectedData[0][0], actualData.get(0)[0]);
        assertEquals(expectedData[0][1], actualData.get(0)[1]);
        for (int i = 1; i < expectedData.length; i++) {
            String[] expectedRow = expectedData[i];
            String[] actualRow = actualData.get(i);
            double expectedX = Double.parseDouble(expectedRow[0]);
            double actualX = Double.parseDouble(actualRow[0]);
            assertEquals(expectedX, actualX, DELTA);
            double expectedVal = Double.parseDouble(expectedRow[1]);
            double actualVal = Double.parseDouble(actualRow[1]);
            assertEquals(expectedVal, actualVal, DELTA);
        }
    }

    private List<String[]> readCsv(String filename) throws IOException {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim();
                }
                data.add(parts);
            }
        }
        return data;
    }
}
