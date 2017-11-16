package com.java.game.server.gameServer.manager;

import com.java.game.server.gameServer.GameServer;
import lombok.Data;

import java.io.DataOutputStream;
import java.net.Socket;

@Data
public class LogManager extends Thread {

    private GameServer gameServer;

    private Socket socket;
    private DataOutputStream dos;
    private String text;
    private String flag;

    //ClientSocketSize;
    private int size;

    public LogManager(Socket socket, GameServer gameServer) throws Exception{
        this.socket = socket;
        this.gameServer = gameServer;
        dos = new DataOutputStream(socket.getOutputStream());
        this.start();
        System.out.println("Server LogManager start");
    }

    @Override
    public void run() {
        //연결된 client 수
        size = gameServer.getClientSocketList().size();
        //

    }
}
