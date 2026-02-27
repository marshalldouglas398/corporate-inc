module com.corporate {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;

    opens com.corporate to javafx.fxml;
    exports com.corporate;
}
