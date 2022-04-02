module a8.ybond.javafxtodo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires org.json;


    opens a8.ybond.javafxtodo to javafx.fxml;
    exports a8.ybond.javafxtodo;
}