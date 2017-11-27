package com.java.game.client.ui;

import com.java.game.client.Client;
import com.java.game.client.RecvManager;
import com.java.game.client.SendManager;
import com.java.game.common.Type;
import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@Data
public class WaitingUI extends JFrame implements UI, ActionListener, KeyListener, MouseListener{


    private Client client;
    private PrintWriter pw;
    private String name;

    //UI
    RoomUI roomUI;
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

    private JScrollPane chat_ScrollPane;
    private JTextArea chat_TextArea;

    private JTextField chat_TextField;
    private JButton chat_send_Button;

    public WaitingUI(){
        this.init();
    }

    public WaitingUI(Client client) throws Exception{
        this.client = client;
        this.name = client.getName();
        this.pw = new PrintWriter(client.getSocket().getOutputStream());
        this.init();
    }

    public void setRoomUI(RoomUI roomUI){
        this.roomUI = roomUI;
    }

    public void init(){
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
            room_ButtonList[i].addActionListener(this);
            //   roomUI_List[i] = new RoomUI(room_ButtonList[i].getName()); //created
//            room_ButtonList[i].addActionListener(new ActionListener() {
//                public void actionPerformed(ActionEvent e) {
//                    WaitingUI.super.dispose();
//                    RoomUI roomUI = new RoomUI();
//                    System.out.println("입장");
//
//                }
//            });
        }

        user_Label = new JLabel("접속자 목록");
        user_list_TextArea = new JTextArea();

        chat_TextArea = new JTextArea("환영합니다");
        chat_ScrollPane = new JScrollPane(chat_TextArea);
        chat_TextField = new JTextField("채팅을 입력하세요"); //setToolText 사용 or 없애기
        chat_TextField.addMouseListener(this);
        chat_TextField.addKeyListener(this);
        chat_send_Button = new JButton("전송");
        chat_send_Button.addActionListener(this);


        //Component add
        info_Panel1.add(room_Panel, BorderLayout.CENTER);
        info_Panel1.add(user_Panel, BorderLayout.EAST);
        for(int i=0; i<ROOM_SIZE; i++){
            room_Panel.add(room_ButtonList[i]);
        }

        user_Panel.add(user_Label);
        user_Panel.add(user_list_TextArea);

        info_Panel2.add(chat_ScrollPane, BorderLayout.CENTER);
        info_Panel2.add(chat_Panel, BorderLayout.SOUTH);

        chat_Panel.add(chat_TextField, BorderLayout.CENTER);
        chat_Panel.add(chat_send_Button, BorderLayout.EAST);


        container.add(info_Panel1);
        container.add(info_Panel2);

        //default Setting
        this.setTitle("같은그림찾기 사용자 : "+name);
        this.setSize(1000,800);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        //  this.pack();

        //Room setting
        try {
            roomUI = new RoomUI(this);
            roomUI.init();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        new WaitingUI();
        //start
    }

    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o==chat_send_Button){

            if(!chat_TextField.getText().equals("")) {
                String text = chat_TextField.getText();
//                append("["+client.getName()+"] "+chat_TextField.getText() + "\n");
                System.out.println(text);
                try{

                    //Type.CHAT = 11
                    pw.println("11");
                    pw.println(text);
                    pw.flush();

                }catch (Exception e2){
                    e2.printStackTrace();
                }

//                System.out.println("채팅 입력 & 소켓 전송");
                chat_TextField.setText("");
            }
        }else{
            //XX방 이름이 있겠지?
            String roomNumber;

            try {
                if (o == room_ButtonList[0]) {
//                    System.out.println(room_ButtonList[0].getText());
                    enterRoom(room_ButtonList[0].getText().substring(0, 1));
                } else if (o == room_ButtonList[1]) {
                     enterRoom(room_ButtonList[1].getText().substring(0, 1));
                } else if(o == room_ButtonList[2]){
                    enterRoom(room_ButtonList[2].getText().substring(0, 1));
                } else if(o == room_ButtonList[3]){
                    enterRoom(room_ButtonList[3].getText().substring(0, 1));
                }else if(o == room_ButtonList[4]){
                    enterRoom(room_ButtonList[4].getText().substring(0, 1));
                }else if(o == room_ButtonList[5]){
                    enterRoom(room_ButtonList[5].getText().substring(0, 1));
                }else if(o == room_ButtonList[6]){
                    enterRoom(room_ButtonList[6].getText().substring(0, 1));
                }else if(o == room_ButtonList[7]){
                    enterRoom(room_ButtonList[7].getText().substring(0, 1));
                }else if(o == room_ButtonList[8]){
                    enterRoom(room_ButtonList[8].getText().substring(0, 1));
                }else if(o == room_ButtonList[9]){
                    enterRoom(room_ButtonList[9].getText().substring(0, 1));
                }else{}

            }catch(Exception e1){
                e1.printStackTrace();
            }
        }
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(!chat_TextField.getText().equals("")) {
                String text = chat_TextField.getText();
                //나한테 붙이기
//                append("["+client.getName()+"] "+ text);
                System.out.println(text);
                try{

                    //Type.CHAT = 11
                    pw.println("11");
                    pw.println(text);
                    pw.flush();

                }catch (Exception e2){
                    e2.printStackTrace();
                }
//                System.out.println("채팅 입력 & 소켓 전송");
                chat_TextField.setText("");
            }
        }

    }

    public void keyReleased(KeyEvent e) {

    }

    public void mouseClicked(MouseEvent e) {
        chat_TextField.setText("");
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void append(String text){
        chat_TextArea.append("\n"+text);
    }

    public void setDefault_Chat(){
        chat_TextArea.setText("환영합니다");
    }

    public void enterRoom(String roomNumber){
        pw.println(roomNumber);
        pw.println(roomNumber+"번 방에 입장에 하셨습니다");
        pw.flush();

        this.setVisible(false);
        setDefault_Chat();
        roomUI.setTitle(roomNumber+"번 방 ["+ client.getName()+"]");
        roomUI.setVisible(true);
        //                        System.out.println("입장");

    }

    public void add_User_List(String[] names){
        for(String name : names)
        user_list_TextArea.append("\n"+name);
    }

    public void repaint_user_list(){
        user_list_TextArea.repaint();
    }

    //body end
}
