package ua.fpm.appsoft.servlets;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import ua.fpm.appsoft.linearEquationsSystem.SquareRootSolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.fpm.appsoft.util.Parser.parseMatrix;
import static ua.fpm.appsoft.util.Parser.parseVector;

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            process(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RealMatrix matrix = MatrixUtils.createRealMatrix(parseMatrix(request, "dim", "mat_"));
        RealVector vector = MatrixUtils.createRealVector(parseVector(request, "dim", "vec_"));
        try {
            RealVector result = new SquareRootSolver(matrix, vector).solve();
            request.setAttribute("result", result.toString());
            getServletContext().getRequestDispatcher("/matrix_result.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("trouble", e.getClass());
            getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

}
