package com.java.game.server.gameServer.model.game;

import lombok.Data;

import java.net.Socket;

@Data
public class User {

    private Socket socket;
    private String name;

    private int state;
    private int score;
    private boolean start;
    private String answer1,answer2;


    public User(){ }


    //생성자
    public User(Socket socket, String name, int state){
        this.socket = socket;
        this.name = name;
        this.state = state;
        this.start = false;
        //대기방인 경우
    }

    //body end
}
