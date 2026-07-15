package graphVisualizer;

import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

public class AdjacencyMatrixVisualizerTest {

    // Boot up the JavaFX Toolkit once in the background.
    // This allows us to use JavaFX objects without launching a massive standalone Application.
    @BeforeAll
    public static void initToolkit() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // Toolkit already started by a previous run, safe to ignore
        }
    }

    @Test
    public void testVisualizerInIsolatedStage() throws InterruptedException {
        int[][] testMatrix = {
            {0, 1, 1},
            {1, 0, 0},
            {1, 0, 0}
        };

        // A Latch helps our JUnit test thread wait until the JavaFX thread finishes rendering
        CountDownLatch latch = new CountDownLatch(1);

        // Run this layout creation safely on the official JavaFX Application Thread
        Platform.runLater(() -> {
            // 1. Create your component in complete isolation
            AdjacencyMatrixVisualizer visualizer = new AdjacencyMatrixVisualizer(testMatrix);

            // 2. Wrap it in a temporary, throwaway Stage
            Stage tempStage = new Stage();
            tempStage.setTitle("Isolated Test Window");
            tempStage.setScene(new Scene(visualizer, 500, 500));
            tempStage.show();

            // 3. Run your layout assertions directly on the live pane
            int circleCount = 0;
            int lineCount = 0;
            for (javafx.scene.Node child : visualizer.getChildren()) {
                if (child instanceof Circle) circleCount++;
                if (child instanceof Line) lineCount++;
            }

            assertEquals(3, circleCount, "Should have 3 nodes.");
            assertEquals(2, lineCount, "Should have 2 edges.");

            // 4. Close the stage automatically when the test assertions complete
            // (Or comment this out if you want to leave the window open to play with it!)
            tempStage.close();

            latch.countDown(); // Tell JUnit we are done
        });

        latch.await(); // Wait for the JavaFX thread to finish execution
    }
}