package com.java.game.server.gameServer;

import com.java.game.common.Common;
import com.java.game.server.gameServer.manager.ChatManager;
import com.java.game.server.gameServer.manager.LogManager;
import com.java.game.server.gameServer.manager.RoomManager;
import lombok.Data;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;


@Data
public class GameServer{

    private static int count = 0;

    // Socket
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private Socket logSocket;

    private ArrayList<Socket> clientSocketList;

    // common Info
    private int gameServer_Port = Common.gameServer_port;
    private int logServer_port = Common.logServer_port;
    private String ip = Common.ip;

    //Manager
   // private static ChatManager chatManager;
    private static LogManager logManager;
    private static HashMap<Integer, RoomManager> roomManagerList;
    private static RoomManager roomManager;

    private ArrayList<Thread> t;



    public GameServer() throws Exception {
        logSocket = new Socket(ip,logServer_port);
        System.out.println("LogServer Connected");

        logManager = new LogManager(logSocket,this);


        serverSocket = new ServerSocket(gameServer_Port);
        System.out.println("GameServer Created");
        clientSocketList = new ArrayList<Socket>();


    }

    // socket connect
    public void connect() throws Exception{
        System.out.println("GameServer wait");
        while(true) {
            clientSocket = serverSocket.accept();
            System.out.println("Client connected");

            clientSocketList.add(clientSocket);
            System.out.println("ClientList added"+ (++count)+"명 접속중");

            //채팅 쓰레드 생성 -
            ChatManager chatManager = new ChatManager(clientSocket);
            System.out.println("GameServer ChatManager start");



        }
    }



    public static void main(String[] args) throws Exception {
       GameServer gameServer = new GameServer();
       gameServer.connect();

    }

}
