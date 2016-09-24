package ua.fpm.appsoft.linear_equations_system;

import org.apache.commons.math3.linear.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SquareRootSolverTest {

    private RealMatrix matrix3;
    private RealMatrix nonPositiveDefiniteMatrix;
    private RealMatrix nonSymmetricMatrix;
    private RealMatrix singularMatrix;
    private RealVector vector3;
    private RealVector vector5;
    private static final double delta = 0.0001;

    @Before
    public void setUp() throws Exception {
        matrix3 = MatrixUtils.createRealMatrix(new double[][]{
                {2, 1, 4},
                {1, 1, 3},
                {4, 3, 14}
        });
        nonPositiveDefiniteMatrix = MatrixUtils.createRealMatrix(new double[][]{
                {1, 2, 4, 6, 8},
                {2, 3, 0, 7, 5},
                {4, 0, 5, 1, 6},
                {6, 7, 1, 7, 0},
                {8, 5, 6, 0, 9}
        });
        nonSymmetricMatrix = MatrixUtils.createRealMatrix(new double[][]{
                {2, 1, 3},
                {1, 1, 3},
                {4, 3, 14}
        });
        singularMatrix = MatrixUtils.createRealMatrix(new double[][]{
                {1, 1, 3},
                {1, 1, 3},
                {3, 3, 14}
        });
        vector3 = MatrixUtils.createRealVector(new double[]{16, 12, 52});
        vector5 = MatrixUtils.createRealVector(new double[]{1, 4, 0, 8, 8});
    }

    @Test
    public void solve() throws Exception {
        RealVector actualResult = SquareRootSolver.solve(matrix3, vector3);
        RealVector product = matrix3.preMultiply(actualResult);
        for (int i = 0; i < actualResult.getDimension(); i++) {
            assertEquals(vector3.getEntry(i), product.getEntry(i), delta);
        }
    }

    @Test(expected = NonPositiveDefiniteMatrixException.class)
    public void testNonPositiveDefiniteMatrix() {
        SquareRootSolver.solve(nonPositiveDefiniteMatrix, vector5);
    }

    @Test(expected = NonSymmetricMatrixException.class)
    public void testNonSymmetricMatrix() {
        SquareRootSolver.solve(nonSymmetricMatrix, vector3);
    }

    @Test(expected = SingularMatrixException.class)
    public void testSingularMatrix() {
        SquareRootSolver.solve(singularMatrix, vector3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonMatchingDimensions() {
        SquareRootSolver.solve(matrix3, vector5);
    }

}