package com.java.game.server.logServer.manager;

import com.java.game.common.Type;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

@Data
@EqualsAndHashCode(callSuper = false)
public class ReadManager extends Thread {

    private Socket socket;

    private String names;
    private String flag;
    private String text;

    private DataInputStream dis;


    public ReadManager(Socket socket) throws IOException {
        this.socket = socket;
        dis = new DataInputStream(socket.getInputStream());
        this.start();
    }


   @Override
    public void run() {
        while(true){
            //flag가 connect라면
            try {
                this.setFlag(dis.readUTF());

                if(this.flag.equals(Type.CONNECT)){
                    this.setNames(dis.readUTF());
                    System.out.println(names);
                }
                if(this.flag.equals(Type.CHAT) || this.flag.equals(Type.GAME) || this.flag.equals(Type.LOG)){
                    //하나로 합쳐도 됨
                    this.setText(dis.readUTF());
                    System.out.println(names+": "+text);

                }else if(this.flag.equals(Type.EXIT)){
                    //종료시 닫아야되는디.
                   // socket.close();
                }else{}


            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                //다듬어야되는부분
                if(!socket.isConnected()){
                    try {
                        socket.close();
                        dis.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }



        }

    }

}
