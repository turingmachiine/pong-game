package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    Pane root;
    Pane startPane;
    Pane gamePane;

    @Override
    public void start(Stage stage) throws Exception{
        root = new Pane();
        root.setPrefSize(200, 200);
        Scene scene = new Scene(root);
        stage.setTitle("Navigator");
        stage.setScene(scene);
        initStartPane();
        initGamePane();
        root.getChildren().add(startPane);
        stage.show();
    }


    private void initStartPane() {
        startPane = new Pane();
        Button button = new Button("Начать игру");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                root.getChildren().setAll(gamePane);
            }
        });
        startPane.getChildren().add(button);
    }

    private void initGamePane() {
        // game
    }


    public static void main(String[] args) {
        launch(args);
    }
}
