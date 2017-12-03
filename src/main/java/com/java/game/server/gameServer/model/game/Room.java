package com.java.game.server.gameServer.model.game;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Room {

    private int roomNumber;
    private ArrayList<User> userList;
    private boolean full;
    private boolean start;
    private int count;
    private ArrayList<String> buttonlist;
    private ArrayList<Character> answerlist;

    //첫번째 user에게만 button을 true하게 제공하기
    private User firstUser;

    public Room(int roomNumber){
        this.roomNumber = roomNumber;
        userList = new ArrayList<User>();
        buttonlist = new ArrayList<>();
        answerlist = new ArrayList<>();
        count = 0;
        this.full = false;
    }

    public void addCount(){
        count++;
    }

    public int getCount(){
        return count;
    }
    public void setDefaultCount(){
        count = 0;
    }

    public boolean makeAnswer(){
        boolean check = false;

        if(answerlist.size()==2){
            //사이즈가 2라면 인덱스 0, 1을 비교
            if(answerlist.get(0).equals(answerlist.get(1))){
                //같다면
                check = true;
            }else{
                check = false;
            }
        }
        //반드시 list 초기화 시켜야함
        buttonlist = new ArrayList<>();
        answerlist = new ArrayList<>();
        //answer의 사이즈가 2라면 확인
        // 정답이라면 true
        // 틀렸다면 false;

        return check;
    }




    public void add(String button, char answer){
        buttonlist.add(button);
        answerlist.add(answer);
    }

    public void addAnswer(String button, char answer){
        add(button, answer);
    }


    //userList가 0이면 생성하고 첫번쨰 user정하고 userList추가 그리고 첫번째 유저에게 첫번째 사람이라고 알려줌
    public synchronized void add(User user){
        if(userList.size()==0){
            firstUser = user;
            userList.add(user);
        }else{
            userList.add(user);
        }

        if(userList.size()==2){

        }
    }

    public synchronized void remove(User user){
        userList.remove(user);
        if(userList.size()==0){

        }
    }



}
