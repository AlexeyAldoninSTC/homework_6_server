package app;

import app.server.MyServer;

public class Main {
    public static void main(String[] args) {
        MyServer myServer = new MyServer();
        myServer.run();
    }
}
