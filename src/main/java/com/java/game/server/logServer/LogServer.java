package com.java.game.server.logServer;

import com.java.game.common.Common;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.java.Log;

import java.net.ServerSocket;
import java.net.Socket;

@Data
@Log
public class LogServer {

    private ServerSocket serverSocket;
    private Socket socket;
    private int port = Common.Server_port;

    public void connect() throws Exception{
        serverSocket = new ServerSocket(port);
        System.out.println("대기중");
        socket = serverSocket.accept();
        System.out.println("메인서버 연결됨");
    }
    public static void main(String[] args) throws Exception {
        new LogServer().connect();
    }

}
