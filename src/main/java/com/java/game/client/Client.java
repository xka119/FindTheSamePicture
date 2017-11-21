package com.java.game.client;


import com.java.game.client.manager.GameManager;
import com.java.game.client.manager.RecvManager;
import com.java.game.client.manager.SendManager;
import com.java.game.client.ui.LoginUI;
import com.java.game.client.ui.RoomUI;
import com.java.game.client.ui.WaitingUI;
import com.java.game.common.Common;
import com.java.game.common.Type;
import lombok.Data;

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
    private String name;
    private boolean state = true; //대기방= true, 방 = false
    private int score; // 점수
    private String answer1,answer2;
    private String flag; // flag - connect, chat, game, log, etc
    // private 두개의 점수 맞출수 있는 신호 두개 필요함


    public Client() throws Exception {
        loginUI = new LoginUI(this);
    }

    // socket connect
    public void connect() throws Exception{
        socket = new Socket(ip,port);
        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        pw.println(Type.CONNECT);
        pw.println(this.name);
        pw.flush();
        System.out.println("Server Connected");


        recvManager = new RecvManager(socket);
        sendManager = new SendManager(socket);


        //이건 Room입장해서 시작할때 만들면됨
//        gameManager = new GameManager(socket, this);
    }

    public void start(){
        while(true){
            //화면에서 x를 누르면 끝내야함.
        }
    }

    public void enterRoom(){
        state = false;
        //룸 쓰레드를 생성은아니고..
    }
    public void gameStart(){

    }


    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.start();

    }


}
