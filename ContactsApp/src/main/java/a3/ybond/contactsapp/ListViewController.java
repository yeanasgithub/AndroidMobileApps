package a3.ybond.contactsapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

//json is like XML, but lighter weight version
//json provides a way of represent object as a string
//we are going to add functions to Contacts
//to export our contact as a Jason's object string
//and a constructor that would take the Jason object
// and build contact info from it

public class ListViewController {

    // To show contactsList (fx:id in list-view.fxml) inside the initialize() below
    // we need to bind our contactsList to an observableList
    // an array that fires an event every time it is changed
    ObservableList<Contact> contacts = FXCollections.observableArrayList();


    @FXML
    ListView<Contact> contactsList;

    // reference to the GridPane
    @FXML
    GridPane newContact;
    @FXML
    TextField txtFirstName, txtLastName, txtPhone;
    @FXML
    VBox vBoxMain;
    @FXML
    MenuItem btnThemeDefault, btnThemeBlue, btnThemeGreen;

    // before loading the contact.json
    // sort the names by surname
    ContactComparator comparator = new ContactComparator();

    // life cycle methods that are available to scenes
    @FXML
    public void initialize() {

        // initialize() is similar to constructor but it
        // runs after the scene is built
        // Contact tmp = new Contact("Yeana", "Bond", "661-555-1234");
        // contacts.add(tmp);
        // System.out.println(tmp);

        // To show the new contact in the ListView
        contactsList.setItems(contacts);
        setNewContactVis(false);

        // Call the load function to show the previously saved contact info
        loadListFromJSON();
        // comparator is a class which can take two objects and
        // return them based on a property
        contacts.sort(comparator);


    }

    // when opening the app again, we want the app to keep the
    // typed input in a file and have it loaded the next time we open the app

    private void loadListFromJSON(){
        // read through the file and pull each line
        // and create JSON object out of it
        // and pass it to the constructor of Contact
        try(Scanner scanner = new Scanner(new File("contacts.json"))){
            while(scanner.hasNextLine()) {
                String contactString = scanner.nextLine();

                //constructor of Json object to make the Json object then
                //calling the constructor of Contact
                Contact tmp = new Contact(new JSONObject(contactString));
                contacts.add(tmp);
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

    }

    // to have all of our contacts saved to a file as Json strings when all
    // when apps are shutting
    // shutting down contorller is contained in Contacts.java
    @FXML
    public void shutDown(){
        System.out.println("Shutting Down");
        // File I/O try-catch block
        try(FileWriter file = new FileWriter("contacts.json")) {
            for (int i = 0; i < contacts.size(); i++) {
                    file.write(contacts.get(i).getJSONString());
                    if (i != contacts.size() - 1) {
                        file.write("\n");
                    }
                }
            } catch(IOException e){
            e.printStackTrace();
            }
    }



    @FXML
    // Theme menu
    protected void onThemeChange(final ActionEvent event){
        // requiring three CSS files
        String defaultCSS = Objects.requireNonNull(getClass().getResource("Default.css")).toString();
        String blueCSS = Objects.requireNonNull(getClass().getResource("Blue.css")).toString();
        String greenCSS = Objects.requireNonNull(getClass().getResource("Green.css")).toString();

        vBoxMain.getScene().getStylesheets().removeAll(defaultCSS, blueCSS, greenCSS);
        // we need to see which button has been pressed

        Object source = event.getSource();
        if (btnThemeDefault.equals(source)) {
            vBoxMain.getScene().getStylesheets().add(defaultCSS);
        }

        if (btnThemeBlue.equals(source)) {
            vBoxMain.getScene().getStylesheets().add(blueCSS);
        }

        if (btnThemeGreen.equals(source)) {
            vBoxMain.getScene().getStylesheets().add(greenCSS);
        }

    }
    // New under Contacts menu
    @FXML
    protected void onNewContact(){
        setNewContactVis(true);
    }

    // Button in Scene Builder
    @FXML
    protected void onSaveContact(){
        Contact tmp = new Contact(txtFirstName.getText(), txtLastName.getText(), txtPhone.getText());
        contacts.add(tmp);
        setNewContactVis(false);
        txtFirstName.setText(null);
        txtLastName.setText(null);
        txtPhone.setText(null);
        // Upon saving the contact every time, we will sort again
        // so after editing and deleting as well as saving a new contact info,
        // contents are always sorted; onsaveContact() is a shared function (intersection function)
        // of all Contacts menu
        // Logic: IF save THEN sort
        contacts.sort(comparator);
    }

    // Edit under Contact menu
    @FXML
    protected void onEditContact(){
        Contact selectedContact = contactsList.getSelectionModel().getSelectedItem();
        if(selectedContact != null){
            onNewContact();
            txtFirstName.setText(selectedContact.getFirstName());
            txtLastName.setText(selectedContact.getLastName());
            txtPhone.setText(selectedContact.getPhone());
            onDeleteContact();
        }
    }

    // Delete under Contacts
    @FXML
    protected void onDeleteContact(){
        Contact selectedContact = contactsList.getSelectionModel().getSelectedItem();
        if(selectedContact != null) contacts.remove(selectedContact);

    }

    // Make ListView content visible or not visible
    private void setNewContactVis(Boolean vis){
        newContact.setVisible(vis);
        newContact.setManaged(vis);
    }
}
