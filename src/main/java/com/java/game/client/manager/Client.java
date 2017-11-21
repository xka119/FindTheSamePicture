package com.java.game.client.manager;


import com.java.game.client.ui.LoginUI;
import com.java.game.client.ui.RoomUI;
import com.java.game.client.ui.WaitingUI;
import com.java.game.common.Common;
import lombok.Data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Data
public class Client {

    //UI
    LoginUI loginUI;
    WaitingUI waitingUI;
    RoomUI roomUI;

    //default
    private Socket socket;
    private String ip = Common.ip;
    private int port = Common.gameServer_port;

    //Manager
    private static RecvManager recvManager;
    private static SendManager sendManager;

    private static GameManager gameManager;

    // 필요한 것들은?
    private static String name;
    private int state; //대기방= true, 방 = false
    private int score; // 점수
    private String answer1,answer2;
    private String flag; // flag - connect, chat, game, log, etc
    // private 두개의 점수 맞출수 있는 신호 두개 필요함


    public Client(String _name) {
        name = _name;
    }
    public Client(){}

    // socket connect
    public void connect() throws Exception{
        socket = new Socket(ip,port);
//        BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
//        PrintWriter pw = new PrintWriter(socket.getOutputStream());
//        System.out.println("이름을 입력하세요");
//        name = br.readLine();
//        pw.println(name);
//        pw.flush();
        System.out.println("Server Connected");


        recvManager = new RecvManager(socket);
        sendManager = new SendManager(socket);
        System.out.println("Thread Start");




    }
    public void threadStart(){
        recvManager.start();
        sendManager.start();
    }



    public void enterRoom(){
        state =0;
        //룸 쓰레드를 생성은아니고..
    }
    public static void setName(String _name){
        name = _name;
    }



    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.connect();
        client.threadStart();

    }


}
