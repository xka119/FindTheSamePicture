package com.java.game.server.gameServer.manager;


import lombok.Data;

import java.net.Socket;

@Data
public class ChatManager  {

    private Socket socket;
    private SendManager sendManager;
    private RecvManager recvManager;

    public ChatManager(Socket socket) throws Exception{
        System.out.println("Client ChatManager start");
        this.socket = socket;
        recvManager = new RecvManager(this.socket);
        sendManager = new SendManager(this.socket);

    }

    public void run() {
    }
}
