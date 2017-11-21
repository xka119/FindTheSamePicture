package com.java.game.server.gameServer.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Room {

    private String RoomNumber;
    private ArrayList<User> userList;

    //첫번째 user에게만 button을 true하게 제공하기
    private String firstUser;

    public Room(){
        userList = new ArrayList<User>();
    }

    public void add(User user){
        if(userList.size()==0){
            firstUser = user.getName();
            userList.add(user);
        }else{
            userList.add(user);
        }
    }

    public void remove(User user){
        userList.remove(user);
        if(userList.size()==0){

        }
    }



}
