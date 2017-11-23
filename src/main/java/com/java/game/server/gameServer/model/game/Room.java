package com.java.game.server.gameServer.model.game;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Room {

    private int roomNumber;
    private ArrayList<User> userList;
    private boolean full;
    private boolean start;

    //첫번째 user에게만 button을 true하게 제공하기
    private User firstUser;

    public Room(int roomNumber){
        this.roomNumber = roomNumber;
        userList = new ArrayList<User>();
        this.start = false;
        this.full = false;
    }


    //userList가 0이면 생성하고 첫번쨰 user정하고 userList추가 그리고 첫번째 유저에게 첫번째 사람이라고 알려줌
    public void add(User user){
        if(userList.size()==0){
            firstUser = user;
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
