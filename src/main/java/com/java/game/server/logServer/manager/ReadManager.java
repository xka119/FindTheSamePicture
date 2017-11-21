package com.java.game.server.logServer.manager;

import com.java.game.common.Type;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class ReadManager extends Thread {

    private Socket socket;
    private BufferedReader br;
    private String text;

    private String time;

    private Date date;
    private SimpleDateFormat simpleDateFormat;

    public ReadManager(Socket socket) throws IOException {
        this.socket = socket;
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }


    @Override
    public void run() {
        text = "";
        time = "";
        date = new Date();
        simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분 ss초");

        while (true) {
            try{
                text = br.readLine();
                time = simpleDateFormat.format(date);
                System.out.println(time + text);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}
