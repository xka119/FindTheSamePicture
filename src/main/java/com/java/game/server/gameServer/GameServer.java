package com.java.game.server.gameServer;

import com.java.game.common.Common;
import com.java.game.common.Type;
import com.java.game.server.gameServer.model.game.Room;
import com.java.game.server.gameServer.model.game.User;
import lombok.Data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


@Data
public class GameServer{

    // Socket
     ServerSocket serverSocket;
     Socket clientSocket;

     PrintWriter pw;
     BufferedReader br;

     static Socket logSocket;

    // common Info
     int gameServer_Port = Common.gameServer_port;
     int logServer_port = Common.logServer_port;
     String ip = Common.ip;

    //접속 유저 리스트 - socket, name을 가지고 있다 //state, score, 등등등등등.
     static ArrayList<User> userList;

     //RoomList
    static ArrayList<Room> roomList;

    //Manager

    // GmaeServer 시작시 LogManager, RoomManager는 반드시 실행되어야한다.
    public GameServer() throws Exception {
        logSocket = new Socket(ip,logServer_port);
        pw = new PrintWriter(logSocket.getOutputStream());
        pw.println("게임 서버 연결");
        pw.flush();
//        System.out.println("로그 서버 연결");

        // Room Setting
        this.setting_Room();

        serverSocket = new ServerSocket(gameServer_Port);


        //list 생성
        userList = new ArrayList<User>();

    }

    // socket connect
    public void connect() throws Exception{
        System.out.println("게임서버 대기중");
        while(true) {
            clientSocket = serverSocket.accept();
//            System.out.println("Client connected");

            //처음에 입장시 이름과 소켓을 입력한다.
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


            //"userList added"
            String name = br.readLine();
            User user = new User(clientSocket, name, Type.WAITING_ROOM);
            addUser(user);
            UserManager userManager = new UserManager(user);
            userManager.start();

            pw = new PrintWriter(logSocket.getOutputStream());
            pw.println("["+name + "]님이 접속하셨습니다.\n" + userList.size() + "명 접속중" );
            pw.flush();

            ServerUtility.send_user_List(user);
//            ServerUtility.send_user_List();



        }
    }

    // Room Setting
    public void setting_Room(){
        roomList = new ArrayList<Room>();
        for(int i=0; i<Common.ROOM_SIZE; i++){
            roomList.add(new Room(i+1));
//            System.out.println((i+1)+"번 방 생성");
        }
    }
    //사람 추가
    public static synchronized void addUser(User user){
        userList.add(user);
    }
    //사람 삭제
    public static synchronized void removeUser(User user){
        userList.remove(user);
    }







    public static void main(String[] args) throws Exception {
       GameServer gameServer = new GameServer();
       gameServer.connect();

    }

}
