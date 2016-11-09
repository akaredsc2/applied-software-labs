package ua.fpm.appsoft.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class Evaluator {

    private final ScriptEngine engine;
    private final String function;

    public Evaluator(String function) {
        this.function = function;
        this.engine = new ScriptEngineManager().getEngineByName("nashorn");
    }

    public double eval(double argument) throws ScriptException {
        engine.eval("var y = " + argument + ";");
        Object evalResult = engine.eval(function);
        if (evalResult != null && evalResult instanceof Number) {
            return ((Number) evalResult).doubleValue();
        }
        throw new ScriptException("Failed to evaluate function!");
    }

    public double eval(double x, double y) throws ScriptException {
        engine.eval("var x = " + x + ";");
        engine.eval("var y = " + y + ";");
        Object evalResult = engine.eval(function);
        if (evalResult != null && evalResult instanceof Number) {
            return ((Number) evalResult).doubleValue();
        }
        throw new ScriptException("Failed to evaluate function!");
    }

}
