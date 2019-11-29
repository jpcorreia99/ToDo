package view;


import todoApp.ToDo;
import todoApp.Urgency;
import todoApp.UsersMap;
import todoApp.exceptions.InvalidCredentialsException;
import todoApp.exceptions.UsernameAlreadyInUseException;

import Services.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class Menus {

    public static UsersMap users;

    public static void initApp() {
        System.out.println("Loading data, please wait a second...");
        users = new UsersMap();
        firstMenu();
    }

    public static void firstMenu() {
/*
       try{
            users.registerUser("a", "b");
            users.registerUser("b", "b");
            users.registerUser("c", "b");
            users.registerUser("d", "b");
            users.registerUser("e", "b");
            users.login("a", "b");
       } catch (UsernameAlreadyInUseException e) {
            System.out.println(e.getMessage());
        } catch (InvalidCredentialsException e){
            System.out.println(e.getMessage());
        }


        users.addToDo("criar algo1", Urgency.NORMAL, null);
        users.addToDo("criar algo2", Urgency.NOT_URGENT, LocalDateTime.now());
        users.addToDo("criar algo3", Urgency.URGENT, null);
        users.addToDo("criar algo4", Urgency.NORMAL, null);
        users.addToDo("criar algo5", Urgency.NOT_URGENT, LocalDateTime.now());
        users.addToDo("criar algo6", Urgency.URGENT, null);

        users.startToDo(2);
        users.completeToDo(4);
        users.changeUrgency(5, Urgency.NORMAL);
        users.startToDo(5);
        users.changeLimitDate(5, LocalDateTime.now().plusDays(3));
        users.changeLimitDate(5, LocalDateTime.now().minusDays(2));


        try {
            users.login("b", "b");
        } catch (InvalidCredentialsException e){
            System.out.println(e.getMessage());
        }

        users.addToDo("criar algo7", Urgency.NOT_URGENT, LocalDateTime.now().plusDays(1));

*/

        int opt = -1;
        while (opt != 0) {
            ScannerWrapper sw = new ScannerWrapper();
            sw.cleanScreen();
            System.out.print("1-Register user\n2-Login\n0-Exit\n\nOption: ");
            opt = sw.readInt("Invalid option", 0, 2);
            if (opt == 1) {
                registerMenu();
            } else if (opt == 2) {
                loginMenu();
            }
            if (opt != 0) {
                toDoMainMenu();
            }
        }
    }

    public static void registerMenu() {
        boolean ok = false;
        ScannerWrapper sw = new ScannerWrapper();
        sw.cleanScreen();
        while (!ok) {
            System.out.print("Username: ");
            String username = sw.readString("Invalid username");
            System.out.print("Password: ");
            String password = sw.readString("Invalid password");
            try {
                users.registerUser(username, password);
                ok = true;
            } catch (UsernameAlreadyInUseException e) {
                System.out.println(e.getMessage() + "\n");
            }
        }
    }

    public static void loginMenu() {
        ScannerWrapper sw = new ScannerWrapper();
        sw.cleanScreen();
        boolean ok = false;
        while (!ok) {
            System.out.print("Username: ");
            String username = sw.readString("Invalid username");
            System.out.print("Password: ");
            String password = sw.readString("Invalid password");
            try {
                users.login(username, password);
                ok = true;
            } catch (InvalidCredentialsException e) {
                System.out.println(e.getMessage() + "\n");
                System.out.println("Do you wish to register first(Y/N)?");
                String opt = sw.readString("Invalid String");
                if (opt.equals("y") || opt.equals("Y")) {
                    registerMenu();
                    ok = true;
                }
            }
        }
    }

    public static void toDoMainMenu() {
        int opt = -1;
        ScannerWrapper sw = new ScannerWrapper();
        sw.cleanScreen();
        while (opt != 0) {
            System.out.println("Welcome, you have " + users.getNumUncompletedTasks() + " uncompleted tasks.\n\n");
            System.out.println("1 - Add ToDo\n" +
                    "2 - Check To Do List\n" +
                    "0 - Sign out");
            opt = sw.readInt("Invalid option", 0, 2);
            if (opt == 1) {
                toDoCreationMenu();
            } else if (opt == 2) {
                toDoSelectionMenu();
            }
        }
    }

    public static void toDoCreationMenu() {
        ScannerWrapper sw = new ScannerWrapper();
        sw.cleanScreen();
        boolean ok = false;
        String task = "";
        while (!ok) {
            System.out.print("Task (max 255 characters): ");
            task = sw.readString("Invalid task");
            if (task.length() > 255) {
                System.out.println("String bigger than 255 characters");
            } else {
                ok = true;
            }
        }

        System.out.println("Urgency: \n\n" +
                "1 - Not Urgent\n" +
                "2 - Normal\n" +
                "3 - Urgent");
        int opt = sw.readInt("invalid option", 1, 3);
        Urgency urgency = null;
        switch (opt) {
            case 1:
                urgency = Urgency.NOT_URGENT;
                break;
            case 2:
                urgency = Urgency.NORMAL;
                break;
            case 3:
                urgency = Urgency.URGENT;
                break;
        }

        LocalDateTime limitDate = null;
        System.out.println("Limit date:\n\n" +
                "1 - In a day\n" +
                "2 - In a week\n" +
                "3 - In a month\n" +
                "4 - Custom\n" +
                "0 - Do not set");
        opt = sw.readInt("Invalid option", 0, 4);
        switch (opt) {
            case 1:
                limitDate = LocalDateTime.now().plusDays(1);
                break;
            case 2:
                limitDate = LocalDateTime.now().plusDays(7);
                break;
            case 3:
                limitDate = LocalDateTime.now().plusMonths(1);
                break;
            case 4:
                limitDate = sw.readDate();
                break;
        }
        users.addToDo(task, urgency, limitDate);
    }

    public static void toDoSelectionMenu() {
        ScannerWrapper sw = new ScannerWrapper();
        sw.cleanScreen();
        System.out.println("1 - Sort by creation date");
        System.out.println("2 - Sort by limit date");
        System.out.println("3 - Sort by urgency");
        System.out.println("0 - Go back");
        int chosenToDoId = 0;
        int opt = sw.readInt("Invalid option", 0, 3);
        switch (opt) {
            case 1:
                chosenToDoId = chooseToDoMenu(users.sortByCreationDate());
                break;
            case 2:
                chosenToDoId = chooseToDoMenu(users.sortByLimitDate());
                break;
            case 3:
                chosenToDoId = chooseToDoMenu(users.sortByUrgency());
                break;
        }
        if (chosenToDoId != 0) { // because 0 signals to go back
            ToDoManipulationMenu(chosenToDoId);
        }
    }

    public static int chooseToDoMenu(Set<ToDo> setToDos) {
        ToDoPaginator toDoPaginator = new ToDoPaginator(setToDos);
        String opt = "-1";
        int idToDo = -1;
        ScannerWrapper sw = new ScannerWrapper();
        while (opt.equals("-1") || opt.equals(".") || opt.equals(",")) {
            sw.cleanScreen();
            List<String> list = toDoPaginator.getCurrentPageList();
            for (String s : list) {
                System.out.println(s);
            }
            System.out.println("Showing page " + (toDoPaginator.getCurrentPage() + 1) + " of " + toDoPaginator.getNumPages());
            System.out.println("Insert the Todo's ID you want to check, 0 to go back, ',' to go to the page before, '.' to go to the next page\n");
            System.out.print("\nOPTION: ");
            opt = sw.readString("Invalid option");

            if (opt.equals(",")) {
                toDoPaginator.beforePage();
            } else if (opt.equals(".")) {
                toDoPaginator.nextPage();
            } else {
                try {
                    idToDo = Integer.parseInt(opt);
                    if (idToDo != 0) {
                        if (!users.containsToDo(idToDo)) {
                            System.out.println("This id does not correspond to any To Do");
                            System.out.println("Press ENTER to continue");
                            sw.readString("Invalid prompt");
                            opt = "-1";
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Number");
                    System.out.println("Press ENTER to continue");
                    sw.readString("Invalid prompt");
                    opt = "-1";
                }
            }
        }
        return idToDo;
    }

    public static void ToDoManipulationMenu(int idToDo) {
        ScannerWrapper sw = new ScannerWrapper();
        sw.cleanScreen();
        ToDo td = users.retrieveToDo(idToDo);
        System.out.println(td.toString());
        System.out.println("1 - Mark as Started");
        System.out.println("2 - Mark as completed");
        System.out.println("3 - Change limit date");
        System.out.println("4 - Change urgency");
        System.out.println("5 - Change task");
        System.out.println("0 - Go back");
        int opt = sw.readInt("Invalid option", 0, 5);
        switch (opt) {
            case 1:
                users.startToDo(idToDo);
                break;
            case 2:
                users.completeToDo(idToDo);
                break;
            case 3:
                LocalDateTime newLimitDate = sw.readDate();
                users.changeLimitDate(idToDo, newLimitDate);
                break;
            case 4:
                System.out.println("1 - Not Urgent\n2 - Normal\n3 - Urgent");
                int newStatus = sw.readInt("invalid option", 1, 3);
                switch (newStatus) {
                    case 1:
                        users.changeUrgency(idToDo, Urgency.NOT_URGENT);
                        break;
                    case 2:
                        users.changeUrgency(idToDo, Urgency.NORMAL);
                        break;
                    case 3:
                        users.changeUrgency(idToDo, Urgency.URGENT);
                        break;
                }
                break;
            case 5:
                boolean ok = false;
                String task;
                while (!ok) {
                    System.out.print("Task (max 255 characters): ");
                    task = sw.readString("Invalid task");
                    if (task.length() > 255) {
                        System.out.println("String bigger than 255 characters");
                    } else {
                        users.changeTask(idToDo, task);
                        ok = true;
                    }
                }
                break;
        }

    }
}

