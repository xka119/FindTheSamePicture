package com.java.game.client.model;

import java.util.ArrayList;

public class Box {


    ArrayList<Integer> button = new ArrayList<>();
    ArrayList<Character> image = new ArrayList<>();


    public void addBtnNum(int btn){
        button.add(btn);
    }
    public void addImageNum(char c){
        image.add(c);
    }

    public int getButtonNum(int i){
        return button.get(i);
    }

    public Character getImageNum(int i){
        return image.get(i);
    }

    public int size(){
        return button.size();
    }


}
