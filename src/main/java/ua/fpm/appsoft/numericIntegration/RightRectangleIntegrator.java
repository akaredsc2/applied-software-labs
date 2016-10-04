package ua.fpm.appsoft.numericIntegration;

import org.apache.commons.math3.geometry.euclidean.oned.Interval;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class RightRectangleIntegrator implements NumericIntegrator {

    private final ScriptEngine engine;
    private final String function;
    private final Interval interval;
    private final int intervalCount;

    public RightRectangleIntegrator(String function, Interval interval, int intervalCount) {
        this.function = function;
        this.interval = interval;
        this.intervalCount = intervalCount;
        this.engine = new ScriptEngineManager().getEngineByName("nashorn");
    }

    @Override
    public double integrate() throws ScriptException {
        double result = 0.0;
        double step = interval.getSize() / intervalCount;
        double currentArgument = interval.getInf();
        for (int i = 0; i < intervalCount; i++) {
            double rightFunctionValue = apply(currentArgument + step);
            result += rightFunctionValue;
            currentArgument += step;
        }
        if (Double.isNaN(result) || Double.isInfinite(result)) {
            throw new ArithmeticException("Not a real number result!");
        } else {
            return result * step;
        }
    }

    private double apply(double argument) throws ScriptException {
        engine.eval("var y = " + argument + ";");
        Object evalResult = engine.eval(function);
        if (evalResult != null) {
            if (evalResult instanceof Double) {
                return (double) evalResult;
            }
            if (evalResult instanceof Integer) {
                return (int) evalResult;
            }
        }
        throw new ScriptException("Failed to evaluate function!");
    }

}