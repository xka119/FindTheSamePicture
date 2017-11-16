package com.java.game.client;


import com.java.game.client.manager.ChatManager;
import com.java.game.client.manager.GameManager;
import com.java.game.client.manager.RecvManager;
import com.java.game.client.manager.SendManager;
import com.java.game.client.ui.LoginUI;
import com.java.game.client.ui.RoomUI;
import com.java.game.client.ui.WaitingUI;
import com.java.game.common.Common;
import lombok.Data;
import lombok.extern.java.Log;

import java.net.Socket;

@Data
public class Client {

    //default
    private Socket socket;
    private String ip = Common.ip;
    private int port = Common.gameServer_port;

    //Manager
    private static ChatManager chatManager;
    private static GameManager gameManager;

    private static RecvManager recvManager;
    private static SendManager sendManager;

    // 필요한 것들은?
    private boolean state = true; //대기방= true, 방 = false
    private int score; // 점수
    private String flag; // flag - connect, chat, game, log, etc
    // private 두개의 점수 맞출수 있는 신호 두개 필요함



    public Client() throws Exception {
     //   loginUI = new LoginUI();
    }

    // socket connect
    public void connect() throws Exception{
        socket = new Socket(ip,port);
        System.out.println("Server Connected");

        chatManager = new ChatManager(socket);
        recvManager = chatManager.getRecvManager();
        sendManager = chatManager.getSendManager();

        gameManager = new GameManager(socket);
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
        client.connect();
        client.start();

    }


}
