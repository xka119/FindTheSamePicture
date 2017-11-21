package com.java.game.client.manager;

import com.java.game.common.Type;
import lombok.Data;

import java.io.*;
import java.net.Socket;

@Data
public class SendManager extends Thread {

    private Socket socket;

    private BufferedReader br;
    private PrintWriter pw;

    private String flag;
    private String text;

    public SendManager(Socket socket) throws IOException {
        this.socket = socket;
        //입력
        br = new BufferedReader(new InputStreamReader(System.in));
        //전송
        pw = new PrintWriter(socket.getOutputStream());
    }


    @Override
    public void run() {
        /*
        1. 메세지 전송 기능만 수행한다.
        2. 메세지를 입력하면 서버에 메세지를 전달한다.
        3. Type = CHAT 과 text를 전송한다.
        4. flush()한 후 대기한다.
         */
        //입력에 따라달라진다.
        //전송이라면 -> chat
        //입장이라면 -> room\
        text = "";
        while(true) {
            try {
                //입력
                text = br.readLine();
//                System.out.println("text: " + text);

                pw.println(text);
                pw.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
