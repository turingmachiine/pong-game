package pong;

import java.io.Serializable;

public class Game implements Serializable {

    private boolean gameStarted = false;
    int ballSpeedY = 1;
    int ballSpeedX = 1;
    double ballX = Constants.WIDTH / 2;
    double ballY = Constants.HEIGHT / 2;
    private int firstScore = 0;
    private int secondScroe = 0;
    double playerOneY = Constants.HEIGHT / 2;
    double playerTwoY = Constants.HEIGHT / 2;

    public Game(boolean gameStarted, int ballSpeedY, int ballSpeedX, double ballX, double ballY, int firstScore, int secondScroe, double playerOneY, double playerTwoY) {
        this.gameStarted = gameStarted;
        this.ballSpeedY = ballSpeedY;
        this.ballSpeedX = ballSpeedX;
        this.ballX = ballX;
        this.ballY = ballY;
        this.firstScore = firstScore;
        this.secondScroe = secondScroe;
        this.playerOneY = playerOneY;
        this.playerTwoY = playerTwoY;
    }

    public Game() {
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public int getFirstScore() {
        return firstScore;
    }

    public void setFirstScore(int firstScore) {
        this.firstScore = firstScore;
    }

    public int getSecondScroe() {
        return secondScroe;
    }

    public void setSecondScroe(int secondScroe) {
        this.secondScroe = secondScroe;
    }

    public double getPlayerOneY() {
        return playerOneY;
    }

    public void setPlayerOneY(double playerOneY) {
        this.playerOneY = playerOneY;
    }

    public double getPlayerTwoY() {
        return playerTwoY;
    }

    public void setPlayerTwoY(double playerTwoY) {
        this.playerTwoY = playerTwoY;
    }

    @Override
    public String toString() {
        return  gameStarted +
                "," + ballSpeedY +
                "," + ballSpeedX +
                "," + ballX +
                "," + ballY +
                "," + firstScore +
                "," + secondScroe +
                "," + playerOneY +
                "," + playerTwoY;
    }
}
