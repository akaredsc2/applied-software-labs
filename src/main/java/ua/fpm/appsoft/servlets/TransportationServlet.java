package ua.fpm.appsoft.servlets;

import ua.fpm.appsoft.transportation.MinimalElementTransportationSolver;
import ua.fpm.appsoft.util.Parser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(urlPatterns = {"/tr"})
public class TransportationServlet extends HttpServlet {

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
        double[][] costs = Parser.parseMatrix(request, "amountCount", "demandsCount", "costs");
        double[] amounts = Parser.parseVector(request, "amountCount", "amount");
        double[] demands = Parser.parseVector(request, "demandsCount", "demand");

        try {
            MinimalElementTransportationSolver solver = new MinimalElementTransportationSolver(costs, amounts, demands);

            double[][] result = solver.getOptimalSolution();

            request.setAttribute("resultFunctionValue", solver.getTotalCost(result));
            request.setAttribute("resultPlan", Arrays.deepToString(result));
            getServletContext().getRequestDispatcher("/tr_result.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("trouble", e.getClass());
            getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

}
