/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Kaylee Laughner
 */
package ucf.assignments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
public class Controller implements Initializable {
    @FXML
    private TableView<tasks> list;
    @FXML
    private TableColumn<tasks, String> taskName;
    @FXML
    private TableColumn<tasks, String> dueDate;
    @FXML
    private TableColumn<tasks, String> description;
    @FXML
    private TableColumn<tasks, Boolean> status;
    @FXML
    private TextField name;
    @FXML
    private DatePicker dateInput;
    @FXML
    private TextField descriptionInput;
    // Observable List
    ObservableList<tasks> taskList = FXCollections.observableArrayList(
    );
    functionalities f = new functionalities();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // setting cells for columns
        taskName.setCellValueFactory(new PropertyValueFactory<tasks, String>("taskName"));
        dueDate.setCellValueFactory(new PropertyValueFactory<tasks, String>("dueDate"));
        description.setCellValueFactory(new PropertyValueFactory<tasks, String>("description"));
        status.setCellValueFactory(new PropertyValueFactory<tasks,Boolean>("check"));
        status.setCellFactory(column -> new CheckBoxTableCell());

        list.setItems(taskList);

        // update table for edits
        list.setEditable(true);
        description.setCellFactory(TextFieldTableCell.forTableColumn());
        dueDate.setCellFactory(TextFieldTableCell.forTableColumn());

        // creating format for description input box to be less than 256 chars
        Pattern pattern = Pattern.compile(".{0,256}");
        TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        // setting description TextField to only allow 256 chars
        descriptionInput.setTextFormatter(formatter);
    }
    public void addItem(MouseEvent e) {
        // get TextField input
        String nameToAdd = name.getText();
        String dateToAdd = dateInput.getValue().toString();
        String descToAdd = descriptionInput.getText();

        // if-else statement to prevent null fields from being added
        if (descToAdd == "" || nameToAdd=="") {
            // print error screen
            System.out.println("Please input into all boxes.");
        }
        else{
            // calling addToList function to add to list
            f.addToList(taskList, nameToAdd, descToAdd, dateToAdd);
            // clear the TextFields of the added input
            name.clear();
            descriptionInput.clear();
        }
    }
    public void removeItem(MouseEvent e) {
        // find selected item to be removed
        tasks nameRemoved = list.getSelectionModel().getSelectedItem();
        // call removal function
        f.removeList(taskList, nameRemoved);
    }
    public void clearList(MouseEvent e) {
        // call clearList function to clear list
        f.clearList(taskList);
    }
    public void changeDescription(TableColumn.CellEditEvent e) {
        // getting selected task
        tasks taskSelected = list.getSelectionModel().getSelectedItem();
        // getting desired edit
        String newDesc = e.getNewValue().toString();
        // calling changeDesc function
        f.changeDesc(taskSelected, newDesc);
    }
    public void changeDate(TableColumn.CellEditEvent e) {
        // getting selected task
        tasks dateSelected = list.getSelectionModel().getSelectedItem();
        // getting desired edit
        String newDate = e.getNewValue().toString();
        // calling changeDate function
        f.changeDate(dateSelected, newDate);
    }
    public void showComplete(MouseEvent e) {
        // calling show complete function to filter through list
        FilteredList<tasks> selectedTasks = f.showComplete(taskList);
        // setting items to the FilteredList so only they show
        list.setItems(selectedTasks);
    }
    public void showIncomplete(MouseEvent e){
        // calling show incomplete function to filter through list
        FilteredList<tasks> selectedTasks = f.showIncomplete(taskList);
        // setting items to the FilteredList so only they show
        list.setItems(selectedTasks);
    }
    public void showAll(MouseEvent e){
        // filter to show all tasks
        list.setItems(taskList);
    }
    public void saveList(Event e) throws IOException{
        // getting desired file to save to
        File file = f.chooseFile();
        // calling saveList function to save data to file
        f.saveList(taskList, file);
    }
    public void openList(Event e) throws IOException {
        // calling function to let user choose a file to open
        // assigning chosen file to bufferedReader
        BufferedReader reader = f.chooseListToOpen();
        // calling function to viewFile using observableList in TableView
        f.viewOpenedFile(reader, taskList);
    }
}
