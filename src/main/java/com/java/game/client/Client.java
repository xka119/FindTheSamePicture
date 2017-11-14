package com.java.game.client;


import com.java.game.client.ui.LoginUI;
import com.java.game.common.Common;
import lombok.Data;
import lombok.extern.java.Log;

import java.net.Socket;

@Data
public class Client {
    private Socket socket;
    private String ip = Common.ip;
    private int port = Common.gameServer_port;

    private LoginUI loginUI;
    public Client(){
        loginUI = new LoginUI();
    }

    public void connect() throws Exception{

    }
    public static void main(String[] args) throws Exception {
        Client client = new Client();

    }


}
