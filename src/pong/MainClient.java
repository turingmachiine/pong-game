package pong;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class MainClient extends Application {

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
        System.out.print("initialization of Pane...");
        initGamePane();
        System.out.println("success!");
        System.out.print("trying to setup socket...");
        Socket socket  = null;
        try {
            socket = new Socket(HOST, PORT);
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

        System.out.print("showing the screen...");
        Scene scene = new Scene(gamePane);
        primaryStage.setTitle("Pong Game");
        primaryStage.setScene(scene);
        primaryStage.show();
        System.out.println("success!");

     }


    private void initGamePane() {
        canvas = new Canvas(Constants.WIDTH, Constants.HEIGHT);
        context = canvas.getGraphicsContext2D();

        gamePane = new StackPane(canvas);
    }

    public static void main(String[] args) { launch(args);
    }
}
