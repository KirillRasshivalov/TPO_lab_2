package org.example;


import org.example.base_functions.Ln;
import org.example.base_functions.Sin;
import org.example.functions.trig.Cos;
import org.example.functions.trig.Cosec;
import org.example.functions.trig.Sec;
import org.example.functions.trig.Tan;
import org.example.functions.log.Log;
import org.example.system.SystemRealisation;
import org.example.util.CsvSaver;

public class App
{

    private static final double DEFAULT_PRECISION = 1e-7;

    public static void main( String[] args )
    {
        try {
            Sin sin = new Sin();
            Cos cos = new Cos(sin);
            Sec sec = new Sec(cos);
            Cosec cosec = new Cosec(sin);
            Tan tan = new Tan(sin, cos);
            Ln ln = new Ln();
            Log log = new Log(ln);

            SystemRealisation system = new SystemRealisation(sin, cosec, sec, tan, ln, log);

            CsvSaver.saveSystem(system, -10, 25, 0.5, DEFAULT_PRECISION, "system_values.csv");
            CsvSaver.saveTrigFunction(sin, "Sin", -10, 10, 0.5, DEFAULT_PRECISION, "sin_values.csv");
            CsvSaver.saveTrigFunction(cos, "Cos", -10, 10, 0.5, DEFAULT_PRECISION, "cos_values.csv");
            CsvSaver.saveTrigFunction(tan, "Tan", -10, 10, 0.5, DEFAULT_PRECISION, "tan_values.csv");
            CsvSaver.saveTrigFunction(sec, "Sec", -10, 10, 0.5, DEFAULT_PRECISION, "sec_values.csv");
            CsvSaver.saveTrigFunction(cosec, "Cosec", -10, 10, 0.5, DEFAULT_PRECISION, "cosec_values.csv");
            CsvSaver.saveLogFunction(ln, "Ln", 0.1, 10, 0.2, DEFAULT_PRECISION, Math.E, "ln_values.csv");
            CsvSaver.saveLogFunction(log, "Log", 0.1, 10, 0.2, DEFAULT_PRECISION, 2, "log2_values.csv");
            CsvSaver.saveLogFunction(log, "Log", 0.1, 10, 0.2, DEFAULT_PRECISION, 3, "log3_values.csv");
            CsvSaver.saveLogFunction(log, "Log", 0.1, 10, 0.2, DEFAULT_PRECISION, 5, "log5_values.csv");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}
