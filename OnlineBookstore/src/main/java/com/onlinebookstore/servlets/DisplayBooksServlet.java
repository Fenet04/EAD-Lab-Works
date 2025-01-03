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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/displayBooks")
public class DisplayBooksServlet extends HttpServlet {
    private DBConnectionManager dbConnectionManager;

    @Override
    public void init() throws ServletException {
        dbConnectionManager = new DBConnectionManager();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sql = "SELECT * FROM Books";

        try (Connection connection = dbConnectionManager.openConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h1>All Books</h1>");
            out.println("<table border='1'>");
            out.println("<tr><th>ID</th><th>Title</th><th>Author</th><th>Price</th></tr>");

            while (resultSet.next()) {
                out.println("<tr>");
                out.println("<td>" + resultSet.getInt("id") + "</td>");
                out.println("<td>" + resultSet.getString("title") + "</td>");
                out.println("<td>" + resultSet.getString("author") + "</td>");
                out.println("<td>" + resultSet.getDate("price") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</body></html>");

        } catch (SQLException e) {
            throw new ServletException("Error fetching books", e);
        }
    }
}

