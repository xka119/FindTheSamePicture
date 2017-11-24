package com.java.game.server.gameServer;

import com.java.game.common.Type;
import com.java.game.server.gameServer.model.filter.Entity;
import com.java.game.server.gameServer.model.filter.LuisResponse;
import com.java.game.server.gameServer.model.game.Room;
import com.java.game.server.gameServer.model.game.User;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.io.PrintWriter;
import java.util.ArrayList;

public class ServerUtility {

    static PrintWriter pw;


    public ServerUtility() {
    }


    public static void test() {
        GameServer.roomList.size();
    }


    // 필터링 기능
    public static synchronized String filterText(String text) throws Exception {
        //text가 들어오면.. 그 텍스트중에서 욕이 있는지 확인
        //있으면 대체 시켜주기..
        String filter_text = text.trim();
        String url = "https://westus.api.cognitive.microsoft.com/luis/v2.0/apps/687ca8e8-168f-4524-9bca-fc52d2c664d8?subscription-key=ff5ff9266c634f14a868471b984e624a&verbose=true&timezoneOffset=0&q=";
        url += filter_text;
        ArrayList<String> value;
        ArrayList<Entity> entities;
        String type;
        //순차적으로 단어를 확인해야함.
        //일단 두글자만 해보자.
        RestTemplate restTemplate = new RestTemplate();
        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

//        ListenableFuture<ResponseEntity<LuisResponse>> luisResponsefuture = asyncRestTemplate.getForEntity(url, LuisResponse.class);
//        LuisResponse luisResponse = luisResponsefuture.get().getBody();

        LuisResponse luisResponse = restTemplate.getForObject(url, LuisResponse.class);

        entities = luisResponse.getEntities();

        //욕이 있다면 type(good word 얻어오고)
        try {
            type = luisResponse.getEntities().get(0).getType();

        } catch (Exception e) {
            return text;
//                type = "Null";
        }

        //욕 list
        value = new ArrayList<String>();
        String temp;
        for (Entity entity : entities) {
            temp = entity.getEntity().trim();
            if (temp.contains(" "))
                temp = temp.replaceAll(" ", "");

            value.add(temp);
        }
        for (int i = 0; i < entities.size(); i++) {
            value.add(entities.get(i).getResolution().getValues().get(0));
//                System.out.println(value.get(0));
        }
        //욕바꾸기
        for (String s : value) {
            text = text.replaceAll(s, type);
        }
        return text;

    }


    //로그 서버에 종료 로그 보내기
    public static synchronized void sendLog(String text) throws Exception {
        pw = new PrintWriter(GameServer.logSocket.getOutputStream());
        pw.println(text);
        pw.flush();
    }

    //로그 서버에 로그 보내기
    public static synchronized void sendLog(String text, String name) throws Exception {
        pw = new PrintWriter(GameServer.logSocket.getOutputStream());
        pw.print("[" + name + "]: ");
        pw.println(text);
        pw.flush();
    }

    //모두에게 메세지 보내기 - 자신일 경우 sendLog 실행
    public static synchronized void sendtoAll(String text, String name, User user) throws Exception {
        //유저 상태 확인
        int user_state = user.getState();
        //유저 상태가 대기방이면
        if (user_state == Type.WAITING_ROOM) {
//            System.out.println("step1");
            for (int i = 0; i < GameServer.userList.size(); i++) {
                // 현재 자신인지 확인 그리고 대기실인지 확인 -> log
//                System.out.println("step2");

                if (GameServer.userList.get(i).getSocket().equals(user.getSocket())) {
                    //나에게 할때만 log찍기
//                    System.out.println("step3");

                    String logText = text + " 현재 위치: "+ GameServer.userList.get(i).getState();
//                    System.out.println(user.getName()+"의 로그: ");
                    sendLog(logText, name);

                    //다른 사람의 상태가 대기실 -> msg
                } else if (!GameServer.userList.get(i).getSocket().equals(user.getSocket())
                            && GameServer.userList.get(i).getState() == Type.WAITING_ROOM) {
                    //socket이 다른 사람들에게 모두 보내기
                    pw = new PrintWriter(GameServer.userList.get(i).getSocket().getOutputStream());
                    pw.println(Type.CHAT);
                    pw.println("[" + name + "]: " + text);
                    pw.flush();
                }
            }
        }else{
            //대기방이 아니라면 유저가 입장한 방 가져오기
            System.out.println(user.getName()+"의 현재 위치: "+ user_state);
            Room room = GameServer.roomList.get(user_state-1);
            System.out.println("step1");
            for(int i=0; i<room.getUserList().size(); i++){

                //그 룸의 유저의 소켓과 같을떄는 로그만 찍기
                if(room.getUserList().get(i).getSocket().equals(user.getSocket())){
                    System.out.println("step2");

                    String logText = text + " 현재 위치: "+ room.getUserList().get(i).getState();
                    sendLog(logText, name);
                }else{
                    System.out.println("step3");

                    pw = new PrintWriter(room.getUserList().get(i).getSocket().getOutputStream());
                    //11.24 금요일 마지막작업 위치
                    //사람이 두명있을떄는 로그를 두번 찍네 왜그러지?
//                    pw.println(user_state);
                    pw.println(Type.CHAT);
                    pw.println("[" + name + "]: " + text);
                    pw.flush();
                }
            }
        }

    }
}