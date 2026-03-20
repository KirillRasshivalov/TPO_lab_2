package org.example.util;

import org.example.interfaces.BaseLogFunction;
import org.example.interfaces.BaseTrigFunction;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

public class CsvMockUtil {
    private static final double DELTA = 1e-9;

    public static <T> T mockFromCsv(String filePath, Class<T> clazz) throws Exception {
        T mock = Mockito.mock(clazz);
        Map<Double, Double> mockValues = new HashMap<>();
        List<Double> sortedKeys = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean skipHeader = true;
            while ((line = br.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts[1].equals("ОШИБКА")) {
                    continue;
                }
                double x = Double.parseDouble(parts[0].trim());
                double y = Double.parseDouble(parts[1].trim());
                mockValues.put(x, y);
                sortedKeys.add(x);
            }
        }

        Collections.sort(sortedKeys);

        if (BaseLogFunction.class.isAssignableFrom(clazz)) {
            when(((BaseLogFunction) mock).calculate(anyDouble(), anyDouble()))
                    .thenAnswer(invocation -> {
                        double x = invocation.getArgument(0);
                        Double value = mockValues.get(x);
                        if (value != null) {
                            return value;
                        }
                        Double bestKey = findNearestKey(mockValues, sortedKeys, x);
                        if (bestKey != null) {
                            return mockValues.get(bestKey);
                        }
                        return Double.NaN;
                    });

            when(((BaseLogFunction) mock).calculate(anyDouble(), anyDouble(), anyDouble()))
                    .thenAnswer(invocation -> {
                        double x = invocation.getArgument(0);
                        Double value = mockValues.get(x);
                        if (value != null) {
                            return value;
                        }
                        Double bestKey = findNearestKey(mockValues, sortedKeys, x);
                        if (bestKey != null) {
                            return mockValues.get(bestKey);
                        }
                        return Double.NaN;
                    });
        } else {
            when(((BaseTrigFunction) mock).calculate(anyDouble(), anyDouble()))
                    .thenAnswer(invocation -> {
                        double x = invocation.getArgument(0);
                        int idx = Collections.binarySearch(sortedKeys, x);
                        if (idx >= 0) {
                            return mockValues.get(sortedKeys.get(idx));
                        }
                        int ins = -idx - 1;
                        if (ins == 0) {
                            return mockValues.get(sortedKeys.get(0));
                        }
                        if (ins >= sortedKeys.size()) {
                            return mockValues.get(sortedKeys.get(sortedKeys.size() - 1));
                        }
                        double left = sortedKeys.get(ins - 1);
                        double right = sortedKeys.get(ins);
                        double nearest = (Math.abs(x - left) < Math.abs(x - right)) ? left : right;
                        return mockValues.get(nearest);
                    });
        }

        return mock;
    }

    private static Double findNearestKey(Map<Double, Double> values, List<Double> sortedKeys, double x) {
        int idx = Collections.binarySearch(sortedKeys, x);
        if (idx >= 0) {
            return sortedKeys.get(idx);
        }
        int ins = -idx - 1;
        if (ins == 0) {
            return sortedKeys.get(0);
        }
        if (ins >= sortedKeys.size()) {
            return sortedKeys.get(sortedKeys.size() - 1);
        }
        double left = sortedKeys.get(ins - 1);
        double right = sortedKeys.get(ins);
        return (Math.abs(x - left) < Math.abs(x - right)) ? left : right;
    }
}