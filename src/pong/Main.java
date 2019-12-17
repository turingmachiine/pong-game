package pong;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class Main extends Application {

    Pane root;
    Pane startPane;
    StackPane gamePane;
    Timeline timeline;


    private boolean gameStarted;
    private int ballSpeedY = 1;
    private int ballSpeedX = 1;
    private double ballX = Constants.WIDTH / 2;
    private double ballY = Constants.HEIGHT / 2;
    private int firstScore;
    private int secondScroe;
    private double playerOneY = Constants.HEIGHT / 2;
    private double playerTwoY = Constants.HEIGHT / 2;

    @Override
    public void start(Stage stage) throws Exception{
        root = new Pane();
        root.setPrefSize(Constants.WIDTH, Constants.HEIGHT);
        Scene scene = new Scene(root);
        stage.setTitle("Pong Game");
        stage.setScene(scene);
        initStartPane();
        root.getChildren().add(startPane);
        stage.show();
    }


    private void initStartPane() {

        startPane = new Pane();
        Button button = new Button("Начать игру c компьютером");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                initGamePane();
                root.getChildren().setAll(gamePane);
                timeline.play();
            }
        });
        startPane.getChildren().add(button);
    }

    private void initGamePane() {
        firstScore = 0;
        secondScroe = 0;
        Canvas canvas = new Canvas(Constants.WIDTH, Constants.HEIGHT);
        GraphicsContext context = canvas.getGraphicsContext2D();
        KeyFrame kf = new KeyFrame(Duration.millis(10), e -> runGame(context));
        timeline = new Timeline(kf);
        timeline.setCycleCount(Timeline.INDEFINITE);

        canvas.setOnMouseMoved(e ->  playerOneY = e.getY());
        canvas.setOnMouseClicked(e ->  gameStarted = true);

        gamePane = new StackPane(canvas);
    }

    private void runGame(GraphicsContext context) {
        context.setFill(Color.WHITE);
        context.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
        if (gameStarted) {

        } else {
            context.setStroke(Color.BLACK);
            context.setTextAlign(TextAlignment.CENTER);
            context.strokeText("Click", Constants.WIDTH / 2, Constants.HEIGHT / 2);
            ballX = Constants.WIDTH / 2;
            ballY = Constants.HEIGHT / 2;
            ballSpeedX = new Random().nextInt(2) == 0 ? 1: -1;
            ballSpeedY = new Random().nextInt(2) == 0 ? 1: -1;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
