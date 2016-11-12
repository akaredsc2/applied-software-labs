package ua.fpm.appsoft.unconditionalOptimization;

import org.apache.commons.math3.linear.RealVector;

import javax.script.ScriptException;

public interface UnconditionalOptimizer {

    double optimize() throws ScriptException;

    RealVector getOptimalPoint();

}
