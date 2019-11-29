package todoApp;

import java.util.Comparator;

public class LimitDateComparator implements Comparator<ToDo> {
    public int compare(ToDo td1, ToDo td2) {
        if (td1.getStatus() == Status.Completed && td2.getStatus() != Status.Completed) return 1; // if the first has already been completed
        if (td1.getStatus() != Status.Completed && td2.getStatus() == Status.Completed) return -1; // if the second has already been completed
        if (td1.getLimitDate() == null) return 1;
        if (td2.getLimitDate() == null) return -1;
        if (td1.getLimitDate().isBefore(td2.getLimitDate())) return -1;
        else return 1;
    }
}
