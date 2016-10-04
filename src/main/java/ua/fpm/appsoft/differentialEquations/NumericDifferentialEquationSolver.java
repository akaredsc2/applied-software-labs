package ua.fpm.appsoft.differentialEquations;

import javax.script.ScriptException;

public interface NumericDifferentialEquationSolver {

    Double[] solve() throws ScriptException;

}
