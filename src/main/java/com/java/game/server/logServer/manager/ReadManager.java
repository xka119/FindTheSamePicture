package com.java.game.server.logServer.manager;

import java.net.Socket;

public class ReadManager implements Runnable, LogServerManager {

    private Socket socket;

    private String flag;
    private String text;



    public ReadManager(Socket socket){
        this.socket = socket;
    }





    public void run() {

    }
}
