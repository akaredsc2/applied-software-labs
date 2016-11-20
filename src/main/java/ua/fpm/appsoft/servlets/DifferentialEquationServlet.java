package ua.fpm.appsoft.servlets;

import org.apache.commons.math3.geometry.euclidean.oned.Interval;
import ua.fpm.appsoft.differentialEquations.NumericDifferentialEquationSolver;
import ua.fpm.appsoft.differentialEquations.RungeKuttaThirdOrderMethodSolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(urlPatterns = {"/diff_eq"})
public class DifferentialEquationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String function = request.getParameter("function");
        final double from = Double.parseDouble(request.getParameter("from"));
        final double to = Double.parseDouble(request.getParameter("to"));
        final int steps = Integer.parseInt(request.getParameter("steps"));
        final double xZero = Double.parseDouble(request.getParameter("xZero"));
        final double yZero = Double.parseDouble(request.getParameter("yZero"));

        try {
            NumericDifferentialEquationSolver solver = new RungeKuttaThirdOrderMethodSolver(function, new Interval(from, to), steps, xZero, yZero);
            Double[] result = solver.solve();
            request.setAttribute("result", Arrays.toString(result));
            getServletContext().getRequestDispatcher("/diff_eq_result.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("trouble", e.getClass());
            getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

}
