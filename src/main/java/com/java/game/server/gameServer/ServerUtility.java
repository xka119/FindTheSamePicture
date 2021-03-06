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
import java.util.HashMap;
import java.util.Random;

public class ServerUtility {

    static PrintWriter pw;

    static Random r = new Random();

    static HashMap<Integer, Integer> maps = new HashMap<Integer, Integer>();



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
        pw.println("[" + name + "]님: "+ text);
        pw.flush();
    }

    //모두에게 메세지 보내기 - 자신일 경우 sendLog 실행
    public static synchronized void sendtoAll(String text, String name, User user) throws Exception {
        //유저 상태 확인
        int user_state = user.getState();
//        System.out.println("user_state: " + user_state);
        //유저 상태가 대기방이면
        if (user_state == Type.WAITING_ROOM) {
//            System.out.println("step1");
            for (int i = 0; i < GameServer.userList.size(); i++) {
                // 현재 자신인지 확인 그리고 대기실인지 확인 -> log
//                System.out.println("step2");

                if (GameServer.userList.get(i).getSocket().equals(user.getSocket())) {
                    //나에게 할때만 log찍기
//                    System.out.println("step3");

//                    String logText = text + " 현재 위치: " + GameServer.userList.get(i).getState();
//                    System.out.println(user.getName()+"의 로그: ");
                    sendLog(text, name);
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
        } else {
            //대기방이 아니라면 유저가 입장한 방 가져오기
//            System.out.println(user.getName() + "의 현재 위치: " + user_state);
            //-1해줘야지 실제 room index가 된다
            Room room = GameServer.roomList.get(user_state - 1);
            //룸의 유저사이즈
            for (int i = 0; i < room.getUserList().size(); i++) {

                //그 룸의 유저의 소켓과 같을 때는 로그만 찍기
                if (room.getUserList().get(i).getSocket().equals(user.getSocket())) {

//                    String logText = text + " 현재 위치: " + room.getUserList().get(i).getState();
                    sendLog(text, name);
                } else if (!room.getUserList().get(i).getSocket().equals(user.getSocket())
                        && room.getUserList().get(i).getState() == user_state) {

                    pw = new PrintWriter(room.getUserList().get(i).getSocket().getOutputStream());
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
        if (check) {
            pw.println("1");
        } else {
            pw.println("2");
            pw.flush();
            //여기로 들어온다면 룸의 첫번째 유저에게는 setting2에 대한 정보를 주자. 2로 들어오면은 시작을 enable하게 만들어주기
            pw = new PrintWriter(GameServer.roomList.get(user.getState()-1).getUserList().get(0).getSocket().getOutputStream());
            pw.println(Type.ENABLESTART);
            pw.println("["+user.getName()+"]님이 입장하셨습니다");
            pw.flush();
        }
        pw.flush();
    }

    public static void send_exit_room(User user) throws Exception {
        if(user.getState()==Type.WAITING_ROOM){
            return;
        }
        Room room = GameServer.roomList.get(user.getState() - 1);

        int size = room.getUserList().size();
        for (int i = 0; i < size; i++) {
//            System.out.println(room.getUserList().get(i).getName() + "입니다");
            if (room.getUserList().get(i).getSocket().equals(user.getSocket())) {
                //무시
            } else {
                //다른사람이면 셋팅을 바꿔줘야함
                pw = new PrintWriter(room.getUserList().get(i).getSocket().getOutputStream());
                pw.println(Type.EXIT);
                //무시
                String text = "[" + user.getName() + "]님이 퇴장하셨습니다";
                pw.println(text);
                pw.flush();
            }
        }

    }

    public static void send_game_start(User user) throws Exception {
        Room room = GameServer.roomList.get(user.getState() - 1);

        //게임 시작 로그 보내기
        String logText = "[" + room.getRoomNumber() + "번 방] 게임 시작하였습니다.";
        sendLog(logText);
        String setting = makeGame();
        for (int i = 0; i < room.getUserList().size(); i++) {
            //그 룸의 모든 유저에게 메시지 보내기
            pw = new PrintWriter(room.getUserList().get(i).getSocket().getOutputStream());
            pw.println(Type.GAMESTART);
            //랜덤으로 게임 숫자 생성해서 보내기
            pw.println(setting);

            //지울예정@@ 3:14
            pw.flush();
        }

    }

    public synchronized static void send_user_List(User user) throws Exception {
        for (int i = 0; i < GameServer.userList.size(); i++) {
            //나랑 소켓이 같으면 보내지말고
            if(GameServer.userList.get(i).getSocket().equals(user.getSocket())) {

            }else{
                //다르다면 메세지를 보낸다
                pw = new PrintWriter(GameServer.userList.get(i).getSocket().getOutputStream());
                pw.println(Type.ENTER);
                pw.println("["+user.getName()+"]님이 입장하셨습니다");
                pw.flush();
            }

        }
    }


    //check = true 1번째 , false 2번쨰 정답을 맞춰야함.
    public synchronized static void send_game_state(User user, String text, char imageNum, boolean check) throws Exception {
        //룸정보를 얻고
        Room room = GameServer.roomList.get(user.getState() - 1);
        room.addAnswer(text, imageNum);

        //게임 시작 로그 보내기
        String logText = "[" + room.getRoomNumber() + "번 방]" + user.getName() + "-> " + text + "선택하였습니다";
        sendLog(logText);
//        System.out.println("check: "+check);
        //
        //true 이면 1번쨰 보내는거
        if(check) {
//            System.out.println("check true로 들어옵니다");
            for (int i = 0; i < room.getUserList().size(); i++) {
                //그 룸의 나를 제외한 모든 유저에게 보내기
//                if (!room.getUserList().get(i).getSocket().equals(user.getSocket())) {
//                    System.out.println("상대방에게 보내는 부분입니다");
                    pw = new PrintWriter(room.getUserList().get(i).getSocket().getOutputStream());
                    pw.println(Type.GAME);
                    pw.println(text);
                    pw.flush();
//                }
            }
        }else{
//            System.out.println("check false로 들어옵니다");
            //Type.TURN 인 경우
            boolean correct = room.makeAnswer();
            if(correct){
                room.addCount();
                logText = "[" + room.getRoomNumber() + "번 방]" + user.getName() + "님이 정답을 선택했습니다";
            }else{
                logText = "[" + room.getRoomNumber() + "번 방]" + user.getName() + "님이 정답이 아닙니다";
            }
            sendLog(logText);
            for(int i=0; i< room.getUserList().size(); i++){
//                System.out.println("모든 유저에게 에코 중입니다.");
                pw = new PrintWriter(room.getUserList().get(i).getSocket().getOutputStream());
                pw.println(Type.TURN);
                pw.println(String.valueOf(correct));
                pw.println(text);
                pw.flush();
            }
            System.out.println(room.getCount());
            if(room.getCount()==8){
                for(int i=0; i<room.getUserList().size(); i++){
                    System.out.println("게임 끝");
                    pw = new PrintWriter(room.getUserList().get(i).getSocket().getOutputStream());
                    pw.println(Type.EXIT);
                    pw.println("게임 끝");
                    pw.flush();
                }
                room.setDefaultCount();
            }


        }
            //두번째니까 makeGame해야함
            //correct를 보냄. 모두에게
                //맞으면 모든 유저에게 에코
                //턴으로 넘기는데




            }


    //랜덤으로 게임 셋팅
    public synchronized static String makeGame() {

        HashMap<Integer, Integer> maps = new HashMap<Integer, Integer>();

        String s = "";
        int x;
        for (int i = 0; i < 16; i++) {
            //0~8
            x = r.nextInt(8);
            if (maps.containsKey(x)) {
                //포함되어있다면 value값을 조회 value가 1이면 2로 추가 , 2이면 i값 감소시키고 continue;
                if (maps.get(x) == 1) {
                    //1개 포함되어있다면
                    //2로 만들고
                    //스트링을 더해줌
                    maps.replace(x,2);
                    s += x;
                } else {
                    //2라면
                    i--;
                    continue;
                }
            } else {
                //포함되어있지않다면
                //한개 추가시킴
                maps.put(x, 1);
                //s에 더해줌
                s += String.valueOf(x);
            }
//            System.out.println("x의 값: " + x);
        }
        System.out.println("S의 값: " + s);
        return s;
    }

}

