module com.example.leexinchen_intellij_19 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.pregame;
    opens com.example.demo.pregame to javafx.fxml;
    exports com.example.demo.ingame;
    opens com.example.demo.ingame to javafx.fxml;
    exports com.example.demo.postgame;
    opens com.example.demo.postgame to javafx.fxml;
}