package ua.fpm.appsoft.differentialEquations;

import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.geometry.euclidean.oned.Interval;
import org.junit.Assert;
import org.junit.Test;

import javax.script.ScriptException;

public class RungeKuttaThirdOrderMethodSolverTest {

    private NumericDifferentialEquationSolver solver;
    private static final double delta = 0.01;
    private static final int intervalCount = 10;

    @Test
    public void solve() throws Exception {
        solver = new RungeKuttaThirdOrderMethodSolver("x - y", new Interval(0, 1), intervalCount, 0, 0);
        double[] expected = new double[]{0, .0048, .0187, .0408, .0703, .1065, .1487, .1965, .2493, .3065, .3678};
        testCorrectSolve(expected);

        solver = new RungeKuttaThirdOrderMethodSolver("((Math.sin(x)) - y) * Math.cos(x)",
                new Interval(0, Math.PI), intervalCount, 0, 0);
        expected = new double[]{0, .0429, .1431, .25412, .3371, .3673, .3366, .2533, .1420, .0414, -.0025};
        testCorrectSolve(expected);
    }

    @Test(expected = NumberIsTooSmallException.class)
    public void reversedInterval() {
        new RungeKuttaThirdOrderMethodSolver("x * x", new Interval(88, 14), intervalCount, 0, 0);
    }

    @Test(expected = ScriptException.class)
    public void wrongFunctionExpression() throws Exception {
        new RungeKuttaThirdOrderMethodSolver(git, new Interval(1, Math.E), intervalCount, 0, 0).solve();
    }

    @Test(expected = ArithmeticException.class)
    public void divideByZero() throws Exception {
        new RungeKuttaThirdOrderMethodSolver("1 / 0", new Interval(1, Math.E), intervalCount, 0, 0).solve();
    }

    @Test(expected = ArithmeticException.class)
    public void complexResult() throws Exception {
        new RungeKuttaThirdOrderMethodSolver("Math.sqrt(-1)", new Interval(1, Math.E), intervalCount, 0, 0).solve();
    }

    private void testCorrectSolve(double[] expected) throws ScriptException {
        Double[] actual = solver.solve();
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], actual[i], delta);
        }
    }

}