module a4.ybond.weather {
    requires javafx.controls;
    requires javafx.fxml;
    // added now
    requires org.json;
    requires java.net.http;


    opens a4.ybond.weather to javafx.fxml;
    exports a4.ybond.weather;
}