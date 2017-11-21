package com.java.game.server.gameServer.manager;

import com.java.game.Server;
import com.java.game.common.Type;
import com.java.game.server.gameServer.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class RecvManager extends Thread {


    private User user;
    private BufferedReader br;
    private PrintWriter pw;
    private String text;
    private String flag;
    private String nick_name;

    public RecvManager(User user) throws Exception{
        this.user = user;
        this.nick_name = user.getName();
        br = new BufferedReader(new InputStreamReader(user.getSocket().getInputStream()));
        System.out.println("Server RecvManager start");
    }

    @Override
    public void run() {
        text = "";
        while(true){
            try{
                //텍스트를 읽어드림
                text = br.readLine();
                //서버에 찍고
//                System.out.println("["+nick_name+"]: "+text); // no timestamp
                //모두에게 뿌리고
                sendtoAll(text, nick_name);

            }catch(Exception e){
//                e.printStackTrace();
                GameServer.removeUser(this.user);
                text = user.getName()+"님이 종료하였습니다\n"+GameServer.userList.size()+ "명 접속중";
                try {
                    sendLog(text);
                } catch (Exception e1) {
//                    e1.printStackTrace();
                }
                System.out.println(text);
            }finally{
                if(user.getSocket().isConnected()){
                    try {
                        user.getSocket().close();
                        br.close();
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
    //로그 서버에 종료 로그 보내기
    public void sendLog(String text) throws Exception{
        pw = new PrintWriter(GameServer.logSocket.getOutputStream());
        pw.println(text);
        pw.flush();
    }

    //로그 서버에 로그 보내기
    public void sendLog(String text, String name) throws Exception{
        pw = new PrintWriter(GameServer.logSocket.getOutputStream());
        pw.print("["+name+"]"+" ");
        pw.println(text);
        pw.flush();
    }


    //모두에게 메세지 보내기 - 자신일 경우 sendLog 실행
    public void sendtoAll(String text, String name) throws Exception{
        for(int i = 0; i< GameServer.userList.size(); i++){
            // 현재 자신인지 확인 그리고 대기실인지 확인 -> log
            if(GameServer.userList.get(i).getSocket().equals(this.user.getSocket()) && this.user.getState()==Type.WAITING_ROOM){
                //나에게 할때만 log찍기

                sendLog(text, name);

                //다른 사람의 상태가 대기실 -> msg
            }else if(GameServer.userList.get(i).getState()==Type.WAITING_ROOM){
                //socket이 다른 사람들에게 모두 보내기
                pw = new PrintWriter(GameServer.userList.get(i).getSocket().getOutputStream());
                pw.println("["+name+"]: "+text);
                pw.flush();
            }


        }
    }



}