module gruntmanesMetode {
    requires java.desktop;
    requires org.junit.jupiter.api;
    requires junit;

    requires javafx.controls;
    requires javafx.fxml;
    
    opens profpaligs to javafx.fxml, javafx.graphics;
}