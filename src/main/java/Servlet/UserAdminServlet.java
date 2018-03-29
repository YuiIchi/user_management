package Servlet;

import java.io.*;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import DatabaseAccess.UserDatabaseAccess;
import Model.User;

@WebServlet("/User/Admin")
public class UserAdminServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDatabaseAccess userDatabaseAccess = new UserDatabaseAccess();
        List<User> users = userDatabaseAccess.getAllUsers();

        response.setContentType("text/html");
        response.setCharacterEncoding("GB2312");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(
                "<!DOCTYPE html>\n" +
                "\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <title>用户管理</title>\n" +
                "        <script>\n" +
                "            function createXMLHttpRequest() {\n" +
                "                if (window.ActiveXObject) {\n" +
                "                    return new ActiveXObject(\"Microsoft.XMLHTTP\");\n" +
                "                }\n" +
                "                else if (window.XMLHttpRequest) {\n" +
                "                    return new XMLHttpRequest();\n" +
                "                }\n" +
                "            }\n" +
                "\n" +
                "            function deleteUser(id) {\n" +
                "                var xhr = createXMLHttpRequest();\n" +
                "                xhr.open(\"POST\", \"/User/Delete\", false);\n" +
                "                xhr.setRequestHeader(\"Content-type\",\"application/x-www-form-urlencoded\");\n" +
                "                xhr.onreadystatechange = function() {\n" +
                "                    if(xhr.readyState == 4 && xhr.status == 200) {\n" +
                "                        var json = eval(\"(\"+xhr.response.text+\")\");\n" +
                "                        if (json.success = true) {\n" +
                "                            window.location.reload();\n" +
                "                        }\n" +
                "                    }\n" +
                "                };\n" +
                "                xhr.send(\"id=\"+id);\n" +
                "            }" +
                "        </script>" +
                "    </head>\n" +
                "\n" +
                "    <body>\n" +
                "        <table>\n" +
                "            <tr>\n" +
                "                <th>用户ID</th>\n" +
                "                <th>用户名</th>\n" +
                "                <th>密码</th>\n" +
                "                <th>操作</th>\n" +
                "            </tr>\n");

        for (User user : users) {
            printWriter.print(
                    "            <tr>\n" +
                    "                <td>" + user.getId() + "</td>\n" +
                    "                <td>" + user.getName() + "</td>\n" +
                    "                <td>" + user.getPassword() + "</td>\n" +
                    "                <td>\n" +
                    "                    <a href=\"/User/Update?id=" + user.getId() + "\">更新</a>\n" +
                    "                    <a onclick=\"deleteUser(" + user.getId() + ");\" href=\"\">删除</a>\n" +
                    "                </td>\n" +
                    "            </tr>\n");
        }

        printWriter.print(
                "        </table>\n" +
                "        <a href=\"/User/Add\">添加</a>\n" +
                "    </body>\n" +
                "</html>");
    }
}