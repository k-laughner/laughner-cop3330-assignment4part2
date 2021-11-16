/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Kaylee Laughner
 */
package ucf.assignments;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class tasks {
    private String taskName;
    private String dueDate;
    private String description;
    private BooleanProperty status;
    public tasks(String taskName, String dueDate, String description, boolean checked) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.description = description;
        this.status = new SimpleBooleanProperty(checked);
    }
    public String getTaskName() {
        return taskName;
    }

    public String getDueDate() {
        return dueDate;
    }
    public void setDueDate(String dueDate){
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public BooleanProperty checkProperty(){
        return status;
    }
}
