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

@WebServlet("/deleteBook")
public class DeleteBookServlet extends HttpServlet {
    private DBConnectionManager dbConnectionManager;

    @Override
    public void init() throws ServletException {
        dbConnectionManager = new DBConnectionManager();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookId = request.getParameter("id");

        String sql = "DELETE FROM Books WHERE id = ?";

        try (Connection connection = dbConnectionManager.openConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, Integer.parseInt(bookId));
            int rowsAffected = statement.executeUpdate();

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            if (rowsAffected > 0) {
                out.println("<h3>Book Deleted Successfully!</h3>");
            } else {
                out.println("<h3>No Book Found with ID: " + bookId + "</h3>");
            }
            out.println("</body></html>");

        } catch (SQLException e) {
            throw new ServletException("Error deleting book", e);
        }
    }
}

