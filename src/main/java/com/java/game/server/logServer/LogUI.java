package com.java.game.server.logServer;

import javax.swing.*;
import java.awt.*;

public class LogUI extends JFrame {


    //나중에 다 만들면 추가시키기
    private JTextArea log_TextArea;
    private JScrollPane log_ScrollPane;

    public LogUI(){

    }
    public LogUI(LogServer logServer){
    }

    public void init() {
        Container container = this.getContentPane();

        //default Layout
        this.setLayout(new BorderLayout());
        //Panel Setting
        //Component Setting
        log_TextArea = new JTextArea("로그 서버...\n");
        log_ScrollPane = new JScrollPane(log_TextArea);

        //Component add
        container.add(log_ScrollPane);

        //default Setting
        this.setTitle("같은그림찾기");
        this.setSize(1000,800);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        //  this.pack();

    }

    public void addLog(String text){
        log_TextArea.append(text+"\n");
    }

}

