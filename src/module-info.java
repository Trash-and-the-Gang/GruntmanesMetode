module JavaFX_Test {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens profpaligs to javafx.fxml, javafx.graphics;
}