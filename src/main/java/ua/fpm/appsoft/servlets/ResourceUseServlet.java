package ua.fpm.appsoft.servlets;

import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optim.linear.Relationship;
import ua.fpm.appsoft.linearProgramming.ResourceUseSolver;
import ua.fpm.appsoft.util.Parser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResourceUseServlet extends HttpServlet {

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
        double[][] resourceConsumptions = Parser.parseMatrix(request, "resourceCount", "productCount", "res_per_prod");
        double[] resourceAmounts = Parser.parseVector(request, "resourceCount", "res_amount");
        double[] productProceeds = Parser.parseVector(request, "productCount", "proceed_from_prod");

        List<LinearConstraint> constraintList = formConstraints(resourceConsumptions, resourceAmounts);
        LinearObjectiveFunction function = new LinearObjectiveFunction(productProceeds, 0);

        try {
            PointValuePair result = ResourceUseSolver.optimize(function, constraintList);

            request.setAttribute("resultFunctionValue", result.getValue());
            request.setAttribute("resultPoint", Arrays.toString(result.getPoint()));

            getServletContext().getRequestDispatcher("/linear_programming_result.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("trouble", e.getClass());
            getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private List<LinearConstraint> formConstraints(double[][] resourceConsumptions, double[] resourceAmounts) {
        List<LinearConstraint> result = new ArrayList<>();
        for (int i = 0; i < resourceConsumptions.length; i++) {
            result.add(new LinearConstraint(resourceConsumptions[i], Relationship.LEQ, resourceAmounts[i]));
        }
        return result;
    }

}
