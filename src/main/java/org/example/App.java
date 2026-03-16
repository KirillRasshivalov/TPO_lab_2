package org.example;


import org.example.base_functions.Ln;
import org.example.base_functions.Sin;
import org.example.functions.log.Cos;
import org.example.functions.log.Cosec;
import org.example.functions.log.Sec;
import org.example.functions.log.Tan;
import org.example.functions.trig.Log;
import org.example.system.SystemRealisation;
import org.example.util.CsvSaver;

public class App
{

    private static final double DEFAULT_PRECISION = 1e-7;

    public static void main( String[] args )
    {
        try {
            Sin sin = new Sin();
            Sec sec = new Sec(new Cos(sin));
            Cosec cosec = new Cosec(sin);
            Tan tan = new Tan(sin, new Cos(sin));
            Ln ln = new Ln();
            Log log = new Log(ln);

            SystemRealisation system = new SystemRealisation(sin, cosec, sec, tan, ln, log);

            CsvSaver.saveSystem(system, 5, 25, 5, DEFAULT_PRECISION, "system_values.csv");
            CsvSaver.saveTrigFunction(sin, "Sin", -10, 10, 0.5, DEFAULT_PRECISION, "sin_values.csv");
            CsvSaver.saveLogFunction(ln, "Ln", 0.1, 10, 0.2, DEFAULT_PRECISION, Math.E, "ln_values.csv");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}
