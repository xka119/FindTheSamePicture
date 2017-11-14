package com.java.game.server.logServer;

import com.java.game.common.Common;
import com.java.game.server.logServer.manager.ReadManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.java.Log;

import java.net.ServerSocket;
import java.net.Socket;

@Data
public class LogServer {

    private ServerSocket logServerSocket;
    private Socket socket;
    private int port = Common.logServer_port;

    private ReadManager readManager;
    private Thread t;


    // 생성시 연결

    public LogServer() throws Exception {
        this.connect();
        System.out.println("LogServer created");
    }

    public void connect() throws Exception {
        // 서버소켓, 소켓 생성
        logServerSocket = new ServerSocket(port);
        System.out.println("LogServer wait");
        socket = logServerSocket.accept();

        // 쓰레드 생성
        readManager = new ReadManager(socket);
        t = new Thread(readManager);
        System.out.println("Log - ReadManager start");

    }
    /*

     */




    public static void main(String[] args) throws Exception {
        LogServer logServer = new LogServer();
    }

}
