package com.java.game.client;

import com.java.game.client.ui.LoginUI;
import com.java.game.client.ui.RoomUI;
import com.java.game.client.ui.WaitingUI;
import com.java.game.common.Type;
import lombok.Data;
import org.apache.ibatis.logging.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class RecvManager extends Thread {

    private Socket socket;
    private BufferedReader br;

    private String text;
    private int flag;
    private String name;
    private ArrayList<String> buttonlist;
    private int count;

    //ui
    private LoginUI loginUI;
    private RoomUI roomUI;
    private WaitingUI waitingUI;

    public RecvManager(Socket socket, WaitingUI waitingUI, RoomUI roomUI, String name) throws IOException {
        this.socket = socket;
        this.loginUI = loginUI;
        this.waitingUI = waitingUI;
        this.roomUI = roomUI;
        this.name = name;
        buttonlist = new ArrayList<>();
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        count = 0;
//        System.out.println("Client UserManager start");
    }


    @Override
    public void run() {
        /*
        1. 메세지와 게임정보를 받는다.
        2. Type = CHAT일 경우 그 다음 text를 br.readLine()하여 읽어서 출력한다. (이는 나중에 UI쪽으로 연결시켜야함)
        3. Type = GAME일 경우 GameLogic을 실행시킨다. //GameLogic클래스를 하나 만들어야할듯함
        4. 위를 반복한다.
         */
        text = "";
        try {
            while (true) {
                String x = br.readLine();
                //flag 체크
                System.out.println("서버로 부터 받은 flag"+x);
                flag = Integer.parseInt(x);
                text = br.readLine();

                switch (flag) {
                    //Type.CHAT - 대기방 채팅
                    case 11:
                        System.out.println(text);
                        waitingUI.append(text);
                        break;
                    //Type.GAME
                    case 12:
                        System.out.println("Type.GAME");
                        System.out.println("눌린 이미지: "+text);
                        //눌린 버튼의 화면을 repaint해주면됨
                        buttonlist.add(text);
                        roomUI.openImage(Integer.parseInt(text));
                        roomUI.repaint();

                        break;

                    //Type.EXIT
                    case 13:
                        System.out.println("Type.EXIT");

                        roomUI.setStart_Button(1);
//                        roomUI.addChat_TextArea(text);
//                        for(int i=0; i<16; i++){
//                            roomUI.defaultImage(i);
//                        }
                        roomUI.setGameImage(false);
                        roomUI.setExit_Button(false);

                        roomUI.append(text);
                        roomUI.repaint();
                        System.out.println("친구가 나가서 버튼 시작으로 바꾸는거야");
                        buttonlist = new ArrayList<>();
                        break;

                    //Type.GAMESTART
                    case 14:
                        System.out.println("Type.GAMESTART");


                        //버튼상태는 모두 만들고
                        roomUI.setStart_Button(3);

                        //게임 이미지 셋팅을 랜덤으로 해야함.
                        System.out.println("이미지번호:"+ text);
                        //
                        roomUI.setGameImageRandom(text);
                        //이미지셋팅
//                        roomUI.setGameImage(true);
                        roomUI.setExit_Button(true);
                        System.out.println("게임이 시작되었습니다.");
                        roomUI.repaint();
                        break;
                    //Type.TURN
                    case 16:
                        String btn = br.readLine();
                        System.out.println("Type.TURN");



                        buttonlist.add(btn);
                        roomUI.openImage(Integer.parseInt(btn));
                        roomUI.repaint();

                        Thread.sleep(1000);

                        /*
                        정답이라면 그 화면을 지워주면됨 아니라면 다시 원래대로
                         */
                        if(text.equals("true")){
                            for(int i=0; i<buttonlist.size(); i++){
                                roomUI.closeImage(Integer.parseInt(buttonlist.get(i)));
                            }
                        }else{
                            for(int i=0; i<buttonlist.size(); i++) {
                                roomUI.defaultImage(Integer.parseInt(buttonlist.get(i)));
                            }
                        }
                        buttonlist = new ArrayList<>();
                        break;

                        //Type.REPAINT
                    case 99:
                        //waiting에서 화면을 바꾸는건데. 버튼하고 목록부분만 리프레쉬시켜야함.
                        //버튼 먼저 내가들어갓으면
                        if(text.equals(Type.USERLIST)){
                            //일렬로 받는다
                            String userList = br.readLine();
                            String name[] = userList.split(" ");
                            waitingUI.add_User_List(name);
                            waitingUI.repaint_user_list();

                        }else{

                        }

//                        waitingUI.add_User_List(name);
                        break;

                    //Type.Setting
                    case 15:
                        System.out.println("Type.SETTING");
                        if(text.equals("1")) {
                            roomUI.setStart_Button(1);
                            roomUI.repaint();

                        }else{
                            roomUI.setStart_Button(2);
                            roomUI.repaint();
                        }
                        break;
                    //Type.ENABLESTART
                    case 17:
                        System.out.println("Type.ENABLESTART");
                        roomUI.enable_Start_button();
                        roomUI.append(text);
                        break;

                    //Type.ENTER
                    case 18:
                        System.out.println("Type.ENTER");
                        waitingUI.append(text);

                        break;
                    //Type.Room 1~9 //룸번호 채팅 진행
                    default:
                        //room번호에 맞는 채팅을 진행하게함.
                        //room번호를 받으면
                        roomUI.append(text);

                        break;

                }
//                if(text!=null) {
//                    System.out.println(text);
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //body end
}
