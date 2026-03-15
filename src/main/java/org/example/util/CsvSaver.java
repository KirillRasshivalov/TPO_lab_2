package org.example.util;

import org.example.interfaces.BaseSystem;
import org.example.interfaces.BaseTrigFunction;
import org.example.interfaces.BaseLogFunction;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CsvSaver {

    private static final double DEFAULT_PRECISION = 1e-7;

    public static void saveSystem(BaseSystem system, double xStart, double xEnd,
                                  double step, String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("X,Результаты модуля (X)");
            for (double x = xStart; x <= xEnd; x += step) {
                try {
                    double result = system.calculateAnswer(x, DEFAULT_PRECISION);
                    writer.printf("%.6f,%.10f%n", x, result);
                } catch (Exception e) {
                    writer.printf("%.6f,ОШИБКА%n", x);
                }
            }
        }
    }
    public static void saveTrigFunction(BaseTrigFunction function, String functionName,
                                        double xStart, double xEnd, double step,
                                        String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("X," + functionName + "(X)");
            for (double x = xStart; x <= xEnd; x += step) {
                try {
                    double result = function.calculate(x, DEFAULT_PRECISION);
                    writer.printf("%.6f,%.10f%n", x, result);
                } catch (Exception e) {
                    writer.printf("%.6f,ОШИБКА%n", x);
                }
            }
        }
    }

    public static void saveLogFunction(BaseLogFunction function, String functionName,
                                       double xStart, double xEnd, double step,
                                       double base, String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("X," + functionName + "_" + (int)base + "(X)");

            for (double x = xStart; x <= xEnd; x += step) {
                try {
                    double result = function.calculate(x, DEFAULT_PRECISION, base);
                    writer.printf("%.6f,%.10f%n", x, result);
                } catch (Exception e) {
                    writer.printf("%.6f,ОШИБКА%n", x);
                }
            }
        }
    }

    public static void saveSystem(BaseSystem system, double xStart, double xEnd,
                                  double step, double precision, String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("X,Результаты модуля (X)");

            for (double x = xStart; x <= xEnd; x += step) {
                try {
                    double result = system.calculateAnswer(x, precision);
                    writer.printf("%.6f,%.10f%n", x, result);
                } catch (Exception e) {
                    writer.printf("%.6f,ОШИБКА%n", x);
                }
            }
        }
    }
}