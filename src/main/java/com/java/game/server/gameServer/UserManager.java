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
    }

    @Override
    public void run() {
        text = "";
        while(true){
            try{
                //텍스트를 읽어드림
                flag = Integer.parseInt(br.readLine());
                text = br.readLine();
                char imageNum;

                switch (flag){
                    //Type.CHAT
                    case 11:
                        //텍스트에 욕이 있으면 필터링을 하자.
//                        System.out.println(text+"<< 받았습니다");

                        //필터링 잠시 stop
                        text = ServerUtility.filterText(text);


                        //모두에게 뿌리고
                        ServerUtility.sendtoAll(text, nick_name, this.user);
//                        System.out.println("text 보냈습니다");
                        break;
                    //Type.GAME
                    case 12:
                        //이제 여기서
//                        System.out.println("TYPE.GAME");
//                        System.out.println("받은 flag: "+flag);
                        imageNum = br.readLine().charAt(0);
//                        System.out.println("눌른 이미지: "+text);
//                        System.out.println("실제 이미지: "+imageNum);
                        ServerUtility.send_game_state(user,text, imageNum, true);

                        break;

                    //Type.EXIT
                    case 13:
                        System.out.println("TYPE.EXIT");

                        //나가는 로그 전달
//                        text += "현재 위치: "+ user.getState();
                        ServerUtility.sendLog(text, nick_name);
                        System.out.println(text);
                        //친구들에 나갔음을 알려주기
                        ServerUtility.send_exit_room(user);

                        //룸에서 삭제
                        GameServer.roomList.get(user.getState()-1).remove(user);
//                        System.out.println("룸에서 삭제함");

                        //룸 점수 변경
                        GameServer.roomList.get(user.getState()-1).setDefaultCount();

                        //상태 변경
                        user.setState(Type.WAITING_ROOM);
//                        System.out.println("상태변경: 웨이팅룸");

                        break;

                    //TYpe.GAMESTART
                    case 14:
                        System.out.println("TYPE.GAMESTART");

                        ServerUtility.send_game_start(user);
                        break;

                    //Type.TURN 15
                    case 16:
//                        System.out.println("TYPE.TURN");

                        imageNum = br.readLine().charAt(0);
//                        System.out.println("눌른 이미지: "+text);
//                        System.out.println("실제 이미지: "+imageNum);
                        //user, btnNum, imageNum
                        ServerUtility.send_game_state(user, text, imageNum, false);
                        break;

                    //Type.Room 1~9
                    default:
                        //user의 state를 그 번호로 변경
                        //room을 들어가는 번호를 입력했음
                        //user 상태 - 방 번호에 따라서 세팅
                        if(user.getState()==Type.WAITING_ROOM) {
                            user.setState(flag);

                            //room 세팅 - 방 번호에 맞게 세팅 flag 1-9 match 0 -8
                            GameServer.roomList.get(flag - 1).add(user);
//                            System.out.println(user.getName()+"님이 "+ flag+"번 방에 입장하셨습니다");

                            //방번호에 추가를 하는데 그 룸에 사람이 없다면 그 user를 첫번째 사람으로 하고
//                            System.out.println("현재 ["+ flag +"]번 방인원: "+ GameServer.roomList.get(flag-1).getUserList().size());
                            if(GameServer.roomList.get(flag-1).getUserList().size()==1){
                                ServerUtility.send_start_flag(user,true);
                                //서버유틸에서 sendStartflag하자
                            }else{
                                //아니라면  flag를 false로 주자.
                                ServerUtility.send_start_flag(user, false);
//                                pw = new PrintWriter(GameServer.roomList.get(flag-1).getUserList().get(0).getSocket().getOutputStream());
//                                pw.println()
//                                pw.println(GameServer.roomList.get(flag-1).getUserList().get(0).getName()+"님이 입장하셨습니다");
//                                pw.flush();
                            }
                            //그 사람에게 start버튼을 줌

//                        System.out.println(GameServer.roomList.get(flag-1).getRoomNumber()+"방 번호 입니다");
//                            text += "님이 " + user.getState();

                            //로그서버에 입장 로그 전달
                            ServerUtility.sendLog(text, nick_name);
                        }else{
                            //현재 방에 들어와 있다면
                            ServerUtility.sendtoAll(text, nick_name, user);
                        }
                        break;
                }

                //서버에 찍고
//                System.out.println("["+nick_name+"]: "+text); // no timestamp

                //모두에게 뿌리고
//                sendtoAll(text, nick_name);

            }catch(Exception e){
                try {
                    ServerUtility.send_exit_room(user);
                    GameServer.removeUser(this.user);
                    if(!(user.getState()==Type.WAITING_ROOM)) {
                        GameServer.roomList.get(user.getState() - 1).remove(user);
                    }
                    text = user.getName()+"님이 종료하였습니다\n"+GameServer.userList.size()+ "명 접속중";
                    ServerUtility.sendLog(text);

                    //user가 나갔음을 알려주기
                    br.close();
                    user.getSocket().close();
                    break;
                } catch (Exception e1) {
                    e1.printStackTrace();
                    break;

                }
            }
        }

    }



}