package Servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import DatabaseAccess.UserDatabaseAccess;
import Model.User;

@WebServlet("/User/Add")
public class AddUserServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("GB2312");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(
                "<!DOCTYPE html>\n" +
                "\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <title>添加用户</title>\n" +
                "    </head>\n" +
                "\n" +
                "    <body>\n" +
                "        <form action=\"/User/Add\" method=\"post\">\n" +
                "            <p>\n" +
                "                <label for=\"name_input\">用户名:</label>\n" +
                "                <input type=\"text\" id=\"name_input\" name=\"name\" />\n" +
                "            </p>\n" +
                "            <p>\n" +
                "                <label for=\"password_input\">密码:</label>\n" +
                "                <input type=\"text\" id=\"password_input\" name=\"password\" />\n" +
                "            </p>\n" +
                "            <input type=\"submit\" value=\"添加\" />\n" +
                "        </form>" +
                "    </body>\n" +
                "</html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDatabaseAccess userDatabaseAccess = new UserDatabaseAccess();
        boolean result = userDatabaseAccess.addUser(request.getParameter("name"), request.getParameter("password"));

        response.sendRedirect("/User/Admin");
    }
}
