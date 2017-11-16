package com.java.game.server.gameServer.manager;

import lombok.Data;

import java.io.DataOutputStream;
import java.net.Socket;


@Data
public class SendManager extends Thread {

    private Socket socket;
    private DataOutputStream dos;

    public SendManager(Socket socket){
        this.socket = socket;
        this.start();
        System.out.println("Server SendManager start");

    }

    @Override
    public void run() {
    }
}
