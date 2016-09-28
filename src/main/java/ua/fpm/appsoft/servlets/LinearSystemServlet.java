package ua.fpm.appsoft.servlets;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import ua.fpm.appsoft.linear_equations_system.SquareRootSolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class LinearSystemServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            process(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            process(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RealMatrix matrix = MatrixUtils.createRealMatrix(parseMatrix(request));
        RealVector vector = MatrixUtils.createRealVector(parseVector(request));
        try {
            RealVector result = SquareRootSolver.solve(matrix, vector);
            request.setAttribute("result", result.toString());
            getServletContext().getRequestDispatcher("/matrix_result.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("trouble", e.getClass());
            getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private double[][] parseMatrix(HttpServletRequest request) {
        int dimension = Integer.parseInt(request.getParameter("dim"));
        double[][] result = new double[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int row = i + 1;
                int column = j + 1;
                result[i][j] = Double.parseDouble(request.getParameter("mat_" + row + column));
            }
        }
        return result;
    }

    private double[] parseVector(HttpServletRequest request) {
        int dimension = Integer.parseInt(request.getParameter("dim"));
        double[] result = new double[dimension];
        for (int j = 0; j < dimension; j++) {
            int column = j + 1;
            result[j] = Double.parseDouble(request.getParameter("vec_" + column));
        }
        return result;
    }


}
