package todoApp;

import org.junit.jupiter.api.Test;
import persistence.UserDAO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UsersMapTest {

    @Test
    void sortByUrgency() {

        UserDAO userDAO = new UserDAO();
        userDAO.deleteUser("testUsername");

        UsersMap usersMapTest = new UsersMap();
        try{
            usersMapTest.registerUser("testUsername","testPassword");
        }catch (Exception e){System.out.println(e.getMessage());}


        ToDo td1 = new ToDo(1,  "test1", Urgency.NOT_URGENT, null);
        ToDo td2 = new ToDo(2, "test2", Urgency.NORMAL, null);
        ToDo td3 = new ToDo(3, "test3", Urgency.URGENT, null);
        List<ToDo> actualArray = new ArrayList<>();
        actualArray.add(td3);
        actualArray.add(td2);
        actualArray.add(td1);

        usersMapTest.addToDo("test1", Urgency.NOT_URGENT, null);
        usersMapTest.addToDo("test2", Urgency.NORMAL, null);
        usersMapTest.addToDo("test3", Urgency.URGENT, null);
        Set<ToDo> testSet = usersMapTest.sortByUrgency();
        List<ToDo> testArray = new ArrayList<>(testSet);

        for (int i = 0; i<actualArray.size(); i++){
            assertEquals(actualArray.get(i).getUrgency().urgencyValue, testArray.get(i).getUrgency().urgencyValue);
        }
    }

    @Test
    void sortByLimitDate() {

        UserDAO userDAO = new UserDAO();
        userDAO.deleteUser("testUsername");

        UsersMap usersMapTest = new UsersMap();
        try{
            usersMapTest.registerUser("testUsername","testPassword");
        }catch (Exception e){System.out.println(e.getMessage());}


        ToDo td1 = new ToDo(1,  "test1", Urgency.NORMAL, LocalDateTime.now());
        ToDo td2 = new ToDo(2, "test2", Urgency.NORMAL, LocalDateTime.now().minusDays(2));
        ToDo td3 = new ToDo(3, "test3", Urgency.NORMAL, LocalDateTime.now().plusDays(3));
        ToDo td4 = new ToDo(4, "test3", Urgency.NORMAL,null);
        List<ToDo> actualArray = new ArrayList<>();
        actualArray.add(td2);
        actualArray.add(td1);
        actualArray.add(td3);
        actualArray.add(td4);

        usersMapTest.addToDo("test1", Urgency.NORMAL, LocalDateTime.now());
        usersMapTest.addToDo("test2", Urgency.NORMAL, LocalDateTime.now().minusDays(2));
        usersMapTest.addToDo( "test3", Urgency.NORMAL, LocalDateTime.now().plusDays(3));
        usersMapTest.addToDo( "test4", Urgency.NORMAL,null);
        Set<ToDo> testSet = usersMapTest.sortByUrgency();
        List<ToDo> testArray = new ArrayList<>(testSet);

        for (int i = 0; i<actualArray.size(); i++){
            assertEquals(actualArray.get(i).getUrgency().urgencyValue, testArray.get(i).getUrgency().urgencyValue);
        }
    }

    @Test
    void sortByCreationDate() {

        UserDAO userDAO = new UserDAO();
        userDAO.deleteUser("testUsername");

        UsersMap usersMapTest = new UsersMap();
        try{
            usersMapTest.registerUser("testUsername","testPassword");
        }catch (Exception e){System.out.println(e.getMessage());}


        ToDo td1 = new ToDo(1,  "test1", Urgency.NORMAL,null);
        ToDo td2 = new ToDo(2, "test2", Urgency.NORMAL, null);
        ToDo td3 = new ToDo(3, "test3", Urgency.NORMAL, null);
        List<ToDo> actualArray = new ArrayList<>();
        actualArray.add(td1);
        actualArray.add(td2);
        actualArray.add(td3);


        usersMapTest.addToDo("test1", Urgency.NORMAL,null);
        usersMapTest.addToDo("test2", Urgency.NORMAL,null);
        usersMapTest.addToDo( "test3", Urgency.NORMAL, null);
        Set<ToDo> testSet = usersMapTest.sortByCreationDate();
        List<ToDo> testArray = new ArrayList<>(testSet);

        for (int i = 0; i<actualArray.size(); i++){
            assertEquals(actualArray.get(i).getUrgency().urgencyValue, testArray.get(i).getUrgency().urgencyValue);
        }
    }
}