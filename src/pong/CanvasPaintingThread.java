package pong;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.io.*;
import java.net.Socket;

public class CanvasPaintingThread extends Thread {
    Socket socket;
    GraphicsContext context;
    DataInputStream in;

    public CanvasPaintingThread(Socket socket, GraphicsContext context, DataInputStream stream) {
        this.context = context;
        this.socket = socket;
        in = stream;
    }

    @Override
    public void run() {
        System.out.println();
        while (true) {
            Game game;
            System.out.print("trying to get object...");
            String temp = null;
            try {
                temp = in.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (temp != null) {
                String[] strings = temp.split(",");

                game = new Game(Boolean.parseBoolean(strings[0]), Integer.parseInt(strings[1]), Integer.parseInt(strings[2]),
                        Double.parseDouble(strings[3]), Double.parseDouble(strings[4]), Integer.parseInt(strings[5]), Integer.parseInt(strings[6]),
                        Double.parseDouble(strings[7]), Double.parseDouble(strings[8]));
                System.out.println("success!");
                System.out.println("received object game: " + game.toString());

                System.out.print("painting canvas...");
                System.out.print("painting backgroud...");
                context.setFill(Color.WHITE);
                context.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
                System.out.println("success!");
                System.out.print("painting first player...");
                context.setFill(Color.BLUEVIOLET);
                context.fillRect(0, game.playerOneY, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
                System.out.println("success!");
                System.out.print("painting second player...");
                context.setFill(Color.DARKRED);
                context.fillRect(Constants.WIDTH - Constants.PLAYER_WIDTH, game.playerTwoY, Constants.PLAYER_WIDTH,
                        Constants.PLAYER_HEIGHT);
                System.out.println("success!");
                System.out.print("painting ball...");
                context.setFill(Color.BLACK);
                context.fillOval(game.ballX, game.ballY, Constants.BALL_RADIUS, Constants.BALL_RADIUS);
                System.out.println("success!");
                if (!game.isGameStarted()) {
                    context.setStroke(Color.BLACK);
                    context.setTextAlign(TextAlignment.CENTER);
                    context.strokeText("Click and wait for opponent click", Constants.WIDTH / 2, Constants.HEIGHT / 2);
                }
                context.setFill(Color.BLACK);
                context.fillText("First " + game.getFirstScore() + " - Second " + game.getSecondScroe(), Constants.WIDTH / 4, 70);
                System.out.println("success!");
            }
        }
    }
}
