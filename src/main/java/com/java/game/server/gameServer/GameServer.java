package com.java.game.server.gameServer;

import com.java.game.common.Common;
import com.java.game.server.gameServer.manager.ChatManager;
import com.java.game.server.gameServer.manager.GameManager;
import com.java.game.server.gameServer.manager.LogManager;
import com.java.game.server.gameServer.manager.RoomManager;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.java.Log;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

@Data
public class GameServer{

    // Socket
    private ServerSocket serverSocket;
    private Socket socket;

    // common Info
    private int gameServer_Port = Common.gameServer_port;
    private int logServer_port = Common.logServer_port;
    private String ip = Common.ip;

    //Manager
    private ChatManager chatManager;
    private GameManager gameManager;
    private LogManager logManager;
    private RoomManager roomManager;

    private ArrayList<Thread> t;

    public void connect() throws Exception{
        serverSocket = new ServerSocket(gameServer_Port);
    }


    public void start_Manager(){
        t = new ArrayList<Thread>();

    }









    public static void main(String[] args) throws Exception {
       GameServer gameServer = new GameServer();

    }

}
