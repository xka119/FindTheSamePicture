package com.java.game.client;

import com.java.game.client.model.Box;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ClientUtility {

    private static final int CHOICE = 2;

    static Box box = new Box();

    public static Box gameCheck(int i, String fileName) {
        if(box.size()==3 || box.size()==4){
            box = new Box();
        }

        box.addBtnNum(i);
        box.addImageNum(fileName.charAt(8));

        int size = box.size();

        /*
        1. 사이즈가 1인경우는 방금 추가한것
        2. 사이즈가 2인경우는 값을 비교해야함. 첫번째 이미지넘과 두번째 이미지넘을 비교
        3. 같은경우는 box size를 3으로 증가시켜서 리턴
        4. 다른경우는 box size를 4로 증가시켜서 리턴
        5. UI에서는 사이즈 1,2,3,4,에 대해서 설정하기
         */


        if(size==2) {
            //99는 단지 카운트
            if (box.getImageNum(0).equals(box.getImageNum(1))) {
                box.addBtnNum(99);
            } else {
                box.addBtnNum(99);
                box.addBtnNum(99);
            }
        }

        return box;
    }

    //body end
}
    //staic 으로 가지고 있자

    //게임 로직을 수행할 수 있도록

    /*
    1. Recv Game, 버튼 번호가 들어오면 여기에 변수를 세팅
    2. 변수가 size가 2개가 되면은. GameLogic 메소드 실행
    3. GameLogic 메소드는 그 변수 2개가 같은 이미지를 가리킨다면 Client에 다시 요청(같으면 지우고 다르면 X)
    4. ......

     */

    /*
    지금먼저해야될일
    1. 이미지를 랜덤으로 배치시키기. (완료)
    2. 버튼을 처음에 invisible 하게 만들어야함. 그런데도 선택이 될 수 있게 해야하는데.. (완료)
    3. 두번 선택하면 턴을 넘기게 만들어야함.
     */
