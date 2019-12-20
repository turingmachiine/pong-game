package pong;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class MainClient extends Application {

    Scene startScene;
    Scene gameScene;
    Stage stage;

    StackPane gamePane;
    Canvas canvas;
    GraphicsContext context;
    int name;
    DataInputStream in;

    private final String HOST = "localhost";
    private final int PORT = 1234;
    PrintWriter out;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        initStartScene();
        stage.setTitle("Pong Game");
        stage.setScene(startScene);
        stage.show();
        System.out.println("success!");

     }


    private void initGamePane() {
        canvas = new Canvas(Constants.WIDTH, Constants.HEIGHT);
        context = canvas.getGraphicsContext2D();

        gamePane = new StackPane(canvas);
    }

    private void initStartScene() {
        System.out.println("init of Start Scene...");
        Pane startPane = new Pane();
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        TextField host = new TextField();
        TextField port = new TextField();
        Button button = new Button("connect");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                initGameScene(host.getText(),Integer.parseInt(port.getText()));
                stage.setScene(gameScene);
                stage.show();
            }
        });
        vBox.getChildren().addAll(host, port, button);
        startPane.getChildren().addAll(vBox);
        startScene = new Scene(startPane);
    }

    private void initGameScene(String host, int port) {
        System.out.print("initialization of Pane...");
        initGamePane();
        System.out.println("success!");
        System.out.print("trying to setup socket...");
        Socket socket  = null;
        try {
            socket = new Socket(host, port);
            in = new DataInputStream(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
            name = in.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("success!");
        System.out.println("received index " + name + " from server");

        System.out.print("start canvas...");

        new CanvasPaintingThread(socket, context, in).start();

        canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                out.println("" + name + ":" + event.getY());
                System.out.println("sent to server" + name + ":" + event.getY());
            }
        });

        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                out.println("" + name + ":" + "startGame");
                System.out.println("" + name + ":" + "ready to start");
            }
        });

        System.out.print("init scene...");
        gameScene = new Scene(gamePane);
        System.out.println("success!");

    }

    public static void main(String[] args) { launch(args);
    }
}
