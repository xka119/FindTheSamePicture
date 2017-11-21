package com.java.game.server.gameServer.manager;

import com.java.game.common.Common;
import com.java.game.common.Type;
import com.java.game.server.gameServer.model.User;
import lombok.Data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


@Data
public class GameServer{

    private static int count = 0;


    // Socket
     ServerSocket serverSocket;
     Socket clientSocket;

     static Socket logSocket;

    // common Info
     int gameServer_Port = Common.gameServer_port;
     int logServer_port = Common.logServer_port;
     String ip = Common.ip;

    //접속 유저 리스트 - socket, name을 가지고 있다 //state, score, 등등등등등.
     static ArrayList<User> userList;



    //Manager
     static RoomManager roomManager;


    // GmaeServer 시작시 LogManager, RoomManager는 반드시 실행되어야한다.
    public GameServer() throws Exception {
        logSocket = new Socket(ip,logServer_port);
        System.out.println("LogServer Connected");

        // Manager Start
        roomManager = new RoomManager();

        serverSocket = new ServerSocket(gameServer_Port);
        System.out.println("GameServer Created");


        //list 생성
        userList = new ArrayList<User>();

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
//            br.readLine();

            //"userList added"
            String name = br.readLine();
            User user = new User(clientSocket, name, Type.WAITING_ROOM);
            addUser(user);
            RecvManager recvManager = new RecvManager(user);
            recvManager.start();

            PrintWriter pw = new PrintWriter(logSocket.getOutputStream());
            pw.println(name + "님이 접속하셨습니다.\n" + userList.size() + "명 접속중" );
            pw.flush();

            System.out.println(name + "님이 접속하셨습니다");
            System.out.println(userList.size() +"명 접속중");


        }
    }
    //사람 추가
    public static void addUser(User user){
        userList.add(user);
    }
    //사람 삭제
    public static void removeUser(User user){
        userList.remove(user);
    }







    public static void main(String[] args) throws Exception {
       GameServer gameServer = new GameServer();
       gameServer.connect();

    }

}
