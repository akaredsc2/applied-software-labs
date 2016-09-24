package ua.fpm.appsoft.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LinearSystemServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            process(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int matrixSize = Integer.parseInt(request.getParameter("matrix_size"));
        System.out.println(matrixSize);
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }

}
