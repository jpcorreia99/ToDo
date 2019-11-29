package todoApp;

import persistence.UserDAO;
import todoApp.exceptions.InvalidCredentialsException;
import todoApp.exceptions.UsernameAlreadyInUseException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class UsersMap {
    /**
     * Map containing all the apllication's users
     */
    private Map<String, User> allUsers;
    /**
     * current Logged in user
     */
    private User currentUser;



    public UsersMap() {
        UserDAO userDAO = new UserDAO();
        this.allUsers = new HashMap<>();
        this.allUsers = userDAO.retrieveData();
    }

    /**
     * Registers an user given it's username and password
     */
    public void registerUser(String username, String pasword) throws UsernameAlreadyInUseException {
        if (allUsers.containsKey(username)) {
            throw new UsernameAlreadyInUseException(username + " already in use");
        } else {
            User newUser = new User(username, pasword);
            allUsers.put(username, newUser);
            this.currentUser = newUser;
            UserDAO userDAO = new UserDAO();
            userDAO.insertUser(newUser);
        }
    }

    /**
     * Logs in given a username, by fetching it from the allUsers hashmap and atributing the user it to the currentUser
     */
    public void login(String username, String password) throws InvalidCredentialsException {
        if (!allUsers.containsKey(username)) {
            throw new InvalidCredentialsException(username + " does not exist");
        } else {
            User user = allUsers.get(username);
            if (!user.getPassword().equals(password)) {
                throw new InvalidCredentialsException("Wrong password");
            } else {
                this.currentUser = user;
            }
        }
    }

    /* Given it's constructor arguments, adds a new task to the current user*/
    public void addToDo(String task, Urgency urgency, LocalDateTime limitDate) {
        this.currentUser.addToDo(task, urgency, limitDate);
    }

    public ToDo retrieveToDo(int idToDo) {
        return this.currentUser.retrieveToDo(idToDo);
    }

    /* Given it's id, marks a task as started*/
    public void startToDo(int idToDo) {
        this.currentUser.startToDo(idToDo);
    }

    /*Given it's id, masrks a taks as completed*/
    public void completeToDo(int idToDo) {
        this.currentUser.completeToDo(idToDo);
    }

    /*Check's if there's a todoApp.ToDo with the given Id*/
    public boolean containsToDo(int idToDo) {
        return this.currentUser.containsToDo(idToDo);
    }

    /* Given it's id, changes the limit date of a task*/
    public void changeLimitDate(int idToDo, LocalDateTime limitDate) {
        this.currentUser.changeLimitDate(idToDo, limitDate);
    }

    /* Given it's id, changes the urgency of a task*/
    public void changeUrgency(int idToDo, Urgency urgency) {
        this.currentUser.setUrgency(idToDo, urgency);
    }

    public void changeTask(int idToDo, String task) { this.currentUser.changeTask(idToDo, task); }

    public int getNumUncompletedTasks(){
      return this.currentUser.getNumOfUncompletedTasks();
    }

    public Set<ToDo> sortByCreationDate() {
        return this.currentUser.sortByCreationDate();
    }

    public Set<ToDo> sortByLimitDate() {
        return this.currentUser.sortByLimitDate();
    }

    public Set<ToDo> sortByUrgency(){
        return this.currentUser.sortByUrgency();
    }

}
