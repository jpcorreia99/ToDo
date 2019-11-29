package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {

    public SQLConnection() {
    }

    public Connection getConnection() {
        Connection conn = null;

        String url = "jdbc:mysql://jpcorreia99@127.0.0.1:3306/mydb?" + "user=jpcorreia99";

        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }

        return conn;
    }
}

