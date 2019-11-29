package todoApp;

import Services.CalendarAccess;
import persistence.ToDoDAO;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class User {
    // to set an id for SQL primary key
    private static int userCount = 0;

    private int id;
    private String username;
    private String password;
    private Map<Integer, ToDo> mapOfToDo;


    public User(String username, String password) {
        userCount += 1;
        this.id = userCount;
        this.username = username;
        this.password = password;
        this.mapOfToDo = new HashMap<>();

    }

    public User(int id, String username, String password, Map<Integer, ToDo> mapOfToDo) {
        userCount += 1;
        this.id = id;
        this.username = username;
        this.password = password;
        this.mapOfToDo = mapOfToDo;
    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public Map<Integer, ToDo> getMapOfToDo() {
        return this.mapOfToDo;
    }


    public boolean containsToDo(int idToDo) {
        return mapOfToDo.containsKey(idToDo);
    }

    public void addToDo(String task, Urgency urgency, LocalDateTime limitDate) {
        ToDo td = new ToDo(this.mapOfToDo.size() + 1, task, urgency, limitDate);
        this.mapOfToDo.put(mapOfToDo.size() + 1, td);
        ToDoDAO toDoDAO = new ToDoDAO();
        toDoDAO.insertToDo(td, this.id);

        if(limitDate!=null) {
            CalendarAccess calendarAccess = new CalendarAccess();

            try {
                calendarAccess.insertEvent(td);
            } catch (IOException | GeneralSecurityException e) {
                System.out.println(e.getMessage());
                // e.printStackTrace();
            }
        }
    }

    public void startToDo(int toDoId) {
        ToDo td = mapOfToDo.get(toDoId);
        td.start();
        ToDoDAO toDoDAO = new ToDoDAO();
        toDoDAO.updateToDo(td, this.id);
    }

    public void completeToDo(int toDoId) {
        ToDo td = mapOfToDo.get(toDoId);
        td.complete();
        ToDoDAO toDoDAO = new ToDoDAO();
        toDoDAO.updateToDo(td, this.id);
    }

    public void changeTask(int idToDo, String task) {
        ToDo td = mapOfToDo.get(idToDo);
        td.setTask(task);
        ToDoDAO toDoDAO = new ToDoDAO();
        toDoDAO.updateToDo(td, this.id);
    }

    public void changeLimitDate(int idToDo, LocalDateTime newLimitDate) {
        ToDo td = mapOfToDo.get(idToDo);
        td.setLimitDate(newLimitDate);
        ToDoDAO toDoDAO = new ToDoDAO();
        toDoDAO.updateToDo(td, this.id);
    }

    public void setUrgency(int idToDo, Urgency urgency) {
        ToDo td = mapOfToDo.get(idToDo);
        td.setUrgency(urgency);
        ToDoDAO toDoDAO = new ToDoDAO();
        toDoDAO.updateToDo(td, this.id);
    }

    public int getNumOfUncompletedTasks() {
        return (int) this.mapOfToDo.values().stream()
                .filter(td ->  td.getStatus() != Status.Completed).count();
    }

    public Set<ToDo> sortByCreationDate() {
        Set<ToDo> toDoSet = new TreeSet<>(new CreationDateComparator());
        toDoSet.addAll(this.mapOfToDo.values());
        return toDoSet;
    }


    public Set<ToDo> sortByUrgency() {
        Set<ToDo> toDoSet = new TreeSet<>(new UrgencyComparator());
        toDoSet.addAll(this.mapOfToDo.values());
        return toDoSet;
    }

    /**
     * Returns a treeset ordered by the todoApp.ToDo's limit date
     * todoApp.ToDo's who are already copmpleted are displayed last
     */

    public Set<ToDo> sortByLimitDate() {
        Set<ToDo> toDoSet = new TreeSet<>(new LimitDateComparator());
        toDoSet.addAll(this.mapOfToDo.values());
        return toDoSet;
    }

    public ToDo retrieveToDo(int idToDo) {
        return this.mapOfToDo.get(idToDo);
    }

}


