package com.java.game.server.logServer;

import com.java.game.common.Common;
import com.java.game.server.logServer.manager.ReadManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.java.Log;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

@Data
public class LogServer {

    // Socket
    private ServerSocket logServerSocket;
    private Socket socket;

    // 로그를 읽어들이는 쓰레드
    private static ReadManager readManager;



    private int port = Common.logServer_port;



    // 생성시 연결

    public LogServer() throws Exception {
        System.out.println("LogServer created");
        logServerSocket = new ServerSocket(port);
    }

    public void connect() throws Exception {
        // 서버소켓, 소켓 생성
            System.out.println("LogServer wait");
            socket = logServerSocket.accept();

            // 쓰레드 생성
            readManager = new ReadManager(socket);
            System.out.println("Log - ReadManager start");
    }

    public void start() throws Exception {
        while(true){
            //readManager가 중단된다면 다시 logServer wait시키기
            if(readManager==null)
                this.connect();
        }
    }

    public static void main(String[] args) throws Exception {
        LogServer logServer = new LogServer();
        logServer.connect();
        logServer.start();
    }
    //body end
}
