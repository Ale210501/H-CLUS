module com.progetto.extension.extension {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.progetto.extension.extension to javafx.fxml;
    exports com.progetto.extension.extension;
}