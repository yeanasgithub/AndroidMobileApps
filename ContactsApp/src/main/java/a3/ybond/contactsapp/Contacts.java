package a3.ybond.contactsapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Contacts extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Contacts.class.getResource("list-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        // define controller
        ListViewController controller = fxmlLoader.getController();
        stage.setTitle("Contacts");
        stage.setScene(scene);
        // ListViewController has public void function shutDown()
        stage.setOnHidden(e -> controller.shutDown());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}