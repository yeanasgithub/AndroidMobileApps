module a6.ybond.fxchat.fxchat {
    requires javafx.controls;
    requires javafx.fxml;


    opens a6.ybond.fxchat.fxchat to javafx.fxml;
    exports a6.ybond.fxchat.fxchat;
}