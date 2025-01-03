//Name : Fenet Mulugeta
package com.onlinebookstore.servlets;

import com.onlinebookstore.db.DBConnectionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/registerBook")
public class BookRegistrationServlet extends HttpServlet {
    private DBConnectionManager dbConnectionManager;

    @Override
    public void init() throws ServletException {
        dbConnectionManager = new DBConnectionManager();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String price = request.getParameter("price");

        String sql = "INSERT INTO Tasks (title, author, price) VALUES (?, ?, ?)";

        try (Connection connection = dbConnectionManager.openConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, title);
            statement.setString(2, author);
            statement.setString(3, price);

            statement.executeUpdate();
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h3>Book Registered Successfully!</h3>");
            out.println("</body></html>");

        } catch (SQLException e) {
            throw new ServletException("Error registering book", e);
        }
    }
}

