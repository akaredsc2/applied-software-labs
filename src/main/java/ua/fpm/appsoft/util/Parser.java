package ua.fpm.appsoft.util;

import javax.servlet.http.HttpServletRequest;

public class Parser {

    public static double[][] parseMatrix(HttpServletRequest request,
                                         String dimensionParameter, String matrixParameter) {
        return parseMatrix(request, dimensionParameter, dimensionParameter, matrixParameter);
    }

    public static double[][] parseMatrix(HttpServletRequest request, String columnDimensionParameter,
                                         String rowDimensionParameter, String matrixParameter) {
        int columnCount = Integer.parseInt(request.getParameter(columnDimensionParameter));
        int rowCount = Integer.parseInt(request.getParameter(rowDimensionParameter));
        double[][] result = new double[columnCount][rowCount];
        for (int i = 0; i < columnCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                int row = i + 1;
                int column = j + 1;
                result[i][j] = Double.parseDouble(request.getParameter(matrixParameter + row + column));
            }
        }
        return result;
    }

    public static double[] parseVector(HttpServletRequest request,
                                       String dimensionParameter, String matrixParameter) {
        int dimension = Integer.parseInt(request.getParameter(dimensionParameter));
        double[] result = new double[dimension];
        for (int j = 0; j < dimension; j++) {
            int column = j + 1;
            result[j] = Double.parseDouble(request.getParameter(matrixParameter + column));
        }
        return result;
    }

}
