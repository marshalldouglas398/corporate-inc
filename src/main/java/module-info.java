module com.corporate {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires junit;
    requires javafx.graphics;

    opens com.controllers to javafx.fxml;
    opens com.corporate to javafx.fxml;
    exports com.corporate;
}
