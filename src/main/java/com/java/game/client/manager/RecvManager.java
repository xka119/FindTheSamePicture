package com.java.game.client.manager;

import com.java.game.common.Type;
import lombok.Data;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

@Data
public class RecvManager extends Thread {

    private Socket socket;
    private DataInputStream dis;
    private String text;
    private String flag;

    public RecvManager(Socket socket) throws IOException {
        this.socket = socket;
        dis = new DataInputStream(socket.getInputStream());
        this.start();
        System.out.println("Client RecvManager start");
    }

    @Override
    public void run() {
        while(true){
            text = "";

            //요청이 들어오면 읽어준다.
            try{
                while((text=dis.readUTF())!=null ){
                    if(text.equals(Type.CHAT)){
                        this.setFlag(text);
                    }else {
                        this.setText(text);
                        System.out.println(text);
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }


        }
    }
}
