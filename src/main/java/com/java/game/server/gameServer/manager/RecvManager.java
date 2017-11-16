package com.java.game.server.gameServer.manager;

import lombok.Data;

import java.io.DataInputStream;
import java.net.Socket;


@Data
public class RecvManager extends Thread {


    private Socket socket;
    private DataInputStream dis;
    private String text;
    private String flag;

    public RecvManager(Socket socket) throws Exception{
        this.socket = socket;
        dis = new DataInputStream(socket.getInputStream());
        this.start();
        System.out.println("Server RecvManager start");
    }

    @Override
    public void run() {
        text = "";
        try{
            while((text=dis.readUTF())!=null){
                System.out.println("text: "+text);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        //finally 처리 해줘야함.

    }
}