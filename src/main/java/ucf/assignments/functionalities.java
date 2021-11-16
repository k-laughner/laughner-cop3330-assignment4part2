/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Kaylee Laughner
 */
package ucf.assignments;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.util.Scanner;
class formatting {
    // function check if desc is larger than 256 chars and cut it off if it is
    public String checkDesc(String desc) {
        if (desc.length() > 256) {
            char[] descArr = new char[256];
            for (int i=0; i<256; i++) {
                descArr[i] = desc.charAt(i);
            }
            desc = descArr.toString();
        }
        return desc;
    }
}
public class functionalities {
    formatting f = new formatting();
    public void addToList(ObservableList<tasks> taskList, String name, String description, String date) {
        // ensuring description is less than 256 chars
        description = f.checkDesc(description);
        // adding to list
        taskList.add(new tasks(name, date, description, false));
    }
    public void removeList(ObservableList<tasks> taskList, tasks nameRemoved){
        // removing item from list
        taskList.remove(nameRemoved);
    }
    public void clearList(ObservableList<tasks> taskList) {
        // clearing the list
        taskList.clear();
    }
    public void changeDesc(tasks taskSelected, String newDesc) {
        // setting newDesc to 256 char limit (if not already)
        newDesc = f.checkDesc(newDesc);
        // changing the description to the new description
        taskSelected.setDescription(newDesc);
    }
    public void changeDate(tasks taskSelected, String newDate) {
        // changing the date to the new date
        taskSelected.setDueDate(newDate);
    }
    public FilteredList showComplete(ObservableList<tasks> taskList) {
        // filtering the list to have only complete items (where checked == true)
        FilteredList<tasks> selectedTasks = new FilteredList<>(taskList, i-> i.checkProperty().get()==true);
        return  selectedTasks;
    }
    public FilteredList showIncomplete(ObservableList<tasks> taskList) {
        // filtering the list to have only incomplete items (where checked == false)
        FilteredList<tasks> selectedTasks = new FilteredList<>(taskList, i-> i.checkProperty().get()!=true);
        return  selectedTasks;
    }
    public void writeList(File file, ObservableList<tasks> taskList) throws IOException {
        FileWriter writer = new FileWriter(file);
        // write each task to file
        for (int i = 0; i<taskList.size(); i++) {
            String taskName = taskList.get(i).getTaskName();
            String dueDate = taskList.get(i).getDueDate();
            String description = taskList.get(i).getDescription();
            String status;
            if (taskList.get(i).checkProperty().get()==true) {
                status = "complete";
            }
            else{
                status = "incomplete";
            }
            writer.write(taskName+","+dueDate+","+description+","+status+"\n");
        }
        writer.close();
    }
    public File chooseFile() throws IOException {
        // calling stage class
        Stage secondaryStage = new Stage();
        // calling FileChooser class
        FileChooser fileChooser = new FileChooser();
        // setting initial directory
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        // set file name to default
        fileChooser.setInitialFileName("default");
        // set file type to .txt
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        // showing the save dialog
        File file = fileChooser.showSaveDialog(secondaryStage);
        return file;
    }
    public void saveList(ObservableList<tasks> taskList, File file) throws IOException{
        writeList(file, taskList);
    }
    public void viewOpenedFile(BufferedReader file, ObservableList<tasks> taskList) throws IOException{
        // empty list before loading new one
        taskList.clear();
        Scanner f = new Scanner(file);
        // adding each item in file to list
        while (f.hasNext()) {
            String line = f.next();
            String[] values  = line.split(",");
            // if status is complete in file, then checkbox will be checked upon load
            if (values[3].equals("complete")) {
                taskList.add(new tasks(values[0], values[1], values[2], true));
            }
            // if status is incomplete in file, then checkbox will not be checked upon load
            else {
                taskList.add(new tasks(values[0], values[1], values[2], false));
            }
        }
    }
    public BufferedReader chooseListToOpen() throws IOException{
        // calling stage class
        Stage secondaryStage = new Stage();
        // calling FileChooser class
        FileChooser fileChooser = new FileChooser();
        // setting initial directory
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        // set file type to .txt
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        // showing the save dialog
        BufferedReader reader = new BufferedReader(new FileReader(fileChooser.showOpenDialog(secondaryStage)));
        return reader;
    }
}
