package com.java.game.client;

import lombok.Data;

import java.io.PrintWriter;
import java.net.Socket;

@Data
public class GameManager extends Thread {

    private Socket socket;
    private PrintWriter pw;

    private Client client;
    private String answer1, answer2;

    private static GameLogic gameLogic;

    public GameManager(Socket socket, Client client) throws Exception {
        this.socket = socket;
        this.client = client;

        pw = new PrintWriter(socket.getOutputStream());
        this.start();
        System.out.println("Client GameManger Start");
    }

    @Override
    public void run() {
        /*
        1. Client로 부터 두개의 버튼 입력을 받는다.
        2. 버튼의 인덱스 값이 모두 채워지면 Server에 보낸다
        3. Type = Game이고 순서대로 answer1,answer2를 보낸다.
        4. flush()
        5. 그리고 대기한다.
         */
        while(true){

        }

    }

}
