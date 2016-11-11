package ua.fpm.appsoft.unconditionalOptimization;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealVector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HookeJeevesOptimizerTest {

    private HookeJeevesOptimizer optimizer;
    private RealVector initPoint;
    private double precision;
    private double initIncrement;

    @Before
    public void setUp() {
        this.precision = 0.001;
        this.initIncrement = 1.0;

    }

    @Test
    public void optimize2DFunction() throws Exception {
        this.initPoint = MatrixUtils.createRealVector(new double[]{8, 9});
        this.optimizer = new HookeJeevesOptimizer("4 * Math.pow(x1 - 5, 2) + Math.pow(x2 - 6, 2)",
                initPoint,
                initIncrement,
                precision,
                1.0
        );
        assertEquals(0, optimizer.optimize(), precision);
    }

    @Test
    public void optimizeTargetFunction() throws Exception {
        this.initPoint = MatrixUtils.createRealVector(new double[]{-2, 3, -4, 5});
        this.optimizer = new HookeJeevesOptimizer("Math.pow(1 - x1, 2) + Math.pow(x1 - x2, 2) " +
                "+ Math.pow(x2 - x3, 2) + Math.pow(x3 - x4, 2)",
                initPoint,
                initIncrement,
                precision,
                1.0
        );
        assertEquals(0, optimizer.optimize(), precision);
    }
}