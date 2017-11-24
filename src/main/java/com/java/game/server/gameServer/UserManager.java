package com.java.game.server.gameServer;

import com.java.game.common.Type;
import com.java.game.server.gameServer.model.game.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class UserManager extends Thread {


    private User user;
    private BufferedReader br;
    private PrintWriter pw;
    private String text;
    private int flag;
    private String nick_name;

    public UserManager(User user) throws Exception{
        this.user = user;
        this.nick_name = user.getName();
        br = new BufferedReader(new InputStreamReader(user.getSocket().getInputStream()));
        System.out.println("Server UserManager start");
    }

    @Override
    public void run() {
        text = "";
        while(true){
            try{
                //텍스트를 읽어드림
                flag = Integer.parseInt(br.readLine());
                text = br.readLine();

                switch (flag){
                    //Type.CHAT
                    case 11:
                        //텍스트에 욕이 있으면 필터링을 하자.
//                        System.out.println(text+"<< 받았습니다");

                        //필터링 잠시 stop
//                        text = ServerUtility.filterText(text);
                        //모두에게 뿌리고
                        ServerUtility.sendtoAll(text, nick_name, this.user);
//                        System.out.println("text 보냈습니다");
                        break;
                    //Type.GAME
                    case 12:
                        break;

                    //Type.EXIT
                    case 13:
                        //나가는 로그 전달
                        user.setState(Type.WAITING_ROOM);
                        ServerUtility.sendLog(text, user.getName());
                        break;
                    //Type.Room 1~9
                    default:
                        //로그서버에 입장 로그 전달
                        //user의 state를 그 번호로 변경
                        //room을 들어가는 번호를 입력했음
                        //user 상태 - 방 번호에 따라서 세팅
                        user.setState(flag);

                        //room 세팅 - 방 번호에 맞게 세팅 flag 1-9 match 0 -8
                        GameServer.roomList.get(flag-1).add(user);
//                        System.out.println(GameServer.roomList.get(flag-1).getRoomNumber()+"방 번호 입니다");
                        text += "현재 위치: "+ user.getState();

                        ServerUtility.sendLog(text, user.getName());
                        break;
                }

                //서버에 찍고
//                System.out.println("["+nick_name+"]: "+text); // no timestamp

                //모두에게 뿌리고
//                sendtoAll(text, nick_name);

            }catch(Exception e){

                try {
                    GameServer.removeUser(this.user);
                    text = user.getName()+"님이 종료하였습니다\n"+GameServer.userList.size()+ "명 접속중";
                    ServerUtility.sendLog(text);
                    br.close();
                    user.getSocket().close();
                    break;
                } catch (Exception e1) {

                }
            }
        }

    }
//    //로그 서버에 종료 로그 보내기
//    public void sendLog(String text) throws Exception{
//        pw = new PrintWriter(GameServer.logSocket.getOutputStream());
//        pw.println(text);
//        pw.flush();
//    }
//
//    //로그 서버에 로그 보내기
//    public void sendLog(String text, String name) throws Exception{
//        pw = new PrintWriter(GameServer.logSocket.getOutputStream());
//        pw.print("["+name+"]"+" ");
//        pw.println(text);
//        pw.flush();
//    }
//
//
//    //모두에게 메세지 보내기 - 자신일 경우 sendLog 실행
//    public void sendtoAll(String text, String name) throws Exception{
//        for(int i = 0; i< GameServer.userList.size(); i++){
//            // 현재 자신인지 확인 그리고 대기실인지 확인 -> log
//            if(GameServer.userList.get(i).getSocket().equals(this.user.getSocket()) && this.user.getState()==Type.WAITING_ROOM){
//                //나에게 할때만 log찍기
//
//                sendLog(text, name);
//
//                //다른 사람의 상태가 대기실 -> msg
//            }else if(GameServer.userList.get(i).getState()==Type.WAITING_ROOM){
//                //socket이 다른 사람들에게 모두 보내기
//                pw = new PrintWriter(GameServer.userList.get(i).getSocket().getOutputStream());
//                pw.println(Type.CHAT);
//                pw.println("["+name+"]: "+text);
//                pw.flush();
//            }
//
//
//        }
//    }



}