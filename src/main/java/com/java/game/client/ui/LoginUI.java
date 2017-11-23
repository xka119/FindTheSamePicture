package com.java.game.client.ui;

import com.java.game.client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;
import java.util.HashMap;

public class LoginUI extends JFrame implements UI, KeyListener, ActionListener{

    //Client
    private Client client;

    //UI
    private WaitingUI waitingUI;

    //panel
    private JPanel main_Panel;
    private JPanel connect_Pannel;

    //접속
    private JLabel title_Label;
    private JTextField name_TextField;
    private JButton start_Button;

    public LoginUI() throws Exception{
        this.init();
    }
//    public LoginUI(Client client){
//        this.setClient(client);
//        this.init();
//    }

    public void init(){
        Container container = this.getContentPane();
        //default Layout
        this.setLayout(new BorderLayout());

        //Panel Setting
        main_Panel = new JPanel();
        main_Panel.setLayout(new GridLayout(2,1));

        connect_Pannel = new JPanel();
        connect_Pannel.setLayout(new GridLayout(1,2));

        //Component Setting
        title_Label = new JLabel("같은그림찾기");
        title_Label.setVerticalAlignment(SwingConstants.CENTER);
        title_Label.setHorizontalAlignment(SwingConstants.CENTER);
        title_Label.setFont(new Font("궁서",15,15));


        name_TextField = new JTextField(9);
        name_TextField.setToolTipText("이름을 입력하세요(9자 이내)");
        name_TextField.addKeyListener(this);
//
//        name_TextField.addKeyListener(new KeyListener() {
//            public void keyTyped(KeyEvent e) {
//
//            }
//
//            public void keyPressed(KeyEvent e) {
//                if(e.getKeyCode()==KeyEvent.VK_ENTER){
//                    if(name_TextField.getText().equals("")){
//                        System.out.println("닉네임을 입력해주세요");
//                    }else{
//                        String name = name_TextField.getText();
//                        System.out.println("name: "+name);
//                        System.out.println("button Click");
//
//                        try {
//                            client = new Client(name);
//                            client.connect();
//
//                            client.threadStart();
//                        } catch (Exception e1) {
//                            e1.printStackTrace();
//                        }
//                        WaitingUI waitingUI = new WaitingUI(client);
//                    }
//                }
//            }
//
//            public void keyReleased(KeyEvent e) {
//
//            }
//        });

        start_Button = new JButton("접속");
        start_Button.addActionListener(this);

//        start_Button.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                if(e.getSource()==start_Button){
//                    if(name_TextField.getText().equals("")){
//                        System.out.println("닉네임을 입력해주세요");
//                    }else{
//                        String name = name_TextField.getText();
//                        System.out.println("name: "+name);
//                        System.out.println("button Click");
//
//                        try {
//                            client = new Client(name);
//                            client.connect();
////                            PrintWriter pw = new PrintWriter(client.getSocket().getOutputStream());
////                            pw.println(name);
////                            pw.flush();
//                            client.threadStart();
//                        } catch (Exception e1) {
//                            e1.printStackTrace();
//                        }
//
//                        System.out.println("Socket connection needed");
////                        LoginUI.super.dispose();
//                        WaitingUI waitingUI = new WaitingUI(client);
//                    }
//                }
//            }
//        });
        //Component add
        main_Panel.add(title_Label);
        main_Panel.add(connect_Pannel);

        connect_Pannel.add(name_TextField);
        connect_Pannel.add(start_Button);

        container.add(main_Panel,BorderLayout.CENTER);


        //default Setting
        this.setTitle("같은그림찾기");
        this.setSize(600,400);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        //  this.pack();


    }






    public static void main(String[] args) throws Exception{
        LoginUI loginUI = new LoginUI();
        //start

    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==start_Button){
            if(name_TextField.getText().equals("")){
                System.out.println("닉네임을 입력해주세요");
            }else{
                String name = name_TextField.getText();
                System.out.println("name: "+name);
                System.out.println("button Click");

                try {
                    client = new Client(name);
                    client.connect();
                    client.threadStart();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                System.out.println("Socket connection needed");
                this.setVisible(false);
                WaitingUI waitingUI = new WaitingUI(client);
            }
        }
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(name_TextField.getText().equals("")){
                System.out.println("닉네임을 입력해주세요");
            }else{
                String name = name_TextField.getText();
                System.out.println("name: "+name);
                System.out.println("button Click");

                try {
                    client = new Client(name);
                    client.connect();

                    client.threadStart();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                this.setVisible(false);
                WaitingUI waitingUI = new WaitingUI(client);
            }
        }

    }

    public void keyReleased(KeyEvent e) {

    }
    //body end
}
