package com.java.game.server.gameServer.manager;

import com.java.game.server.gameServer.GameServer;
import lombok.Data;

import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class LogManager extends Thread {


    private Socket socket;
    private PrintWriter pw;
    private String text;
    private String flag;

    //ClientSocketSize;
    private int size;

    public LogManager(Socket socket) throws Exception{
        this.socket = socket;
        pw = new PrintWriter(this.socket.getOutputStream());
        this.start();
        System.out.println("Server LogManager start");
    }

    @Override
    public void run() {
        /*
        1. 각 클라이언트로 부터 채팅 메세지를 받는다.
        2. pw를 사용하여 LogServer로 전송한다.
        3. flush()한다.
        4. 위를 반복한다
         */
        text = "";
        while(true){
            //메세지가 있다면
            while(!text.equals("")){

                pw.println(this.flag);
                pw.println(this.text);
                pw.flush();

                //초기화
                flag = "";
                text = "";
            }

        }

    }

    //동기화
    public synchronized void setInfo(String flag, String text){
        this.flag = flag;
        this.text = text;
    }
}
