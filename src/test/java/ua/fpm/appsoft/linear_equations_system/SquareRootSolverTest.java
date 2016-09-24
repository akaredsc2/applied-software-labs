package ua.fpm.appsoft.linear_equations_system;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SquareRootSolverTest {

    private RealMatrix matrix;
    private RealVector vector;
    private static final double delta = 0.0001;

    @Before
    public void setUp() throws Exception {
        matrix = MatrixUtils.createRealMatrix(new double[][]{
                {2, 1, 4},
                {1, 1, 3},
                {4, 3, 14}
        });
        vector = MatrixUtils.createRealVector(new double[]{16, 12, 52});
    }

    @Test
    public void solve() throws Exception {
        RealVector actualResult = SquareRootSolver.solve(matrix, vector, delta);
        RealVector product = matrix.preMultiply(actualResult);
        for (int i = 0; i < actualResult.getDimension(); i++) {
           assertEquals(vector.getEntry(i), product.getEntry(i), delta);
        }
    }

}