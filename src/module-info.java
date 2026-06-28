module gruntmanesMetode {
    requires java.desktop;
    requires org.junit.jupiter.api;
    requires junit;

    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
    
    // This allows FXMLLoader and Graphics to open files inside this package!
    opens profpaligs to javafx.fxml, javafx.graphics;
}