package ua.fpm.appsoft.linearEquationsSystem;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.linear.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SquareRootSolverTest {

    private LinearSystemDecompositionSolver solver;
    private RealMatrix matrix3;
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
        vector3 = MatrixUtils.createRealVector(new double[]{16, 12, 52});
        vector5 = MatrixUtils.createRealVector(new double[]{1, 4, 0, 8, 8});
        solver = new SquareRootSolver(matrix3, vector3);
    }

    @Test
    public void testSolve() throws Exception {
        RealVector actualResult = solver.solve();
        RealVector product = matrix3.preMultiply(actualResult);
        for (int i = 0; i < actualResult.getDimension(); i++) {
            assertEquals(vector3.getEntry(i), product.getEntry(i), delta);
        }
    }

    @Test
    public void testTriangleMatrices() {
        RealMatrix upper = solver.getUpperTriangleMatrix();
        RealMatrix bottom = solver.getBottomTriangleMatrix();
        RealMatrix actualProduct = upper.preMultiply(bottom);
        for (int i = 0; i < actualProduct.getRowDimension(); i++) {
            for (int j = 0; j < actualProduct.getColumnDimension(); j++) {
                assertEquals(matrix3.getEntry(i, j), actualProduct.getEntry(i, j), delta);
            }
        }
    }

    @Test(expected = NonPositiveDefiniteMatrixException.class)
    public void testNonPositiveDefiniteMatrix() {
        RealMatrix nonPositiveDefiniteMatrix = MatrixUtils.createRealMatrix(new double[][]{
                {1, 2, 4, 6, 8},
                {2, 3, 0, 7, 5},
                {4, 0, 5, 1, 6},
                {6, 7, 1, 7, 0},
                {8, 5, 6, 0, 9}
        });
        new SquareRootSolver(nonPositiveDefiniteMatrix, vector5);
    }

    @Test(expected = NonSymmetricMatrixException.class)
    public void testNonSymmetricMatrix() {
        RealMatrix nonSymmetricMatrix = MatrixUtils.createRealMatrix(new double[][]{
                {2, 1, 3},
                {1, 1, 3},
                {4, 3, 14}
        });
        new SquareRootSolver(nonSymmetricMatrix, vector3);
    }

    @Test(expected = SingularMatrixException.class)
    public void testSingularMatrix() {
        RealMatrix singularMatrix = MatrixUtils.createRealMatrix(new double[][]{
                {1, 1, 3},
                {1, 1, 3},
                {3, 3, 14}
        });
        new SquareRootSolver(singularMatrix, vector3);
    }

    @Test(expected = DimensionMismatchException.class)
    public void testNonMatchingDimensions() {
        new SquareRootSolver(matrix3, vector5);
    }

}