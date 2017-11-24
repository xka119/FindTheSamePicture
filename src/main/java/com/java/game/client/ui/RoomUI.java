package com.java.game.client.ui;

import com.java.game.Client;
import com.java.game.common.Type;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;

public class RoomUI extends JFrame implements UI, ActionListener, KeyListener, MouseListener{

    //
    private PrintWriter pw;

    //UI
    private WaitingUI waitingUI;

    private static final int SIZE = 16;
    private String room_Name;

    //panel
    private JPanel game_Panel;
    private JPanel game_info_Panel;
    private JPanel etc_Panel;
    private JPanel under_Panel;
    private JPanel chat_Panel;
    private JPanel button_Panel;

    //component
    private JButton[] gameImage; //이건 생각해봐야함. 가칭
    private ImageIcon[] gameImageIcon;
    private JTextArea game_info_TextArea;
    private JLabel game_info_Lable;

    private JScrollPane chat_ScrollPane;
    private JTextArea chat_TextArea;
    private JTextField chat_TextField;

    private JButton chat_send_Button;

    private JButton start_Button;
    private JButton exit_Button;



    public RoomUI(WaitingUI waitingUI) throws Exception{
        this.waitingUI = waitingUI;
        this.pw = new PrintWriter(waitingUI.getClient().getSocket().getOutputStream());

    }

    public RoomUI(String room_Name){
        this.room_Name = room_Name;
    }

    public RoomUI(){
        this.init();
    }


    public void init() {
        Container container = this.getContentPane();
        this.setTitle(room_Name);
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

//        Component Setting

        gameImageIcon = new ImageIcon[SIZE];
        for(int i=0; i<SIZE/2; i++){
            gameImageIcon[i] = new ImageIcon("./image/"+String.valueOf(i)+".jpg");
            gameImageIcon[8+i] = new ImageIcon("./image/"+String.valueOf(i)+".jpg");
        }

        gameImage = new JButton[SIZE];
        for(int i=0; i<SIZE; i++){
            gameImage[i] = new JButton(gameImageIcon[i]);
            gameImage[i].setVisible(false); //가리기
            gameImage[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                }
            });
        }

        game_info_TextArea = new JTextArea("Realtime Game Log");
        game_info_TextArea.setBackground(new Color(127,127,127));
        game_info_Lable = new JLabel("game Infomation");

        chat_TextArea = new JTextArea("환영합니다");
        chat_ScrollPane = new JScrollPane(chat_TextArea);
        chat_TextField = new JTextField("채팅을 입력하세요");
        chat_TextField.addMouseListener(this);
        chat_TextField.addKeyListener(this);
        chat_send_Button = new JButton("전송");
        chat_send_Button.addActionListener(this);

        start_Button = new JButton("시작");
        start_Button.addActionListener(this);

        exit_Button = new JButton("나가기");
        exit_Button.addActionListener(this);

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
        chat_Panel.add(chat_ScrollPane);
        chat_Panel.add(chat_TextField);
        button_Panel.add(chat_send_Button);
        button_Panel.add(start_Button);
        button_Panel.add(exit_Button);

        container.add(game_Panel);
        container.add(etc_Panel);

        //default Setting
        this.setTitle("같은그림찾기");
        this.setSize(1000,800);
        this.setVisible(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        //  this.pack();
    }

    public void init(String room_Name){
        Container container = this.getContentPane();
        this.setTitle(room_Name);
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
        chat_TextField.addKeyListener(this);
        chat_send_Button = new JButton("전송");
        chat_send_Button.addActionListener(this);

        start_Button = new JButton("시작");
        start_Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==start_Button){
                    //
                    System.out.println("Game Start");
                }
            }
        });

        exit_Button = new JButton("나가기");
        exit_Button.addActionListener(this);

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

    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o==chat_send_Button){
            if(!chat_TextField.getText().equals("")) {
                String text = chat_TextField.getText();
                //나한테 붙이기
                append("["+waitingUI.getClient().getName()+"] "+ text+"\n");
                System.out.println(text);

                try{
                    //Type.CHAT = 11
//                    System.out.println("Room번호 "+room_Name);
//                    pw.println(room_Name);
                    pw.println("11");
                    pw.println(text);
                    pw.flush();

                }catch (Exception e2){
                    e2.printStackTrace();
                }
                chat_TextField.setText("");
                System.out.println("채팅 입력 & 소켓 전송");
            }
        }else if(o==start_Button){
            //Socket 메시지
            System.out.println("Game Start");

        }else if(o==exit_Button){
            this.setVisible(false);
            waitingUI.setVisible(true);
            try{
                pw.println("13");
                pw.println(this.getTitle()+"번 방 퇴장");
                pw.flush();

                setDefault_Chat();

            }catch (Exception e1){
                e1.printStackTrace();
            }
//            System.out.println("나가기");
        }

    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(!chat_TextField.getText().equals("")) {
                String text = chat_TextField.getText();
                //나한테 붙이기
                append("["+waitingUI.getClient().getName()+"] "+ text+"\n");
                System.out.println(text);

                try{

                    //Type.CHAT = 11
                    pw.println("11");
                    pw.println(text);
                    pw.flush();

                }catch (Exception e2){
                    e2.printStackTrace();
                }

                chat_TextField.setText("");
                System.out.println("채팅 입력 & 소켓 전송");
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

    //body end
}
