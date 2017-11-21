package com.java.game.server.gameServer;

import com.java.game.common.Common;
import com.java.game.common.Type;
import com.java.game.server.gameServer.manager.ChatManager;
import com.java.game.server.gameServer.manager.LogManager;
import com.java.game.server.gameServer.manager.RoomManager;
import com.java.game.server.gameServer.model.User;
import lombok.Data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;


@Data
public class GameServer{

    private static int count = 0;


    // Socket
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private Socket logSocket;

    // common Info
    private int gameServer_Port = Common.gameServer_port;
    private int logServer_port = Common.logServer_port;
    private String ip = Common.ip;

    //접속 유저 리스트 - socket, name을 가지고 있다
    private static ArrayList<User> userList;

    //접속 유저 chatManager - send, recv Manager를 가지고 있다
    private static ArrayList<ChatManager> chatManagerList;


    //Manager
    private static LogManager logManager;
    private static RoomManager roomManager;


    // GmaeServer 시작시 LogManager, RoomManager는 반드시 실행되어야한다.
    public GameServer() throws Exception {
        logSocket = new Socket(ip,logServer_port);
        System.out.println("LogServer Connected");

        // Manager Start
        logManager = new LogManager(logSocket);
        roomManager = new RoomManager();

        serverSocket = new ServerSocket(gameServer_Port);
        System.out.println("GameServer Created");


        //list 생성
        userList = new ArrayList<User>();
        chatManagerList = new ArrayList<ChatManager>();

    }

    // socket connect
    public void connect() throws Exception{
        System.out.println("GameServer wait");
        while(true) {
            clientSocket = serverSocket.accept();
            System.out.println("Client connected");

            //처음에 입장시 이름과 소켓을 입력한다.
            BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //flag 비우기
            br.readLine();

            String name = br.readLine();
            User user = new User(clientSocket, name);
            userList.add(user);
            System.out.println(user.getName()+"님이 입장하셨습니다");
            System.out.println(userList.size() +"명 접속중");
            //"userList added"

            PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());
            pw.println(Type.CHAT);
            pw.println("환영합니다");
            pw.flush();
            System.out.println("pw.flush()");

            System.out.println("GameServer ChatManager start");
        }
    }

    public synchronized void sendtoWaitingRoom(String text){
        for(int i=0; i<userList.size(); i++){
            // waitingroom = true;
            if(userList.get(i).isState()){
                chatManagerList.get(i).getSendManager().sendMsg(text);
            }
        }

    }




    public static void main(String[] args) throws Exception {
       GameServer gameServer = new GameServer();
       gameServer.connect();

    }

}
