package com.java.game.server.gameServer.manager;

import com.java.game.server.gameServer.model.Room;

import java.net.Socket;
import java.util.ArrayList;

public class RoomManager extends Thread {

    private static int ROOM_SIZE = 9;

    private static ArrayList<Room> roomList;



    public RoomManager(){
        this.setting();
        this.start();
        System.out.println("Server RoomManager start");
    }


    // Room 초기화 - Room은 9개를 만들고 번호를 갖고있으나 user정보는 가지고 있지 않다.
    public void setting(){
        roomList = new ArrayList<Room>();
        Room room;
        for(int i=0; i<ROOM_SIZE; i++){
            room = new Room();
            room.setRoomNumber(String.valueOf(i+1));
            roomList.add(room);
        }
        System.out.println("Room Setting finished");
    }
    public void run() {
    }
}
