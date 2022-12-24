module com.example.supplychainsandeep17dec {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.supplychainsandeep17dec to javafx.fxml;
    exports com.example.supplychainsandeep17dec;
}