package ua.fpm.appsoft.servlets;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealVector;
import ua.fpm.appsoft.unconditionalOptimization.HookeJeevesOptimizer;
import ua.fpm.appsoft.unconditionalOptimization.UnconditionalOptimizer;
import ua.fpm.appsoft.util.Parser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnconditionalOptimizationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            process(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            process(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String function = request.getParameter("function");
        final double precision = Double.parseDouble(request.getParameter("precision"));
        final double increment = Double.parseDouble(request.getParameter("increment"));
        final double acceleration = Double.parseDouble(request.getParameter("acceleration"));
        final RealVector initPoint = MatrixUtils.createRealVector(Parser.parseVector(request, "dim", "vec_"));

        try {
            UnconditionalOptimizer optimizer =
                    new HookeJeevesOptimizer(function, initPoint, increment, precision, acceleration);

            double resultFunctionValue = optimizer.optimize();
            request.setAttribute("resultFunctionValue", resultFunctionValue);

            RealVector resultPoint = optimizer.getOptimalPoint();
            request.setAttribute("resultPoint", resultPoint.toString());

            getServletContext().getRequestDispatcher("/uncond_opt_result.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("trouble", e.getClass());
            getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

}
