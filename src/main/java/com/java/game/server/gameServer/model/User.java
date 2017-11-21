package com.java.game.server.gameServer.model;

import com.java.game.server.gameServer.manager.ChatManager;
import lombok.Data;

import java.net.Socket;

@Data
public class User {

    private Socket socket;
    private String name;
    private boolean state;
    private String answer1,answer2;

    private String roomNumber;

    public User(){ }


    //생성자
    public User(Socket socket, String name){
        this.socket = socket;
        this.name = name;
        //대기방인 경우
        this.state = true;
        System.out.println(name+" 대기방 입장");
    }

    //body end
}
