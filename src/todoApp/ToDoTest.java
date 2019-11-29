package todoApp;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ToDoTest {

    @Test
    void setTask() {
        ToDo td = new ToDo(1, "test task", Urgency.NORMAL, LocalDateTime.now().plusDays(2));
        String expected = "new test taks";
        td.setTask(expected);
        String actual = td.getTask();
        assertEquals(expected, actual);
    }

    @Test
    void setUrgencyTest(){
        ToDo td = new ToDo(1, "test task", Urgency.NORMAL, LocalDateTime.now().plusDays(2));
        Urgency expectedUrgency = Urgency.NOT_URGENT;
        td.setUrgency(expectedUrgency);
        Urgency actualUrgency = td.getUrgency();
        assertEquals(expectedUrgency, actualUrgency);
    }

    @Test
    void setLimitDate() {
        ToDo td = new ToDo(1, "test task", Urgency.NORMAL, LocalDateTime.now().plusDays(2));
        LocalDateTime expectedLimitDate = LocalDateTime.now();
        td.setLimitDate(expectedLimitDate);
        assertEquals(expectedLimitDate, td.getLimitDate());
    }

    @Test
    void start() {
        ToDo td = new ToDo(1, "test task", Urgency.NORMAL, null);
        td.start();
        assertEquals(td.getStatus(), Status.In_progress);
        assertNull(td.getCompletionDate());
    }

    @Test
    void complete() {
        ToDo td = new ToDo(1, "test task", Urgency.NORMAL, LocalDateTime.now().plusDays(2));
        td.complete();
        assertEquals(td.getStatus(), Status.Completed);
        assertTrue(td.getCompletionDate().isBefore(LocalDateTime.now()));
    }

}