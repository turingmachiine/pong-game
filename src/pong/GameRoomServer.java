package pong;

import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class GameRoomServer {
    ClientHandler[] clients;
    int activePlayers;
    Game game = new Game();
    boolean flag = false;
    DataOutputStream[] outs;
    private boolean flag1 = true;

    public GameRoomServer() {
        clients = new ClientHandler[2];
        activePlayers = 0;
        outs = new DataOutputStream[2];
    }

    public void start(int port) {
        ServerSocket serverSocket;
        System.out.println("run the server, waiting for players");
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        while (true) {
            try {
                if (activePlayers < 2) {
                    new ClientHandler(serverSocket.accept());
                    System.out.println("new player");
                    DataOutputStream out = new DataOutputStream(clients[activePlayers].clientSocket.getOutputStream());
                    activePlayers++;
                    out.writeInt(activePlayers);
                    System.out.println("send index " + activePlayers + " for player" + activePlayers);
                }
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
            if (!flag && activePlayers == 2) {
                System.out.println("full of players, start game");
                for (ClientHandler handler: clients) {
                    handler.start();
                }
                game.setGameStarted(true);
                flag = true;
            }

            System.out.println("send coordinates...");
            if (activePlayers == 2) {
                for (int i = 0; i < 2; i++) {
                    try {
                        outs[i].writeUTF(game.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("success!");
                System.out.println("sent game: " + game.toString());
            }
            if (game.isGameStarted()) {
                game.ballX += game.ballSpeedX;
                game.ballY += game.ballSpeedY;
                if (game.ballY + Constants.BALL_RADIUS > Constants.HEIGHT || game.ballY < 0) {
                    game.ballSpeedY *= -1;
                }
                if (game.ballX + Constants.BALL_RADIUS < 0) {
                    game.setSecondScroe(game.getSecondScroe() + 1);
                    game.setGameStarted(false);
                } else if (game.ballX - Constants.BALL_RADIUS > Constants.WIDTH) {
                    game.setFirstScore(game.getFirstScore() + 1) ;
                    game.setGameStarted(false);
                } else if ((game.ballX + Constants.BALL_RADIUS >= Constants.WIDTH - Constants.PLAYER_WIDTH) &&
                        (game.ballY + Constants.BALL_RADIUS > game.playerTwoY) && (game.ballY - Constants.BALL_RADIUS <
                        game.playerTwoY + Constants.PLAYER_HEIGHT) || (game.ballX <= Constants.PLAYER_WIDTH) &&
                        (game.ballY + Constants.BALL_RADIUS > game.playerOneY) && (game.ballY - Constants.BALL_RADIUS <
                        game.playerOneY + Constants.PLAYER_HEIGHT)) {
                    if (Math.abs(game.ballSpeedX) < 8) {
                        game.ballSpeedX += game.ballSpeedX > 0 ? 1 : - 1;
                    }
                    game.ballSpeedY = Math.abs(game.ballSpeedY) <= 1 ? game.ballSpeedY * (new Random().nextInt(2) + 1) :
                            (int) Math.signum(1.0 * game.ballSpeedY);
                    game.ballSpeedX *= -1;
                }
            } else {
                game.ballX = Constants.WIDTH / 2;
                game.ballY = Constants.HEIGHT / 2;
                game.ballSpeedX = new Random().nextInt(2) == 0 ? 1: -1;
                game.ballSpeedY = new Random().nextInt(2) == 0 ? 1: -1;
            }
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private class ClientHandler extends Thread {
        private Socket clientSocket;
        private BufferedReader in;
        private ObjectOutputStream out;

        ClientHandler(Socket socket) {
            this.clientSocket = socket;
            clients[activePlayers] = this;
            try {
                outs[activePlayers] = new DataOutputStream(this.clientSocket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("New client");
        }

        @Override
        public void run() {

            try {
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                while (true) {
                    System.out.println("waiting for moves");
                    while ((inputLine = in.readLine()) != null) {
                        if (inputLine.split(":")[0].equals("1")) {
                            game.setPlayerOneY(Double.parseDouble(inputLine.split(":")[1]));
                        } else if (inputLine.split(":")[0].equals("2")) {
                            game.setPlayerTwoY(Double.parseDouble(inputLine.split(":")[1]));
                        }
                    }
                }
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
