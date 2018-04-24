package com.chinesechess.util;

import com.chinesechess.pojo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * 用于记录当前存的房间信息
 */
public class RoomInform  {
    //表示房间号
    private Integer roomId = -1;
    //表示房间的状态:0：等待状态，1进行中
    private Integer roomState = -1;
    //表示当前房间中的用户=new ArrayList<>()
    private TreeMap<Integer,OnlineUser> onlineUserList = new TreeMap<Integer,OnlineUser>();
    //当前出现的光点号
    private Integer diannum = -1;
    //地图上光圈依附的棋子号
    private Integer guangnum = -1;
    //当前闪烁的棋子号
    private Integer chooseman = -1;
    //红方是否准备
    private boolean redisReady = false;
    //黑方是否准备
    private boolean blackisReady = false;
    //红方的username
    private String redName = null;
    //黑方的username
    private String blackName = null;
    //红方的局时
    private Integer redTime = 1200;
    //红方的步时
    private Integer redSteptime = 180;
    //黑方的局时
    private Integer blackTime = 1200;
    //黑方的步时
    private Integer blackSteptime = 180;
    //哪一方在下
    private boolean isRed = true;
    //当前所有棋子的信息
    private Chessman[] chessmans =null;
    //当前棋盘所有的信息
    private int[][] ans =null;
    //存储每一步的信息
    private List<StepInform> stepInformList=new ArrayList<>();
    //存储这一对局的信息
    private GameInform gameInform=new GameInform();



    //时间线程
    private TimeThread timeThread=null;


    public RoomInform(){

    }

   public RoomInform(boolean userisRed){
       this.chessmans=new Chessman[33];
       this.ans = new int[9][10];
        for(int i=0;i<9;i++){
            for(int j=0;j<10;j++){
                ans[i][j]=0;
            }
        }
        for(int i=1;i<=32;++i){//初始化当前的棋盘

            int num;
            int num1;
            int num2;
            int num3;
            String bgurl1;
            String bgurl2;
            String bgurl3;
            if(i%2==0){
                num=i/2;
                num1=3;
                num2=2;
                num3=0;
                bgurl1="../../../../static/img/blackchess/blackBing01.png";
                bgurl2="../../../../static/img/blackchess/blackPao0.png";
                bgurl3="../../../../static/img/blackchess/black"+((num<=12)?num:(24-num))+".png";

            }
            else {
                num=(i+1)/2;
                num1=6;
                num2=7;
                num3=9;
                bgurl1="../../../../static/img/redchess/redBing01.png";
                bgurl2="../../../../static/img/redchess/redPao0.png";
                bgurl3="../../../../static/img/redchess/red"+((num<=12)?num:(24-num))+".png";

            }
            int x;
            int y;
            if(num<=5){
                x=(num-1)*2;
                y=num1;
                if(!userisRed){
                    y=9-y;
                }
                chessmans[i]=new Chessman(x,y,bgurl1,i);

            }
            else if(num==6){
                x=1;
                y=num2;
                if(!userisRed){
                    y=9-y;
                }
                chessmans[i]=new Chessman(x,y,bgurl2,i);
            }
            else if(num==7){
                x=7;
                y=num2;
                if(!userisRed){
                    y=9-y;
                }
                chessmans[i]=new Chessman(x,y,bgurl2,i);
            }
            else {
                x=num-8;
                y=num3;
                if(!userisRed){
                    y=9-y;
                }
                chessmans[i]=new Chessman(x,y,bgurl3,i);
            }
            ans[x][y]=i;

        }
    }

    public TimeThread getTimeThread() {
        return timeThread;
    }

    public void setTimeThread(TimeThread timeThread) {
        this.timeThread = timeThread;
    }
    public GameInform getGameInform() { return gameInform; }

    public void setGameInform(GameInform gameInform) { this.gameInform = gameInform; }
    public List<StepInform> getStepInformList() { return stepInformList; }

    public void setStepInformList(List<StepInform> stepInformList) { this.stepInformList = stepInformList; }
    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getRoomState() {
        return roomState;
    }

    public void setRoomState(Integer roomState) {
        this.roomState = roomState;
    }

    public TreeMap<Integer, OnlineUser> getOnlineUserList() {
        return onlineUserList;
    }

    public void setOnlineUserList(TreeMap<Integer, OnlineUser> onlineUserList) {
        this.onlineUserList = onlineUserList;
    }

    public Integer getDiannum() {
        return diannum;
    }

    public void setDiannum(Integer diannum) {
        this.diannum = diannum;
    }

    public Integer getGuangnum() {
        return guangnum;
    }

    public void setGuangnum(Integer guangnum) {
        this.guangnum = guangnum;
    }

    public Integer getChooseman() {
        return chooseman;
    }

    public void setChooseman(Integer chooseman) {
        this.chooseman = chooseman;
    }

    public boolean isRedisReady() {
        return redisReady;
    }

    public void setRedisReady(boolean redisReady) {
        this.redisReady = redisReady;
    }

    public boolean isBlackisReady() {
        return blackisReady;
    }

    public void setBlackisReady(boolean blackisReady) {
        this.blackisReady = blackisReady;
    }

    public String getRedName() {
        return redName;
    }

    public void setRedName(String redName) {
        this.redName = redName;
    }

    public String getBlackName() {
        return blackName;
    }

    public void setBlackName(String blackName) {
        this.blackName = blackName;
    }

    public Integer getRedTime() {
        return redTime;
    }

    public void setRedTime(Integer redTime) {
        this.redTime = redTime;
    }

    public Integer getRedSteptime() {
        return redSteptime;
    }

    public void setRedSteptime(Integer redSteptime) {
        this.redSteptime = redSteptime;
    }

    public Integer getBlackTime() {
        return blackTime;
    }

    public void setBlackTime(Integer blackTime) {
        this.blackTime = blackTime;
    }

    public Integer getBlackSteptime() {
        return blackSteptime;
    }

    public void setBlackSteptime(Integer blackSteptime) {
        this.blackSteptime = blackSteptime;
    }

    public boolean isRed() {
        return isRed;
    }

    public void setRed(boolean red) {
        isRed = red;
    }

    public Chessman[] getChessmans() {
        return chessmans;
    }

    public void setChessmans(Chessman[] chessmans) {
        this.chessmans = chessmans;
    }

    public int[][] getAns() {
        return ans;
    }

    public void setAns(int[][] ans) {
        this.ans = ans;
    }


}