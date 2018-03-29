package Servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import DatabaseAccess.UserDatabaseAccess;

@WebServlet("/User/Delete")
public class DeleteUserServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDatabaseAccess userDatabaseAccess = new UserDatabaseAccess();
        boolean result = userDatabaseAccess.deleteUser(Integer.parseInt(request.getParameter("id")));

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String json = "{\"success\": true}";

        PrintWriter printWriter = response.getWriter();
        printWriter.print(json);
    }
}
