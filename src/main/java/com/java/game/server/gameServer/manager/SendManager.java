package com.java.game.server.gameServer.manager;

import lombok.Data;
import org.apache.ibatis.logging.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


@Data
public class SendManager extends Thread {


    private Socket socket;

    private BufferedReader br;
    private PrintWriter pw;

    public SendManager(Socket socket) throws  Exception{
        this.socket = socket;
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pw = new PrintWriter(socket.getOutputStream());
        this.start();
        System.out.println("Server SendManager start");

    }

    public void sendMsg(String text){
        pw.println(text);
        pw.flush();
    }

    @Override
    public void run() {
        /*
        1. 전체에 메세지 보낼때 사용함
         */
    }
}
