package DatabaseAccess;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

import Model.User;

public class UserDatabaseAccess {
    Map<String, String> databaseProperties = new HashMap<String, String>();

    private Connection connection = null;

    public UserDatabaseAccess() {
        super();

        String databasePropertiesPath = "";
        try {
             databasePropertiesPath = this.getClass().getClassLoader().getResource("database.properties").getPath();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }

        Properties properties = new Properties();
        try {
            InputStream inputStream = new BufferedInputStream(new FileInputStream(databasePropertiesPath));
            properties.load(inputStream);
            Enumeration enumeration = properties.propertyNames();
            while (enumeration.hasMoreElements()) {
                String key = (String)enumeration.nextElement();
                databaseProperties.put(key, properties.getProperty(key));
            }
            inputStream.close();
        }
        catch(Exception exception) {
            exception.printStackTrace();
        }

        try {
            Class.forName(databaseProperties.get("JDBC_DRIVER"));
            connection = DriverManager.getConnection(databaseProperties.get("DB_URL"),
                    databaseProperties.get("USER"), databaseProperties.get("PASSWORD"));
        }
        catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "select * from user;";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");

                users.add(new User(id, name, password));
            }

            resultSet.close();
            statement.close();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        return users;
    }

    public boolean addUser(String name, String password) {
        try {
            String SQL = "insert into user (name, password) values (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            int result = preparedStatement.executeUpdate();

            preparedStatement.close();

            if (result > 0)
                return true;
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    public User getUser(int id) {
        User user = null;

        try {
            String SQL = "select * from user where id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");

                user = new User(id, name, password);
            }

            resultSet.close();
            preparedStatement.close();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        return user;
    }

    public boolean updateUser(int id, String name, String password) {
        try {
            String SQL = "update user set name = ?, password = ? where id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, id);
            int result = preparedStatement.executeUpdate();

            preparedStatement.close();

            if (result > 0)
                return true;
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    public boolean deleteUser(int id) {
        try {
            String SQL = "delete from user where id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();

            preparedStatement.close();

            if (result > 0)
                return true;
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }
}
