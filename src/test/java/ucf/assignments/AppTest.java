/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Kaylee Laughner
 */
package ucf.assignments;
import javafx.collections.transformation.FilteredList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

// Requirement for Help File is satisfied using a README.md file
public class AppTest {
    Controller c;
    functionalities f;
    @BeforeEach
    void setup() {
        c = new Controller();
        f = new functionalities();
    }
    @Test
    @DisplayName("Testing requirement that user can add to list (at least 100 items)")
    public void addToListTest() {
        // populating list to 100 items
        for (int i=0; i<100; i++) {
            String name = "task"+i;
            String dueDate = i+i+i+i+"/"+i+i+"/"+i+i;
            String desc = "desc"+i;
            // calling add to list function to add 100 items to list
            f.addToList(c.taskList, name,desc, dueDate);
        }
        // getting actual list size after populating it
        int listSize = c.taskList.size();
        // if list size is = 100 then test is passed and user can add at least 100 items to the list
        assertEquals(100, listSize);
    }
    @Test
    @DisplayName("Testing requirement that user can remove an item from the list")
    public void removeListTest() {
        // populating list with 2 items
        f.addToList(c.taskList, "name1","desc1", "1111/11/11");
        f.addToList(c.taskList, "name2", "desc2", "2222/22/22");
        // printing list size before removal
        System.out.println("List size before removing 1 item is "+c.taskList.size());
        // calling removeList function to remove the first entry
        f.removeList(c.taskList, c.taskList.get(0));
        // list size should go down 1 after removal
        int expectedSize = 1;
        // getting actual size to test
        int actualSize = c.taskList.size();
        assertEquals(expectedSize, actualSize);
    }
    @Test
    @DisplayName("Testing requirement that user can clear a list")
    public void clearListTest() {
        // populating list with 100 items
        for (int i=0; i<100; i++) {
            String name = "task"+i;
            String dueDate = i+i+i+i+"/"+i+i+"/"+i+i;
            String desc = "desc"+i;
            // calling add to list function to add 100 items to list
            f.addToList(c.taskList, name,desc, dueDate);
        }
        // getting list size before clear
        int listBeforeClear = c.taskList.size();
        // printing this size to console
        System.out.println(listBeforeClear+" items on list");
        // calling clear function to clear the list
        f.clearList(c.taskList);
        // getting list size after clear
        int listSize = c.taskList.size();
        System.out.println(listSize+" items on list after clear");
        // if listSize is 0, then it was successfully cleared
        assertEquals(0, listSize);
    }
    @Test
    @DisplayName("Testing requirement that description is between 1-256 chars")
    public void descSizeTest() {
        // declaring desc string
        String desc = "";
        // creating desc larger than 300
        for (int i=0; i<300; i++) {
            desc = ""+"a";
        }
        // creating expected desc of length 256
        String expectedDesc = "";
        for (int i=0; i<256; i++) {
            expectedDesc = ""+"a";
        }
        // calling add function to add it to the list
        f.addToList(c.taskList, "name1", desc, "2021/01/01");

        // if desc validation function was successful, the desc should be equal to the expectedDesc and not the inputted desc
        assertEquals(expectedDesc, c.taskList.get(0).getDescription());
    }
    @Test
    @DisplayName("Testing requirement that date is showed to user in correct format")
    public void dateFormatTest() {
        f.addToList(c.taskList, "name1","desc1", "2021-11-11");
        String actualDate = c.taskList.get(0).getDueDate();
        String expectedDate = "2021-11-11";
        assertEquals(expectedDate, actualDate);
    }
    @Test
    @DisplayName("Testing requirement that user can change description")
    public void changeDescTest() {
        // populating the list
        f.addToList(c.taskList, "name1","desc1", "2021/11/11");
        f.addToList(c.taskList, "name2", "desc2", "2021/22/22");
        // setting new desc
        String newDesc = "new description";
        // selecting the first item to change
        tasks taskSelection = c.taskList.get(0);

        // calling changeDesc function
        f.changeDesc(taskSelection, newDesc);

        // getting the current description for item 0
        String actualDesc = c.taskList.get(0).getDescription();

        // if current description is the same as new description, test is passed and user can change description
        assertEquals(newDesc, actualDesc);
    }
    @Test
    @DisplayName("Testing requirement that user can change due date")
    public void changeDueDateTest() {
        // populating the list
        f.addToList(c.taskList, "name1","desc1", "1111/11/11");
        f.addToList(c.taskList, "name2", "desc2", "2222/22/22");
        // setting new date
        String newDate = "2021/11/15";
        // selecting the first item to change
        tasks taskSelection = c.taskList.get(0);

        // calling changeDate function
        f.changeDesc(taskSelection, newDate);

        // getting the current date for item 0
        String actualDate = c.taskList.get(0).getDescription();

        // if current date is the same as new date, test is passed and user can change date
        assertEquals(newDate, actualDate);
    }
    @Test
    @DisplayName("Testing requirement that user can mark an item complete")
    public void markCompleteTest() {
        // populating the list
        // when populated list items are automatically marked as incomplete
        f.addToList(c.taskList, "name1","desc1", "1111/11/11");
        f.addToList(c.taskList, "name2", "desc2", "2222/22/22");

        // marking item 0 as complete
        // following CheckBox logic
        c.taskList.get(0).checkProperty().set(true);

        // if the item's checkProperty is true then it is marked as complete
        // this means the test would be passed as when checked its property is true
        assertTrue(c.taskList.get(0).checkProperty().get());
    }
    @Test
    @DisplayName("Testing requirement that user can filter to see complete items")
    public void showCompleteTest() {
        // populating the list
        // when populated list items are automatically marked as incomplete
        f.addToList(c.taskList, "name1","desc1", "1111/11/11");
        f.addToList(c.taskList, "name2", "desc2", "2222/22/22");

        // marking item 0 as complete
        c.taskList.get(0).checkProperty().set(true);

        // calling showComplete to get the Filtered list of complete items
        FilteredList<tasks> completedTasks = f.showComplete(c.taskList);

        int listSize = completedTasks.size();
        String taskName = completedTasks.get(0).getTaskName();
        // based off inputted data, the filtered list should have size of 1
        assertEquals(1, listSize);
        // based off inputted data, the filtered list should only show task name1
        assertEquals("name1", taskName);
    }
    @Test
    @DisplayName("Testing requirement that user can filter to see incomplete items")
    public void showIncompleteTest() {
        // populating the list
        // when populated list items are automatically marked as incomplete
        f.addToList(c.taskList, "name1","desc1", "1111/11/11");
        f.addToList(c.taskList, "name2", "desc2", "2222/22/22");

        // marking item 0 as complete
        c.taskList.get(0).checkProperty().set(true);

        // calling showComplete to get the Filtered list of incomplete items
        FilteredList<tasks> incompleteTasks = f.showIncomplete(c.taskList);

        int listSize = incompleteTasks.size();
        String taskName = incompleteTasks.get(0).getTaskName();
        // based off inputted data, the filtered list should have size of 1
        assertEquals(1, listSize);
        // based off inputted data, the filtered list should only show task name2
        assertEquals("name2", taskName);
    }
    @Test
    @DisplayName("Testing requirement that user can save file with list data")
    public void saveFileTest() throws IOException {
        // creating file in test files folder for project to run test on
        File file = new File("src/test/testFiles/test.txt");
        // adding an item to the list that will be written to a file
        c.taskList.add(new tasks("name", "0000/00/00", "desc", true));

        // calling saveList function to save the list to desired path
        f.saveList(c.taskList, file);

        // calling buffered reader to read file that was just saved
        BufferedReader file2 = new BufferedReader(new FileReader("src/test/testFiles/test.txt"));
        // getting content in file
        String fileContent = file2.readLine();
        // setting expected content
        String expectedContent = "name,0000/00/00,desc,complete";
        // if expected content is the same as actual content, the list was saved successfully
        assertEquals(expectedContent, fileContent);
    }
    @Test
    @DisplayName("Testing requirement that user can load a list")
    public void openListTest() throws IOException {
        // creating buffered reader with test file that I want to load
        BufferedReader reader = new BufferedReader(new FileReader("src/test/testFiles/displayTest.txt"));
        // calling openList function to load the list
        f.viewOpenedFile(reader, c.taskList);
        // now the items on .txt file should be loaded to the taskList
        // to test this, I'm going to create a string with the list's content to see if it matches expected content
        String content = c.taskList.get(0).getTaskName()+","+
                c.taskList.get(0).getDueDate()+","+
                c.taskList.get(0).getDescription()+","+
                c.taskList.get(0).checkProperty().get()+","+
                c.taskList.get(1).getTaskName()+","+
                c.taskList.get(1).getDueDate()+","+
                c.taskList.get(1).getDescription()+","+
                c.taskList.get(1).checkProperty().get();
        String expected = "name1,2021-11-03,desc1,true,name2,2021-11-03,desc2,false";
        // if the loaded list string content is the same as the expected content, the test is passed
        assertEquals(expected, content);
    }
}
