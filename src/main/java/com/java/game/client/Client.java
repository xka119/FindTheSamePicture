package com.java.game.client;


import com.java.game.client.ui.LoginUI;
import com.java.game.client.ui.RoomUI;
import com.java.game.client.ui.WaitingUI;
import com.java.game.common.Common;
import lombok.Data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    //UI
    LoginUI loginUI;
    WaitingUI waitingUI;
    RoomUI roomUI;

    //default
    private Socket socket;
    private String ip = Common.ip;
    private int port = Common.gameServer_port;


    //
    private BufferedReader br;
    private PrintWriter pw;

    //Manager
    private static RecvManager recvManager;
    private static SendManager sendManager;

    private static GameManager gameManager;

    // 필요한 것들은?
    private String name;
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
        br =new BufferedReader(new InputStreamReader(System.in));
        pw = new PrintWriter(socket.getOutputStream());
//        System.out.println("이름을 입력하세요");
//        name = br.readLine();
        pw.println(name);
        pw.flush();
//        System.out.println("Server Connected");
    }

    public void threadStart() throws Exception{
        recvManager = new RecvManager(socket, loginUI, waitingUI, roomUI);
//        sendManager = new SendManager(socket);
//        System.out.println("Thread Start");
        recvManager.start();
//        sendManager.start();
    }

    public Socket getSocket() {
        return socket;
    }

    public String getName() {
        return name;
    }


    public void setName(String _name){
        name = _name;
    }

    //UI setting method
//    public static void setLoginUI(LoginUI _loginUI){
//        loginUI = _loginUI;
//    }
//
    public  void setWaitingUI(WaitingUI _waitingUI){
        waitingUI = _waitingUI;
    }

    public void setRoomUI(RoomUI roomUI) {
        this.roomUI = roomUI;
    }


    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.connect();
        client.threadStart();

    }



}