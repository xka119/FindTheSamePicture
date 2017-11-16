package com.java.game.client.manager;

import lombok.Data;

import java.net.Socket;

@Data
public class ChatManager {

    private Socket socket;
    private RecvManager recvManager;
    private SendManager sendManager;

    public ChatManager(Socket socket) throws Exception{
        System.out.println("Client ChatManager start");

        this.socket = socket;
        recvManager = new RecvManager(this.socket);
        sendManager = new SendManager(this.socket);

    }

}
