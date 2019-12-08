package persistence;

import todoApp.ToDo;
import todoApp.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserDAO {

    public UserDAO() {
    }

    public void insertUser(User user) {
        SQLConnection sqlconnection = new SQLConnection();
        ToDoDAO toDoDAO = new ToDoDAO();

        Connection conn = sqlconnection.getConnection();
        if (conn != null) {
            try {
                PreparedStatement posted = conn.prepareStatement("INSERT INTO user VALUES (?,?,?)");
                posted.setInt(1, user.getId());
                posted.setString(2, user.getUsername());
                posted.setString(3, user.getPassword());

                for (ToDo td : user.getMapOfToDo().values()) {
                    toDoDAO.insertToDo(td, user.getId());
                }
                posted.executeUpdate();
            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }


    public Map<String, User> retrieveData() {
        Map<String, User> allUsers = new HashMap<>();
        SQLConnection sqlConnection = new SQLConnection();
        Connection connection = sqlConnection.getConnection();
        if (connection != null) {
            try {
                PreparedStatement statement = connection.prepareStatement("Select * from user");
                ResultSet rs = statement.executeQuery();
                allUsers = processUserResultSet(rs);
            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }

        return allUsers;
    }

    public Map<String, User> processUserResultSet(ResultSet rs) {

        Map<String, User> ret = new HashMap<>();

        try {
            int id;
            String username;
            String password;
            Map<Integer, ToDo> mapOfToDo;
            ToDoDAO toDoDAO = new ToDoDAO();
            while (rs.next()) {
                id = rs.getInt(1);
                username = rs.getString(2);
                password = rs.getString(3);
                mapOfToDo = toDoDAO.retrieveToDos(id);
                User user = new User(id, username, password, mapOfToDo);
                ret.put(username, user);
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        return ret;
    }

    public void deleteUser(String username) {
        SQLConnection sqlConnection = new SQLConnection();
        Connection connection = sqlConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE username =?");
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                int idUser = rs.getInt(1);
                ToDoDAO toDoDAO = new ToDoDAO();
                toDoDAO.deleteToDos(idUser);
                statement = connection.prepareStatement("DELETE FROM user WHERE username =?");
                statement.setString(1, username);
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }

}


