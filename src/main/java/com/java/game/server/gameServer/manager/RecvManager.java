package com.java.game.server.gameServer.manager;

import com.java.game.common.Type;
import lombok.Data;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.net.Socket;


@Data
public class RecvManager extends Thread {

    private LogManager logManager;

    private Socket socket;
    private BufferedReader br;
    private String text;
    private String flag;

    public RecvManager(Socket socket, LogManager logManager) throws Exception{
        this.socket = socket;
        this.logManager = logManager;
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.start();
        System.out.println("Server RecvManager start");
    }

    @Override
    public void run() {
        text = "";
        try{
            while (true) {
                this.setFlag(br.readLine());
                if(this.flag.equals(Type.CHAT)) {
                    text = br.readLine();
                    //여기
                    System.out.println(text);
                    logManager.setInfo(flag, text);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            //if 소켓닫아주기.
        }

    }
}