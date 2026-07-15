package graphVisualizer;

import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

public class VisualizerLayoutTest {

    @BeforeAll
    public static void initToolkit() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // Toolkit already active, safe to ignore
        }
    }

    @Test
    public void testFullAppLayoutIntegration() throws InterruptedException {
        int[][] testMatrix = {
            {1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0},
            {0, 1, 1, 0, 0},
            {0, 0, 0, 1, 0},
            {1, 0, 0, 0, 0}
        };

        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            // 1. Root Container
            BorderPane mainLayout = new BorderPane();
            mainLayout.setStyle("-fx-background-color: #1e1e24;");

            // 2. Top Header Bar
            HBox header = new HBox();
            header.setStyle("-fx-background-color: #2f313d; -fx-padding: 15;");
            Label title = new Label("Collaborative Graph Visualizer Suite");
            title.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px;");
            header.getChildren().add(title);
            mainLayout.setTop(header);

            // 3. Right Control Panel (Buttons to manipulate graph)
            VBox controlsPanel = new VBox(10);
            controlsPanel.setStyle("-fx-background-color: #2a2b36; -fx-padding: 15;");
            controlsPanel.setPrefWidth(200);
            
            Label controlTitle = new Label("Controls");
            controlTitle.setStyle("-fx-text-fill: #00adb5; -fx-font-weight: bold;");
            Button generateBtn = new Button("Generate Graph");
            Button clearBtn = new Button("Clear Graph");
            
            controlsPanel.getChildren().addAll(controlTitle, generateBtn, clearBtn);
            mainLayout.setRight(controlsPanel);

            // 4. Center Console/Logger (Simulating MessageHandler display)
            VBox logArea = new VBox(5);
            logArea.setStyle("-fx-padding: 15;");
            Label logTitle = new Label("System Message Log:");
            logTitle.setStyle("-fx-text-fill: #8c8c8c;");
            TextArea consoleLog = new TextArea();
            consoleLog.setEditable(false);
            consoleLog.setPromptText("Waiting for network messages...");
            consoleLog.setText("[MessageHandler] Connection established.\n[MessageHandler] Ready to receive matrices.");
            
            logArea.getChildren().addAll(logTitle, consoleLog);
            mainLayout.setCenter(logArea);

            // 5. Bottom-Left Graph Visualizer (Strictly constrained)
            AdjacencyMatrixVisualizer visualizer = new AdjacencyMatrixVisualizer(testMatrix);
            visualizer.setPrefSize(300, 300);
            visualizer.setStyle("-fx-border-color: #00adb5; -fx-border-width: 2px; -fx-background-color: #111216;");

            StackPane bottomLeftWrapper = new StackPane(visualizer);
            bottomLeftWrapper.setStyle("-fx-alignment: bottom-left; -fx-padding: 15;");
            
            mainLayout.setLeft(bottomLeftWrapper);
            BorderPane.setAlignment(bottomLeftWrapper, Pos.BOTTOM_LEFT);

            // 6. Stage Setup
            Stage stage = new Stage();
            Scene scene = new Scene(mainLayout, 900, 650);
            stage.setScene(scene);
            stage.show();

            // 7. Assertions (Verifying structural validity with other components live)
            mainLayout.layout();

            Bounds visualizerBounds = visualizer.localToScene(visualizer.getBoundsInLocal());
            Bounds mainLayoutBounds = mainLayout.getLayoutBounds();

            // Verify Left alignment holds
            double visualizerCenterX = visualizerBounds.getMinX() + (visualizerBounds.getWidth() / 2);
            double layoutHalfWidth = mainLayoutBounds.getWidth() / 2;
            assertTrue(visualizerCenterX < layoutHalfWidth, "Visualizer must stay on the LEFT half.");

            // Verify Bottom alignment holds
            double visualizerCenterY = visualizerBounds.getMinY() + (visualizerBounds.getHeight() / 2);
            double layoutHalfHeight = mainLayoutBounds.getHeight() / 2;
            assertTrue(visualizerCenterY > layoutHalfHeight, "Visualizer must stay on the BOTTOM half.");

            // Verify auxiliary components exist on screen
            assertNotNull(mainLayout.getRight(), "Control panel should be configured on the right side.");
            assertNotNull(mainLayout.getCenter(), "The logging area must occupy the center pane.");

            System.out.println("✅ Full Layout Integration Test Passed successfully!");

  
        });

        latch.await();
    }
}