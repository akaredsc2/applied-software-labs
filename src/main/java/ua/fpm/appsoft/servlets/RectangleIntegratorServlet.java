package ua.fpm.appsoft.servlets;

import org.apache.commons.math3.geometry.euclidean.oned.Interval;
import ua.fpm.appsoft.numericIntegration.NumericIntegrator;
import ua.fpm.appsoft.numericIntegration.RightRectangleIntegrator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/num_integr"})
public class RectangleIntegratorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            process(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            process(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final String function = request.getParameter("function");
        final double from = Double.parseDouble(request.getParameter("from"));
        final double to = Double.parseDouble(request.getParameter("to"));
        final int steps = Integer.parseInt(request.getParameter("steps"));

        NumericIntegrator integrator;
        double result;
        try {
            if (from < to) {
                integrator = new RightRectangleIntegrator(function, new Interval(from, to), steps);
                result = integrator.integrate();
            } else {
                integrator = new RightRectangleIntegrator(function, new Interval(to, from), steps);
                result = -integrator.integrate();
            }
            request.setAttribute("result", result);
            getServletContext().getRequestDispatcher("/integr_result.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("trouble", e.getClass());
            getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

}
