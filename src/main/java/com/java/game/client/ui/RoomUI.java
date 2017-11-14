package com.java.game.client.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RoomUI extends JFrame{

    private static final int SIZE = 16;

    //panel
    private JPanel game_Panel;
    private JPanel game_info_Panel;
    private JPanel etc_Panel;
    private JPanel under_Panel;
    private JPanel chat_Panel;
    private JPanel button_Panel;

    //component
    private JButton[] gameImage; //이건 생각해봐야함. 가칭
    private JTextArea game_info_TextArea;
    private JLabel game_info_Lable;
    private JTextArea chat_TextArea;
    private JTextField chat_TextField;
    private JButton chat_send_Button;
    private JButton start_Button;
    private JButton exit_Button;




    public RoomUI(){
        Container container = this.getContentPane();

        //default Layout
        this.setLayout(new GridLayout(2,1));

        //Panel Setting
        game_Panel = new JPanel();
        game_Panel.setLayout(new GridLayout(4,4,10,10));

        etc_Panel = new JPanel();
        etc_Panel.setLayout(new GridLayout(2,1,10,10));

        game_info_Panel = new JPanel();
        game_info_Panel.setLayout(new GridLayout(1,2,10,10));

        under_Panel = new JPanel();
        under_Panel.setLayout(new GridLayout(1,2,10,10));

        chat_Panel = new JPanel();
        chat_Panel.setLayout(new GridLayout(2,1,10,10));

        button_Panel = new JPanel();
        button_Panel.setLayout(new GridLayout(1,3));

        //Component Setting
        gameImage = new JButton[SIZE];
        for(int i=0; i<SIZE; i++){
            gameImage[i] = new JButton((i+1)+"번 이미지");
        }
        game_info_TextArea = new JTextArea("Realtime Game Log");
        game_info_TextArea.setBackground(new Color(127,127,127));
        game_info_Lable = new JLabel("game Infomation");

        chat_TextArea = new JTextArea("채팅창");
        chat_TextField = new JTextField("채팅을 입력하세요");
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

        start_Button = new JButton("시작");
        exit_Button = new JButton("나가기");

        //Component add
        for(int i=0; i<SIZE; i++){
            game_Panel.add(gameImage[i]);
        }
        game_info_Panel.add(game_info_TextArea);
        game_info_Panel.add(game_info_Lable);

        etc_Panel.add(game_info_Panel);
        etc_Panel.add(under_Panel);
        under_Panel.add(chat_Panel);
        under_Panel.add(button_Panel);
        chat_Panel.add(chat_TextArea);
        chat_Panel.add(chat_TextField);
        button_Panel.add(chat_send_Button);
        button_Panel.add(start_Button);
        button_Panel.add(exit_Button);

        container.add(game_Panel);
        container.add(etc_Panel);

        //default Setting
        this.setTitle("같은그림찾기");
        this.setSize(1000,800);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        //  this.pack();

    }


    public static void main(String[] args){
        new RoomUI();
        //start
    }
    //body end
}
