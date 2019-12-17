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
            ballX += ballSpeedX;
            ballY += ballSpeedY;
            if (ballY + Constants.BALL_RADIUS > Constants.HEIGHT || ballY < 0) {
                ballSpeedY *= -1;
            }
            if (ballX + Constants.BALL_RADIUS < 0) {
                secondScroe += 1;
                gameStarted = false;
            } else if (ballX - Constants.BALL_RADIUS > Constants.WIDTH) {
                firstScore += 1;
                gameStarted = false;
            } else if ((ballX + Constants.BALL_RADIUS >= Constants.WIDTH - Constants.PLAYER_WIDTH) && (ballY + Constants.BALL_RADIUS > playerTwoY) &&
                    (ballY - Constants.BALL_RADIUS < playerTwoY + Constants.PLAYER_HEIGHT) || (ballX <= Constants.PLAYER_WIDTH) &&
                    (ballY + Constants.BALL_RADIUS > playerOneY) && (ballY - Constants.BALL_RADIUS < playerOneY + Constants.PLAYER_HEIGHT)) {
                if (Math.abs(ballSpeedX) < 8) {
                    ballSpeedX += ballSpeedX > 0 ? 1 : - 1;
                }
                ballSpeedY = Math.abs(ballSpeedY) <= 1 ? ballSpeedY * (new Random().nextInt(2) + 1) :
                        (int) Math.signum(1.0 * ballSpeedY);
                ballSpeedX *= -1;
            }
            if(ballX < Constants.WIDTH - Constants.WIDTH  / 3) {
                playerTwoY = ballY - Constants.PLAYER_HEIGHT / 2;
            }  else {
                int step = new Random().nextInt(2) + 1;
                playerTwoY =  ballY > playerTwoY + Constants.PLAYER_HEIGHT / 2 ? playerTwoY + step: playerTwoY - step;
            }
            context.setFill(Color.BLACK);
            context.fillOval(ballX, ballY, Constants.BALL_RADIUS, Constants.BALL_RADIUS);
        } else {
            context.setStroke(Color.BLACK);
            context.setTextAlign(TextAlignment.CENTER);
            context.strokeText("Click", Constants.WIDTH / 2, Constants.HEIGHT / 2);
            ballX = Constants.WIDTH / 2;
            ballY = Constants.HEIGHT / 2;
            ballSpeedX = new Random().nextInt(2) == 0 ? 1: -1;
            ballSpeedY = new Random().nextInt(2) == 0 ? 1: -1;
        }

        context.setFill(Color.BLUEVIOLET);
        context.fillRect(0, playerOneY, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
        context.setFill(Color.DARKRED);
        context.fillRect(Constants.WIDTH - Constants.PLAYER_WIDTH, playerTwoY, Constants.PLAYER_WIDTH,
                Constants.PLAYER_HEIGHT);
        context.setFill(Color.BLACK);
        context.fillText("First " + firstScore + " - Second " + secondScroe, Constants.WIDTH / 4, 70);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
