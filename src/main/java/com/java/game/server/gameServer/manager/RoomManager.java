package com.java.game.server.gameServer.manager;

import com.java.game.server.gameServer.model.Room;

import java.net.Socket;
import java.util.ArrayList;

public class RoomManager extends Thread {

    private static int ROOM_SIZE = 9;
    private Socket socket;

    private ArrayList<Room> roomList;



    public RoomManager(Socket socket){
        this.socket = socket;
        roomList = new ArrayList<Room>();
        for(int i=0; i<ROOM_SIZE; i++){
        }
        this.start();
        System.out.println("Server RoomManager start");
    }
    public void run() {
    }
}
