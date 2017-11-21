package com.java.game.server.gameServer.manager;


import lombok.Data;

import java.net.Socket;

@Data
public class ChatManager  {

    private Socket socket;
    private SendManager sendManager;
    private RecvManager recvManager;

    private LogManager logManager;

    public ChatManager(Socket socket, LogManager logManager) throws Exception{
        System.out.println("Client ChatManager start");
        this.socket = socket;
        this.logManager = logManager;
        recvManager = new RecvManager(this.socket, logManager);
        sendManager = new SendManager(this.socket, logManager);

    }

    public void run() {
    }
}
