package com.java.game.client.manager;

import com.java.game.common.Type;
import lombok.Data;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

@Data
public class SendManager extends Thread {

    private Socket socket;

    private DataOutputStream dos;
    private BufferedReader br;

    private String flag;
    private String text;

    public SendManager(Socket socket) throws IOException {
        this.socket = socket;

        dos = new DataOutputStream(this.socket.getOutputStream());
        br = new BufferedReader(new InputStreamReader(System.in));

        // Thread Start
        this.start();
        System.out.println("Client SendManager start");

    }


    @Override
    public void run() {
        //입력에 따라달라진다.
        //전송이라면 -> chat
        //입장이라면 -> room
        try {
            text = br.readLine();
            System.out.println("text: "+ text);

            dos.writeUTF(Type.CHAT);
            dos.writeUTF(text);
            dos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
