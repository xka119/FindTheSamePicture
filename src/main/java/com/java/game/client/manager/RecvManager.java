package com.java.game.client.manager;

import com.java.game.common.Type;
import lombok.Data;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

@Data
public class RecvManager extends Thread {

    private Socket socket;
    private BufferedReader br;

    private String text;
    private String flag;

    public RecvManager(Socket socket) throws IOException {
        this.socket = socket;
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.start();
        System.out.println("Client RecvManager start");
    }

    @Override
    public void run() {
        /*
        1. 메세지와 게임정보를 받는다.
        2. Type = CHAT일 경우 그 다음 text를 br.readLine()하여 읽어서 출력한다. (이는 나중에 UI쪽으로 연결시켜야함)
        3. Type = GAME일 경우 GameLogic을 실행시킨다. //GameLogic클래스를 하나 만들어야할듯함
        4. 위를 반복한다.
         */
        super.run();
        text = "";
        try {

            while (true) {
                flag = br.readLine();
                System.out.println(flag);
                if(flag==Type.CHAT) {
                    //type chat이면 한번 더 읽어서 채팅창에 읽어준다
                    text = br.readLine();
                    System.out.println(text);
                }else if(text==Type.GAME){
                    // game관련 로직으로 들어가야할듯.
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {

        }

                //if문 stop
//                if(text==null){
//                    break;
//                }else{
//                    System.out.println(text);
//                }
//           }
    }
    //body end
}
