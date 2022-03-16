package a6.ybond.fxchat.fxchat;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ChatViewController implements CanWriteMessage {
    ObservableList<String> messages = FXCollections.observableArrayList();
    ObservableList<String> people = FXCollections.observableArrayList();
    private Client client;

    @FXML
    TextField txtMessage;

    @FXML
    ListView<String> listMessages, listPeople;

    @FXML
    public void initialize() {
        // ListView listMessages would be updated to
        // reflect any new messages
        listMessages.setItems(messages);
        // we need to bind people with listPeople
        listPeople.setItems(people);
    }

    public void setClient(Client client) {
        this.client = client;
        // we should tell the client to start listening messages
        client.listenForMessages(this);
    }


    @FXML
    private void onSendClicked() {

        // this is to send our messages to the server
        // UserName said this message
        client.sendMessage(client.getUserName() + ": " + txtMessage.getText());
        // to clear out the message field
        txtMessage.setText("");
    }

    @Override
    public void writeMessage(String message) {

        switch (message.charAt(0))
        {
            case '+' -> Platform.runLater( () -> people.add(message.substring(1)));
            case '-' -> Platform.runLater( () -> people.remove(message.substring(1)));

            // when the main thread is ready
            // message will be put into the observable array list
            // which is bound to our ListView listMessages

            default -> Platform.runLater(() -> messages.add(message));
        }

    }
}
