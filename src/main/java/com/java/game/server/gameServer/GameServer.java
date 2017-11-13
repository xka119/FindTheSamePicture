package com.java.game.server.gameServer;

import com.java.game.common.Common;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.java.Log;

import java.net.ServerSocket;
import java.net.Socket;

@Data
@Log
public class GameServer{

    private ServerSocket serverSocket;
    private Socket socket;
    private int Client_port = Common.Client_port;
    private int Server_port = Common.Server_port;
    private String ip = Common.ip;

    public void connect() throws Exception{
        serverSocket = new ServerSocket(Client_port);
        System.out.println("watit");
        socket = serverSocket.accept();
        System.out.println("클라이언트 연결됨");
        System.out.println("log서버연결");
        socket = new Socket(ip,Server_port);
        System.out.println("로그서버연결");
    }
    public static void main(String[] args) throws Exception {
        new GameServer().connect();

    }

}
