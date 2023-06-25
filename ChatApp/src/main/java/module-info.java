module com.example.managerapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.managerapp to javafx.fxml;
    exports com.example.managerapp;
}