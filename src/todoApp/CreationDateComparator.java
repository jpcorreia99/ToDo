package todoApp;

import java.util.Comparator;

public class CreationDateComparator implements Comparator<ToDo>{

    public int compare(ToDo td1, ToDo td2){
        if (td1.getStatus() == Status.Completed && td2.getStatus() != Status.Completed) return 1; // if the first has already been completed
        if (td1.getStatus() != Status.Completed && td2.getStatus() == Status.Completed) return -1; // if the second has already been completed
        if (td1.getCreationDate().isBefore(td2.getCreationDate())) return -1;
        else return 1;
    }
}
