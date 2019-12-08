package persistence;

import todoApp.Status;
import todoApp.ToDo;
import todoApp.Urgency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ToDoDAO {

    public ToDoDAO(){}

    public void insertToDo(ToDo td, int idUser) {
        SQLConnection sqlConnection = new SQLConnection();
        Connection connection = sqlConnection.getConnection();
        if (connection != null) {
            int globalId = td.getGlobalId();
            int relativeId = td.getRelativeId();
            String task = td.getTask();
            Status status = td.getStatus();
            LocalDateTime creationDate = td.getCreationDate();
            LocalDateTime startDate = td.getStartDate();
            LocalDateTime limitDate = td.getLimitDate();
            LocalDateTime completionDate = td.getCompletionDate();
            Urgency urgency = td.getUrgency();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss,dd/MM/yyyy");

            String creationDateString =  creationDate.format(formatter); //to handle SQL sintax and permit both nulls and strings

            String startDateString = null;
            if (startDate != null) {
                startDateString = startDate.format(formatter) ;
            }

            String limitDateString = null;
            if (limitDate != null) {
                limitDateString =limitDate.format(formatter);
            }

            String completionDateString = null;
            if (completionDate != null) {
                completionDateString =  completionDate.format(formatter);
            }

            try {
                PreparedStatement posted = connection.prepareStatement("INSERT INTO todo " +
                        "Values (?,?,?,?,?,?,?,?,?,?)");

                posted.setInt(1,globalId);
                posted.setInt(2, relativeId);
                posted.setString(3, task);
                posted.setString(4, creationDateString);
                posted.setString(5, startDateString);
                posted.setString(6, limitDateString);
                posted.setString(7, completionDateString);
                posted.setString(8, status.toString());
                posted.setString(9, urgency.toString());
                posted.setInt(10, idUser);

                posted.executeUpdate();
            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }

    public Map<Integer, ToDo> retrieveToDos(int userId) {
        Map<Integer, ToDo> ret = new HashMap<>();
        SQLConnection sqlConnection = new SQLConnection();
        Connection connection = sqlConnection.getConnection();
        if (connection != null) {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM todo WHERE userId = ?");
                statement.setInt(1,userId);
                ResultSet rs = statement.executeQuery();
                ret = processToDoResultSet(rs);
            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
        return ret;
    }

    // processes the resultSet given from the retrieveToDos method
    public Map<Integer, ToDo> processToDoResultSet(ResultSet rs) {
        Map<Integer, ToDo> ret = new HashMap<>();
        int globalId;
        int relativeId;
        String task;
        LocalDateTime creationDate;
        LocalDateTime startDate;
        LocalDateTime limitDate;
        LocalDateTime completionDate;
        Status status;
        Urgency urgency;
        SQLConnection sqlConnection = new SQLConnection();
        Connection connection = sqlConnection.getConnection();
        if (connection != null) {
            try {
                while (rs.next()) {
                    globalId = rs.getInt(1);
                    relativeId = rs.getInt(2);
                    task = rs.getString(3);
                    creationDate = processDateString(rs.getString(4));
                    startDate = processDateString(rs.getString(5));
                    limitDate = processDateString(rs.getString(6));
                    completionDate = processDateString(rs.getString(7));
                    status = processStatusString(rs.getString(8));
                    urgency = processUrgencyString(rs.getString(9));
                    ToDo td = new ToDo(globalId, relativeId, task, creationDate, startDate, limitDate, completionDate, status, urgency);
                    ret.put(relativeId, td);
                }
            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }

        return ret;
    }

    public LocalDateTime processDateString(String date) {
        if (date == null) return null;
        String[] allTokens = date.split(",");
        String[] dayTokens = allTokens[0].split(":");
        String[] dateTokens = allTokens[1].split("/");
        int hour = Integer.parseInt(dayTokens[0]);
        int minute = Integer.parseInt(dayTokens[1]);
        int second = Integer.parseInt(dayTokens[2]);
        int day = Integer.parseInt(dateTokens[0]);
        int month = Integer.parseInt(dateTokens[1]);
        int year = Integer.parseInt(dateTokens[2]);
        return LocalDateTime.of(year, month, day, hour, minute, second);
    }

    public Status processStatusString(String status) {
        if (status.equals("Not_started")) return Status.Not_started;
        if (status.equals("In_progress")) return Status.In_progress;
        return Status.Completed;
    }

    public Urgency processUrgencyString(String urgency) {
        if (urgency.equals("NOT_URGENT")) return Urgency.NOT_URGENT;
        if (urgency.equals("NORMAL")) return Urgency.NORMAL;
        return Urgency.URGENT;
    }


    public void updateToDo(ToDo td, int userId) {
        SQLConnection sqlConnection = new SQLConnection();
        Connection connection = sqlConnection.getConnection();
        if(connection!=null) {
            try {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM todo WHERE globalId =?");
                statement.setInt(1, td.getGlobalId());

                statement.executeUpdate();
                insertToDo(td, userId);
            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }

    public void deleteToDos(int idUser){
        SQLConnection sqlConnection = new SQLConnection();
        Connection connection = sqlConnection.getConnection();
        try{
            PreparedStatement statement = connection.prepareStatement("DELETE FROM todo WHERE userId =?");
            statement.setInt(1, idUser);
            statement.executeUpdate();
        }catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }
}
