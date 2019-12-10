package todoApp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ToDo {

    private static int toDoCount = 0;
    private int globalId;
    private int relativeId;
    private String task;
    private LocalDateTime creationDate;
    private LocalDateTime startDate;
    /**
     * [Optional] date it should be completed
     */
    private LocalDateTime limitDate;
    private LocalDateTime completionDate;
    private Status status;
    private Urgency urgency;

    public ToDo(int idToDo, String task, Urgency urgency, LocalDateTime limitDate) {
        toDoCount += 1;
        this.globalId = toDoCount;
        this.relativeId = idToDo;
        this.task = task;
        this.creationDate = LocalDateTime.now();
        this.startDate = null;
        this.limitDate = limitDate;
        this.completionDate = null;
        this.status = Status.Not_started;
        this.urgency = urgency;
    }

    public ToDo(int globalId, int relativeId, String task, LocalDateTime creationDate, LocalDateTime startDate, LocalDateTime limitDate, LocalDateTime completionDate, Status status, Urgency urgency) {
        toDoCount += 1;
        this.relativeId = relativeId;
        this.globalId = globalId;
        this.task = task;
        this.creationDate = creationDate;
        this.startDate = startDate;
        this.limitDate = limitDate;
        this.completionDate = completionDate;
        this.status = status;
        this.urgency = urgency;
    }

    // Getters

    public LocalDateTime getCreationDate() {
        return this.creationDate;
    }

    public LocalDateTime getLimitDate() {
        return this.limitDate;
    }

    public Urgency getUrgency() {
        return this.urgency;
    }

    public int getRelativeId() {
        return relativeId;
    }

    public int getGlobalId() {
        return globalId;
    }

    public String getTask() {
        return task;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public Status getStatus() {
        return status;
    }

    //Setters

    public void setTask(String task) {
        this.task = task;
    }


    public void setUrgency(Urgency urgency) {
        this.urgency = urgency;
    }

    public void setLimitDate(LocalDateTime limitDate) {
        this.limitDate = limitDate;
    }


    @Override
    public String toString() {

        String ret = "Id: " + relativeId + "\n" +
                "Task: " + task + '\n' +
                "Status: ";

        ret = ret + status + "\n";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss, dd/MM/yyyy ");

        ret = ret + "Creation Date: " + this.creationDate.format(formatter) + "\n";

        if (this.status == Status.In_progress || this.status == Status.Completed) {
            ret = ret + "Start Date: " + this.startDate.format(formatter) + "\n";
        }
        if (this.limitDate != null) {
            ret = ret + "Limit Date: " + this.limitDate.format(formatter) + "\n";
        }

        if (this.status == Status.Completed) {
            ret = ret + "Completion Date: " + this.completionDate.format(formatter) + "\n";
        }

        ret = ret + "Urgency: " + urgency + "\n";
        return ret;
    }


    public void start() {
        this.startDate = LocalDateTime.now();
        this.status = Status.In_progress;
        this.completionDate = null;
    }

    public void complete() {
        if (this.startDate == null) this.startDate = LocalDateTime.now();
        this.completionDate = LocalDateTime.now();
        this.status = Status.Completed;
    }
}
