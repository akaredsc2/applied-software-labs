package ua.fpm.appsoft.numericIntegration;

import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.geometry.euclidean.oned.Interval;
import org.junit.Test;

import javax.script.ScriptException;

import static org.junit.Assert.assertEquals;

public class RightRectangleIntegratorTest {

    private static final int intervalCount = 100;
    private static final double delta = 1. / intervalCount;

    @Test
    public void integrate() throws Exception {
        String function = "Math.pow(Math.log(y), 2) / y";
        NumericIntegrator integrator = new RightRectangleIntegrator(function, new Interval(1, Math.E), intervalCount);
        assertEquals(integrator.integrate(), 1.0 / 3.0, delta);

        function = "Math.exp(y) + 1";
        integrator = new RightRectangleIntegrator(function, new Interval(0, 1), intervalCount);
        assertEquals(integrator.integrate(), Math.E, delta);

        integrator = new RightRectangleIntegrator(function, new Interval(0, 0), intervalCount);
        assertEquals(integrator.integrate(), 0, delta);
    }

    @Test(expected = NumberIsTooSmallException.class)
    public void reversedInterval() {
        new RightRectangleIntegrator("x * x", new Interval(88, 14), intervalCount);
    }

    @Test(expected = ScriptException.class)
    public void wrongFunctionExpression() throws Exception {
        new RightRectangleIntegrator("14.88yz", new Interval(1, Math.E), intervalCount).integrate();
    }

    @Test(expected = ArithmeticException.class)
    public void divideByZero() throws Exception {
        new RightRectangleIntegrator("1 / 0", new Interval(1, Math.E), intervalCount).integrate();
    }

    @Test(expected = ArithmeticException.class)
    public void complexResult() throws Exception {
        new RightRectangleIntegrator("Math.sqrt(-1)", new Interval(1, Math.E), intervalCount).integrate();
    }

}