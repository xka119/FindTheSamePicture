package com.java.game.client.ui;

import com.java.game.Client;
import com.java.game.client.ClientUtility;
import com.java.game.client.model.Box;
import com.java.game.common.Type;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
    private ImageIcon defaultImage = new ImageIcon("./image/default.jpg");
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
        gameImage = new JButton[SIZE];

        //default setting
        for(int i=0; i<SIZE; i++){
 //절대주소 이름임
            gameImageIcon[i] = new ImageIcon("./image/default.jpg");
//            gameImage[i] = new JButton("이미지");
            gameImage[i] = new JButton(gameImageIcon[i]);
            gameImage[i].setVisible(false);
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

        start_Button = new JButton("시작 대기중");
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

        start_Button = new JButton("시작 대기중");
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
        //그림에 관한부분
        for(int i=0; i<SIZE; i++) {
            if (o == gameImage[i]){
                //그게 이미지라면
                System.out.println(i+"번째 이미지클릭");
                gameImage[i].setIcon(gameImageIcon[i]);
//                System.out.println(gameImageIcon[i].toString());
//                gameImage[i].setVisible(true);

                Box box = ClientUtility.gameCheck(i, gameImageIcon[i].toString());
                int size = box.size();
                System.out.println(size);

                switch (size){
                    //한개만 클릭
                    case 1:
                        pw.println("12");
                        pw.println(String.valueOf(i));
                        pw.flush();
                        break;

                    //두개클릭인대 이경우는 없음 맞추면 3 틀리면 4
                    case 2:
                        break;

                    case 3:
                        //맞추면 안보이게 만들고 클릭 못하게함

                        gameImage[box.getButtonNum(0)].setVisible(false);
                        gameImage[box.getButtonNum(1)].setVisible(false);
                        System.out.println("맞췄습니다");
                        pw.println("12");
                        pw.println(String.valueOf(i));
                        pw.flush();
                        break;

                    case 4:
                        //틀리면 안보이게만 함
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
//                        gameImage[box.getButtonNum(0)].setIcon(defaultImage);
//                        gameImage[box.getButtonNum(1)].setIcon(defaultImage);
                        System.out.println("틀렸습니다");

                        pw.println("12");
                        pw.println(String.valueOf(i));
                        pw.flush();
                        break;
                    default:
                        break;
                }

                //요게 트루면 -- 맞추었다는 뜻

            }
        }

        if(o==chat_send_Button){
            if(!chat_TextField.getText().equals("")) {
                String text = chat_TextField.getText();
                //나한테 붙이기
                append("["+waitingUI.getClient().getName()+"] "+ text);
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
            pw.println("14");
            pw.println("게임시작");
            pw.flush();

        }else if(o==exit_Button){
            this.setVisible(false);
            waitingUI.setVisible(true);
            try{
                //Type.EXIT
                pw.println("13");
                pw.println(this.getTitle().substring(0,4)+"에서 퇴장하셨습니다");
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
                append("["+waitingUI.getClient().getName()+"] "+ text);
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

    public void setStart_Button(int start){
        if(start==1){
            start_Button.setText("시작");
            start_Button.setEnabled(true);
//            start_Button.repaint();
        }
        else if(start==2) {
            start_Button.setText("시작 대기중");
            start_Button.setEnabled(false);
//            start_Button.repaint();
        }
        else if(start==3){
            start_Button.setText("게임 진행 중");
            start_Button.setEnabled(false);
//            start_Button.repaint();

        }
    }

    public String getStart_Button(){
        return start_Button.getText();
    }

    //게임 이미지 세팅 - test - true/false만 바꿔주면됨
    public void setGameImage(boolean start){
            for(int i=0; i<gameImage.length; i++){
                if(start) {
                    gameImage[i].setVisible(start);
//                    gameImage[i].setEnabled(true);
                }
                else{
                    gameImage[i].setVisible(start);
//                    gameImage[i].setEnabled(false);
                }
            }

    }
    public void setGameImage_Enabled(boolean start){
        for(int i=0; i<gameImage.length; i++){
            if (start) {
                gameImage[i].setEnabled(start);
                gameImage[i].setVisible(start);
            }else{
                gameImage[i].setEnabled(start);
                gameImage[i].setVisible(start);
            }
        }
    }

    public void setExit_Button(boolean start){
        if(start)
            exit_Button.setEnabled(false);
        else
            exit_Button.setEnabled(true);
    }

    public void openImage(int i){

//        gameImage[i].setVisible(false);
        gameImage[i].setIcon(gameImageIcon[i]);
        //여기서 룸에서 해줫던거를 해줘야함.
        Box box = ClientUtility.gameCheck(i, gameImageIcon[i].toString());
        int size = box.size();
        System.out.println(size);

//        switch (size){
//            //한개만 클릭
//            case 1:
//                pw.println("12");
//                pw.println(String.valueOf(i));
//                pw.flush();
//                break;
//
//            //두개클릭인대 이경우는 없음 맞추면 3 틀리면 4
//            case 2:
//                break;
//
//            case 3:
//                //맞추면 안보이게 만들고 클릭 못하게함
//                gameImage[box.getButtonNum(0)].setVisible(false);
//                gameImage[box.getButtonNum(0)].setEnabled(false);
//                gameImage[box.getButtonNum(1)].setVisible(false);
//                gameImage[box.getButtonNum(1)].setEnabled(false);
//                System.out.println("맞췄습니다");
//                pw.println("12");
//                pw.println(String.valueOf(i));
//                pw.flush();
//                break;
//
//            case 4:
//                //틀리면 안보이게만 함
//                gameImage[box.getButtonNum(0)].setVisible(false);
//                gameImage[box.getButtonNum(1)].setVisible(false);
//                System.out.println("틀렸습니다");
//
//                pw.println("12");
//                pw.println(String.valueOf(i));
//                pw.flush();
//                break;
//            default:
//                break;
//        }


    }

    public void addChat_TextArea(String text){
        chat_TextArea.append("\n"+ text );
    }


    public void setGameImageRandom(String text) {
        /*
        이미지가 들어오면 이미지를 맞춰서 생성한다
         */
        for(int i=0; i<SIZE; i++){
//            System.out.println("이미지: "+text.charAt(i)+" 번째 이미지 세팅 성공");
            gameImageIcon[i] = new ImageIcon("./image/"+String.valueOf(text.charAt(i))+".jpg");
//            gameImageIcon[i].setImage(new ImageIcon("./image/"+String.valueOf(text.charAt(i))+".jpg").getImage());
//            System.out.println(gameImageIcon[i].toString());
            gameImage[i].setVisible(true);
            gameImage[i].addActionListener(this);
        }
//        System.out.println("repaint() start");
        repaint();
    }


    //body end
}
