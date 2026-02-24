module com.corporate {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.corporate to javafx.fxml;
    exports com.corporate;
}
