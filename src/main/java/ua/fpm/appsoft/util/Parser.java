package ua.fpm.appsoft.util;

import javax.servlet.http.HttpServletRequest;

public class Parser {

    public static double[][] parseMatrix(HttpServletRequest request,
                                         String dimensionParameter, String matrixParameter) {
        int dimension = Integer.parseInt(request.getParameter(dimensionParameter));
        double[][] result = new double[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
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
