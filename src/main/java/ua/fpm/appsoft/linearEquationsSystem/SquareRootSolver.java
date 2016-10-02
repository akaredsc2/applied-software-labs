package ua.fpm.appsoft.linearEquationsSystem;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.linear.*;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static org.apache.commons.math3.linear.MatrixUtils.createRealMatrix;

public class SquareRootSolver implements LinearSystemDecompositionSolver {

    private RealMatrix initMatrix;
    private RealVector rightSideVector;
    private RealMatrix upperTriangleMatrix;

    public SquareRootSolver(RealMatrix initMatrix, RealVector rightSideVector) {
        checkMatrix(initMatrix);
        checkDimensionMatch(initMatrix, rightSideVector);
        this.initMatrix = initMatrix;
        this.rightSideVector = rightSideVector;
        this.upperTriangleMatrix = createUpperTriangleMatrix();
    }

    @Override
    public RealVector solve() {
        RealVector bottomVector = runForward(getBottomTriangleMatrix(), rightSideVector);
        return reversalRun(upperTriangleMatrix, bottomVector);
    }

    @Override
    public RealMatrix getBottomTriangleMatrix() {
        return upperTriangleMatrix.transpose();
    }

    @Override
    public RealMatrix getUpperTriangleMatrix() {
        return upperTriangleMatrix;
    }

    private RealMatrix createUpperTriangleMatrix() {
        final int rowDimension = initMatrix.getRowDimension();
        RealMatrix result = createRealMatrix(rowDimension, rowDimension);
        for (int i = 0; i < result.getRowDimension(); i++) {
            for (int j = 0; j < result.getRowDimension(); j++) {
                double entryValue;
                entryValue = (i == j) ? getDiagonal(initMatrix, result, i) : getNonDiagonal(initMatrix, result, i, j);
                result.setEntry(i, j, entryValue);
            }
        }
        return result;
    }


    private RealVector runForward(RealMatrix matrix, RealVector vector) {
        RealVector resultVector = MatrixUtils.createRealVector(new double[matrix.getRowDimension()]);
        for (int i = 0; i < matrix.getColumnDimension(); i++) {
            double entryValue = vector.getEntry(i);
            int j;
            for (j = 0; j < i; j++) {
                entryValue -= resultVector.getEntry(j) * matrix.getEntry(i, j);
            }
            resultVector.setEntry(i, entryValue / matrix.getEntry(i, j));
        }
        return resultVector;
    }

    private RealVector reversalRun(RealMatrix matrix, RealVector vector) {
        RealVector resultVector = MatrixUtils.createRealVector(new double[matrix.getRowDimension()]);
        for (int i = matrix.getRowDimension() - 1; i >= 0; i--) {
            double entryValue = vector.getEntry(i);
            int j;
            for (j = matrix.getColumnDimension() - 1; j > i; j--) {
                entryValue -= resultVector.getEntry(j) * matrix.getEntry(i, j);
            }
            resultVector.setEntry(i, entryValue / matrix.getEntry(i, j));
        }
        return resultVector;
    }

    private void checkMatrix(RealMatrix matrix) {
        if (!MatrixUtils.isSymmetric(matrix, 0.0001)) throw new NonSymmetricMatrixException(0, 0, 0);
        if (new LUDecomposition(matrix).getDeterminant() == 0) throw new SingularMatrixException();
        if (!isPositiveDefinite(matrix)) throw new NonPositiveDefiniteMatrixException(0, 0, 0);
    }

    private void checkDimensionMatch(RealMatrix matrix, RealVector vector) {
        if (matrix.getRowDimension() != vector.getDimension())
            throw new DimensionMismatchException(vector.getDimension(), matrix.getColumnDimension());
    }

    private boolean isPositiveDefinite(RealMatrix matrix) {
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            RealMatrix subMatrix = matrix.getSubMatrix(0, i, 0, i);
            if (new LUDecomposition(subMatrix).getDeterminant() <= 0) return false;
        }
        return true;
    }

    private double getDiagonal(RealMatrix matrix, RealMatrix resultMatrix, int index) {
        double result = matrix.getEntry(index, index);
        for (int i = 0; i < index; i++) {
            result -= pow(resultMatrix.getEntry(i, index), 2);
        }
        return sqrt(result);
    }

    private double getNonDiagonal(RealMatrix matrix, RealMatrix resultMatrix, int rowIndex, int columnIndex) {
        if (columnIndex < rowIndex) {
            return 0;
        } else {
            double result = matrix.getEntry(rowIndex, columnIndex);
            for (int i = 0; i < rowIndex; i++) {
                result -= resultMatrix.getEntry(i, rowIndex) * resultMatrix.getEntry(i, columnIndex);
            }
            return result / resultMatrix.getEntry(rowIndex, rowIndex);
        }
    }

}