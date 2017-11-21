package com.java.game.server.gameServer.model;

import lombok.Data;

import java.net.Socket;

@Data
public class User {

    private Socket socket;
    private String name;

    private int state;
    private String answer1,answer2;

    private String roomNumber;

    public User(){ }


    //생성자
    public User(Socket socket, String name, int state){
        this.socket = socket;
        this.name = name;
        this.state = state;
        //대기방인 경우
    }

    //body end
}
