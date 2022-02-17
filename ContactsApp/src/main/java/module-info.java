module a3.ybond.contactsapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens a3.ybond.contactsapp to javafx.fxml;
    exports a3.ybond.contactsapp;
}