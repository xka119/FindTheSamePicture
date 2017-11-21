package com.java.game.server.logServer;

import com.java.game.common.Common;
import com.java.game.server.logServer.manager.LogManager;
import lombok.Data;

import java.net.ServerSocket;
import java.net.Socket;

@Data
public class LogServer {

    // Socket
    private ServerSocket logServerSocket;
    private Socket socket;

    // 로그를 읽어들이는 쓰레드
    private static LogManager readManager;

    private int port = Common.logServer_port;

    // 생성시 연결

    public LogServer() throws Exception {
        logServerSocket = new ServerSocket(port);
        System.out.println("LogServer created");
    }

    public void connect() throws Exception {
        // 서버소켓, 소켓 생성
        System.out.println("LogServer wait");
        while (true) {
            socket = logServerSocket.accept();

            // 쓰레드 생성
            readManager = new LogManager(socket);
            readManager.start();
            System.out.println("LogServer readManager start");
        }
    }


    public static void main(String[] args) throws Exception {
        LogServer logServer = new LogServer();
        logServer.connect();
    }
    //body end
}
