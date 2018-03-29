package Servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import DatabaseAccess.UserDatabaseAccess;
import Model.User;

@WebServlet("/User/Update")
public class UpdateUserServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDatabaseAccess userDatabaseAccess = new UserDatabaseAccess();
        int id = Integer.parseInt(request.getParameter("id"));
        User user = userDatabaseAccess.getUser(id);

        if (user != null) {
            response.setContentType("text/html");
            response.setCharacterEncoding("GB2312");
            PrintWriter printWriter = response.getWriter();
            printWriter.print(
                    "<!DOCTYPE html>\n" +
                    "\n" +
                    "<html>\n" +
                    "    <head>\n" +
                    "        <meta charset=\"UTF-8\">\n" +
                    "        <title>更新用户</title>\n" +
                    "    </head>\n" +
                    "\n" +
                    "    <body>\n" +
                    "        <form action=\"/User/Update\" method=\"post\">\n" +
                    "            <input type=\"hidden\" id=\"id_input\" name=\"id\" value=\"" + id + "\"" +
                    "            <p>\n" +
                    "                <label for=\"name_input\">用户名:</label>\n" +
                    "                <input type=\"text\" id=\"name_input\" name=\"name\" value=\"" +
                    user.getName() + "\" />\n" +
                    "            </p>\n" +
                    "            <p>\n" +
                    "                <label for=\"password_input\">密码:</label>\n" +
                    "                <input type=\"text\" id=\"password_input\" name=\"password\" value=\"" +
                    user.getPassword() + "\" />\n" +
                    "            </p>\n" +
                    "            <input type=\"submit\" value=\"更新\" />\n" +
                    "        </form>" +
                    "    </body>\n" +
                    "</html>");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDatabaseAccess userDatabaseAccess = new UserDatabaseAccess();
        boolean result = userDatabaseAccess.updateUser(Integer.parseInt(request.getParameter("id")),
                request.getParameter("name"), request.getParameter("password"));

        response.sendRedirect("/User/Admin");
    }
}
