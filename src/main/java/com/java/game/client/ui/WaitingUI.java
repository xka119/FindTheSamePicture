package com.java.game.client.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class WaitingUI extends JFrame{

    private static final int ROOM_SIZE = 9;

    //panel
    private JPanel info_Panel1;
    private JPanel info_Panel2;
    private JPanel room_Panel;
    private JPanel user_Panel;
    private JPanel chat_Panel;

    //component
    private JButton[] room_ButtonList;

    private JLabel user_Label;
    private JTextArea user_list_TextArea;

    private JTextArea chat_TextArea;

    private JTextField chat_TextField;
    private JButton chat_send_Button;

    public WaitingUI(){
        Container container = this.getContentPane();

        //default Layout
        this.setLayout(new GridLayout(2,1));


        //Panel Setting
        info_Panel1 = new JPanel();
        info_Panel1.setLayout(new BorderLayout());
        room_Panel = new JPanel();
        room_Panel.setLayout(new GridLayout(3,3,10,10));
        user_Panel = new JPanel();

        info_Panel2 = new JPanel();
        info_Panel2.setLayout(new BorderLayout());
        chat_Panel = new JPanel();
        chat_Panel.setLayout(new BorderLayout());


        //Component Setting
        room_ButtonList = new JButton[ROOM_SIZE];
        for(int i=0; i<ROOM_SIZE; i++){
            room_ButtonList[i] = new JButton((i+1)+"번 방"); //뒤에 텍스트는 클릭시 listener 구현필요
        }

        user_Label = new JLabel("접속자 목록");
        user_list_TextArea = new JTextArea();

        chat_TextArea = new JTextArea();
        chat_TextField = new JTextField("채팅을 입력하세요"); //setToolText 사용 or 없애기
        chat_TextField.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {

            }

            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    System.out.println("채팅 입력 & 소켓 전송");
                }

            }

            public void keyReleased(KeyEvent e) {

            }
        });
        chat_send_Button = new JButton("전송");
        chat_send_Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==chat_send_Button){
                    System.out.println("채팅 입력 & 소켓 전송");
                }
            }
        });


        //Component add
        info_Panel1.add(room_Panel, BorderLayout.CENTER);
        info_Panel1.add(user_Panel, BorderLayout.EAST);
        for(int i=0; i<ROOM_SIZE; i++){
            room_Panel.add(room_ButtonList[i]);
        }

        user_Panel.add(user_Label);
        user_Panel.add(user_list_TextArea);

        info_Panel2.add(chat_TextArea, BorderLayout.CENTER);
        info_Panel2.add(chat_Panel, BorderLayout.SOUTH);

        chat_Panel.add(chat_TextField, BorderLayout.CENTER);
        chat_Panel.add(chat_send_Button, BorderLayout.EAST);


        container.add(info_Panel1);
        container.add(info_Panel2);

        //default Setting
        this.setTitle("같은그림찾기");
        this.setSize(1000,800);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        //  this.pack();


    }



    public static void main(String[] args){
        new WaitingUI();
        //start
    }
    //body end
}
