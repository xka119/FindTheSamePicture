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
        System.out.println("user_state: "+user_state);
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
                    pw = new PrintWriter(GameServer.userList.get(i).getSocket().getOutputStream());
                    pw.println(Type.CHAT);
//                    pw.println("[" + name + "]: " + text+"Echo 확인");
                    pw.println("[" + name + "]: " + text);
                    pw.flush();

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
            //-1해줘야지 실제 room index가 된다
            Room room = GameServer.roomList.get(user_state-1);
            //룸의 유저사이즈
            for(int i=0; i<room.getUserList().size(); i++){

                //그 룸의 유저의 소켓과 같을 때는 로그만 찍기
                if(room.getUserList().get(i).getSocket().equals(user.getSocket())){

                    String logText = text + " 현재 위치: "+ room.getUserList().get(i).getState();
                    sendLog(logText, name);
                }else if(!room.getUserList().get(i).getSocket().equals(user.getSocket())
                        && room.getUserList().get(i).getState() == user_state){

                    pw = new PrintWriter(room.getUserList().get(i).getSocket().getOutputStream());
                    //11.24 금요일 마지막작업 위치
                    //사람이 두명있을떄는 로그를 두번 찍네 왜그러지?
//                    pw.println(user_state);
                    //채팅방의 번호로 타입을 보내주어야함
                    pw.println(room.getUserList().get(i).getState());
                    pw.println("[" + name + "]: " + text);
                    pw.flush();
                }
            }
        }

    }


    // 스타트 버튼 주는 것
    public static void send_start_flag(User user, boolean check) throws Exception {
        pw = new PrintWriter(user.getSocket().getOutputStream());
        pw.println(Type.SETTING);

        //check true = 1 = enable, 2 = 시작대기중
        if(check){
            pw.println("1");
        }else{
            pw.println("2");
        }
        pw.flush();
    }

    public static void send_exit_room(User user) throws Exception{
        //user의 room정보를 가져와서
        //size ==1 이면 본인만 있다는 뜻이니까.본인에게 EXIT
        /*
        1. 일단 내가 나가면 그냥 나간거임
        2. 다른 친구에게는 내가 나갔으니까 Setting을 1하게 하면됨
         */
        Room room = GameServer.roomList.get(user.getState()-1);
        int size = room.getUserList().size();
        for(int i=0; i<size; i++){
            System.out.println(room.getUserList().get(i).getName()+"입니다");
            if(room.getUserList().get(i).getSocket().equals(user.getSocket())){
                //무시
            }else{
                //다른사람이면 셋팅을 바꿔줘야함
                pw = new PrintWriter(room.getUserList().get(i).getSocket().getOutputStream());
                pw.println(Type.EXIT);
                //무시
                pw.println(user.getName()+"님이 퇴장하셨습니다");
                pw.flush();
            }
        }

    }

    public static void send_game_start(User user) throws Exception{
        Room room = GameServer.roomList.get(user.getState()-1);

        //게임 시작 로그 보내기
        String logText = room.getRoomNumber() + "번 방 게임 시작하였습니다.";
        sendLog(logText);

        for(int i=0; i<room.getUserList().size(); i++){
            //그 룸의 모든 유저에게 메시지 보내기
            pw = new PrintWriter(room.getUserList().get(i).getSocket().getOutputStream());
            pw.println(Type.GAMESTART);
            //무시
            pw.println("Game Start");
            pw.flush();
        }

    }

    public synchronized static void send_user_List() throws Exception {
        for(int i=0; i<GameServer.userList.size(); i++){
            pw = new PrintWriter(GameServer.userList.get(i).getSocket().getOutputStream());
            pw.println(Type.REPAINT);
            pw.println(Type.USERLIST);
            //사이즈 만큼 보낸다.
            for(int j=0; j<GameServer.userList.size(); j++){
                System.out.print(GameServer.userList.get(j).getName()+" ");
                pw.println(GameServer.userList.get(j).getName()+" ");
            }
            System.out.println("");
            pw.flush();

        }
    }

    public synchronized static void send_game_state(User user, String text) throws Exception {
        Room room = GameServer.roomList.get(user.getState()-1);

        //게임 시작 로그 보내기
        String logText = "["+room.getRoomNumber() + "번 방]"+ user.getName()+"-> "+text+"선택하였습니다";
        sendLog(logText);

        for(int i=0; i<room.getUserList().size(); i++){
            //그 룸의 나를 제외한 모든 유저에게 보내기
            if(!room.getUserList().get(i).getSocket().equals(user.getSocket())) {
                pw = new PrintWriter(room.getUserList().get(i).getSocket().getOutputStream());
                pw.println(Type.GAME);
                pw.println(text);
                pw.flush();
            }
        }
    }

}
