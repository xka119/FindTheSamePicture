package com.java.game.client;


import com.java.game.common.Common;
import lombok.Data;
import lombok.extern.java.Log;

import java.net.Socket;

@Data
@Log
public class Client {
    private Socket socket;
    private String ip = Common.ip;
    private int port = Common.Client_port;

    public void connect() throws Exception{
        socket = new Socket(ip, port);
        System.out.println("서버와 연결됨");
    }
    public static void main(String[] args) throws Exception {
        new Client().connect();

    }


}
