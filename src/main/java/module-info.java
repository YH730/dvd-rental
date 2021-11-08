module com.example.project_java2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.project_java2 to javafx.fxml;
    exports com.example.project_java2;
}