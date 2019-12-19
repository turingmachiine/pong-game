package pong;

public class GameServer {
    public static final int PORT = 1234;

    public static void main(String[] args) {
        new GameRoomServer().start(PORT);
    }
}
