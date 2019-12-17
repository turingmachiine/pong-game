package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {

    Pane root;
    Pane startPane;
    Pane gamePane;

    @Override
    public void start(Stage stage) throws Exception{
        root = new Pane();
        root.setPrefSize(200, 200);
        Scene scene = new Scene(root);
        stage.setTitle("Pong Game");
        stage.setScene(scene);
        initStartPane();
        root.getChildren().add(startPane);
        stage.show();
    }


    private void initStartPane() {
        startPane = new Pane();
        Button button = new Button("Начать игру");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                initGamePane();
                root.getChildren().setAll(gamePane);
            }
        });
        startPane.getChildren().add(button);
    }

    private void initGamePane() {
        Canvas canvas = new Canvas();
        GraphicsContext context = canvas.getGraphicsContext2D();
        gamePane = new Pane(canvas);
        
    }


    public static void main(String[] args) {
        launch(args);
    }
}
