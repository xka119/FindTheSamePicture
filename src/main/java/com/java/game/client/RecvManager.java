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

    //ui
    private LoginUI loginUI;
    private RoomUI roomUI;
    private WaitingUI waitingUI;

    public RecvManager(Socket socket, LoginUI loginUI,  WaitingUI waitingUI, RoomUI roomUI) throws IOException {
        this.socket = socket;
        this.loginUI = loginUI;
        this.waitingUI = waitingUI;
        this.roomUI = roomUI;

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
                System.out.println(x);
                flag = Integer.parseInt(x);
//                flag = Integer.parseInt(br.readLine());
                text = br.readLine();

                switch (flag) {
                    //Type.CHAT
                    case 11:
                        System.out.println(text);
                        waitingUI.append(text);
                        break;
                    //Type.GAME
                    case 12:
                        break;

                    //Type.EXIT
                    case 13:
                        break;

                    //Type.REFRESH
                    case 99:
                        break;

                    //Type.Room 1~9
                    default:
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
