package bitonic;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Main extends Application {

    private Canvas canvas;
    private SortVisualiser thread;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.canvas = new Canvas();

        Group root = new Group();
        root.getChildren().add(canvas);

        primaryStage.setTitle("Bitonic sort");
        primaryStage.setMaximized(false);
        primaryStage.setScene(new Scene(root, 1504, 801));
        primaryStage.show();

        double width = primaryStage.getWidth();
        double height = primaryStage.getHeight();
        //width -= 20;
        //height -= 40;
        System.out.println("Width: " + width + " Height: " + height);

        canvas.setWidth(width);
        canvas.setHeight(height);

        Thread.sleep(2000);

        thread = new SortVisualiser(this.canvas);
    }

    @Override
    public void stop() {
        thread.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
