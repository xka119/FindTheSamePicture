package com.java.game.server.gameServer.model;

import java.util.ArrayList;

public class Room {

    private ArrayList<User> userList;

    public Room(){
        userList = new ArrayList<User>();
    }

    public void add(User user){
        userList.add(user);
    }

    public void remove(User user){
        userList.remove(user);
    }

}
