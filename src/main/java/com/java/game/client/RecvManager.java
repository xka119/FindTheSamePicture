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

public class RecvManager extends Thread {

    private Socket socket;
    private BufferedReader br;

    private String text;
    private int flag;
    private String name;

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

        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
//                flag = Integer.parseInt(br.readLine());
                text = br.readLine();

                switch (flag) {
                    //Type.CHAT - 대기방 채팅
                    case 11:
                        System.out.println(text);
                        waitingUI.append(text);
                        break;
                    //Type.GAME
                    case 12:
                        break;

                    //Type.EXIT
                    case 13:
                        roomUI.setStart_Button(1);
                        roomUI.setGameImage(false);
                        roomUI.setExit_Button(false);
                        roomUI.repaint();
                        break;

                    //Type.GAMESTART
                    case 14:
                        roomUI.setStart_Button(3);
                        //이미지셋팅
                        roomUI.setGameImage(true);
                        roomUI.setExit_Button(true);
                        System.out.println("게임이 시작되었습니다.");
                        roomUI.repaint();

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
                        roomUI.setStart_Button(1);
                        roomUI.repaint();
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
