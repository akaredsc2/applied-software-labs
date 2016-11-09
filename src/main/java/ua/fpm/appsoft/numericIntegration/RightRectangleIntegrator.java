package ua.fpm.appsoft.numericIntegration;

import org.apache.commons.math3.geometry.euclidean.oned.Interval;
import ua.fpm.appsoft.util.Evaluator;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class RightRectangleIntegrator implements NumericIntegrator {
    private Evaluator evaluator;
    private final Interval interval;
    private final int intervalCount;

    public RightRectangleIntegrator(String function, Interval interval, int intervalCount) {
        this.evaluator = new Evaluator(function);
        this.interval = interval;
        this.intervalCount = intervalCount;
    }

    @Override
    public double integrate() throws ScriptException {
        double result = 0.0;
        double step = interval.getSize() / intervalCount;
        double currentArgument = interval.getInf();
        for (int i = 0; i < intervalCount; i++) {
            double rightFunctionValue = evaluator.eval(currentArgument + step);
            result += rightFunctionValue;
            currentArgument += step;
        }
        if (Double.isNaN(result) || Double.isInfinite(result)) {
            throw new ArithmeticException("Not a real number result!");
        } else {
            return result * step;
        }
    }

}