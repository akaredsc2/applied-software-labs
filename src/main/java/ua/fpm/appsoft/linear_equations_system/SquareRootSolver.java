package ua.fpm.appsoft.linear_equations_system;

import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import static java.lang.Math.*;
import static org.apache.commons.math3.linear.MatrixUtils.createRealMatrix;

public class SquareRootSolver {

    //TODO Check arguments for being valid
    //TODO More tests
    //TODO UI
    public static RealVector solve(RealMatrix matrix, RealVector vector, double precision) {
        if (isValidMatrix(matrix, precision)) {
            RealMatrix upperTriangleMatrix = createUpperTriangleMatrix(matrix);
            RealMatrix bottomTriangleMatrix = upperTriangleMatrix.transpose();
            RealVector bottomVector = runForward(bottomTriangleMatrix, vector);
            return reversalRun(upperTriangleMatrix, bottomVector);
        } else {
            throw new IllegalArgumentException("Invalid matrix!");
        }
    }

    private static RealMatrix createUpperTriangleMatrix(RealMatrix fromMatrix) {
        RealMatrix result = createRealMatrix(fromMatrix.getRowDimension(), fromMatrix.getRowDimension());
        for (int i = 0; i < result.getRowDimension(); i++) {
            for (int j = 0; j < result.getRowDimension(); j++) {
                double entryValue;
                entryValue = (j < i) ? 0.0 : ((i == j) ? getDiagonal(fromMatrix, i) : getNonDiagonal(fromMatrix, i, j));
                result.setEntry(i, j, entryValue);
            }
        }
        return result;
    }

    private static RealVector runForward(RealMatrix matrix, RealVector vector) {
        if (!isBottomTriangleMatrix(matrix, 0.0001)) {
            throw new IllegalArgumentException("Matrix is not upper triangle!");
        }
        RealVector resultVector = MatrixUtils.createRealVector(new double[]{0, 0, 0});

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

    private static RealVector reversalRun(RealMatrix matrix, RealVector vector) {
        if (!isUpperTriangleMatrix(matrix, 0.0001)) {
            throw new IllegalArgumentException("Matrix is not bottom triangle!");
        }
        RealVector resultVector = MatrixUtils.createRealVector(new double[]{0, 0, 0});

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

    private static boolean isValidMatrix(RealMatrix matrix, double precision) {
        return MatrixUtils.isSymmetric(matrix, precision) && new LUDecomposition(matrix).getDeterminant() != 0;
    }

    private static boolean isValidIndexForSquareMatrix(RealMatrix matrix, int index) {
        return index >= 0 || index < matrix.getColumnDimension();
    }

    private static boolean isBottomTriangleMatrix(RealMatrix matrix, double precision) {
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            for (int j = i + 1; j < matrix.getColumnDimension(); j++) {
                if (abs(matrix.getEntry(i, j)) > precision) return false;
            }
        }
        return true;
    }

    private static boolean isUpperTriangleMatrix(RealMatrix matrix, double precision) {
        for (int j = 0; j < matrix.getColumnDimension(); j++) {
            for (int i = j + 1; i < matrix.getRowDimension(); i++) {
                if (abs(matrix.getEntry(i, j)) > precision) return false;
            }
        }
        return true;
    }

    private static double getDiagonal(RealMatrix matrix, int index) {
        if (!isValidIndexForSquareMatrix(matrix, index)) {
            throw new IllegalArgumentException("Index outside matrix dimensions!");
        }
        double result = matrix.getEntry(index, index);
        for (int i = 0; i < index; i++) {
            result -= pow(getNonDiagonal(matrix, i, index), 2);
        }
        return sqrt(result);
    }

    private static double getNonDiagonal(RealMatrix matrix, int rowIndex, int columnIndex) {
        if (!isValidIndexForSquareMatrix(matrix, columnIndex) || !isValidIndexForSquareMatrix(matrix, rowIndex)) {
            throw new IllegalArgumentException("Index outside matrix dimensions!");
        }
        double result = matrix.getEntry(rowIndex, columnIndex);
        for (int i = 0; i < rowIndex; i++) {
            result -= getNonDiagonal(matrix, i, rowIndex) * getNonDiagonal(matrix, i, columnIndex);
        }
        return result / getDiagonal(matrix, rowIndex);
    }

}