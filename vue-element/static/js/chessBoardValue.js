
import chessboard from '../../src/page/user/chessComponets/chessboard'
import { Message } from 'element-ui';

var mapinf=new Object();
mapinf.userisRed=true;//当前登录用户的红黑方
mapinf.redisReady=false;//红方是否已准备
mapinf.blackisReady=true;//黑方是否已准备

var chessmans = [];
var imgarrys=[];
var ans=new Array();
for(var i=0;i<9;i++){
  ans[i]=new Array(i);
  for(var j=0;j<10;j++){
    ans[i][j]=0;
  }
}

function Chessman (x,y,bgurl,i) {
  this.isflash = false;//当前棋子是否闪烁
  this.isexit=true;//当前棋子是否存在
  this.x=x;//当前棋子的逻辑坐标1-32
  this.y=y;//当前棋子的逻辑坐标1-32
  this.positionX = 4+x*66;//当前棋子的实际坐标
  this.positionY = 4+y*68;//当前棋子的实际坐标
  this.num=i;//当前的棋子号
  this.bgurl=bgurl;//当前棋子字的图片地址
  this.setXY=function (x,y){//更新棋子的逻辑坐标和实际坐标

    this.x=x;
    this.y=y;
    this.positionX = 4+x*66;
    this.positionY = 4+y*68;
  }
}


for(var i=1;i<=32;++i){//初始化当前的棋盘

  var num;
  var num1;
  var num2;
  var num3;
  var bgurl1;
  var bgurl2;
  var bgurl3;
  if(i%2===0){
    num=i/2;
    num1=3;
    num2=2;
    num3=0;
    bgurl1='../../../../static/img/blackchess/blackBing01.png';
    bgurl2='../../../../static/img/blackchess/blackPao0.png';
    bgurl3='../../../../static/img/blackchess/black'+((num<=12)?num:(24-num))+'.png';

  }
  else {
    num=(i+1)/2;
    num1=6;
    num2=7;
    num3=9;
    bgurl1='../../../../static/img/redchess/redBing01.png';
    bgurl2='../../../../static/img/redchess/redPao0.png';
    bgurl3='../../../../static/img/redchess/red'+((num<=12)?num:(24-num))+'.png';

  }
  let x;
  let y;
  if(num<=5){
    x=(num-1)*2;
    y=num1;
    if(!mapinf.userisRed){
      y=9-y;
    }
    chessmans[i]=new Chessman(x,y,bgurl1,i);

  }
  else if(num==6){
    x=1;
    y=num2;
    if(!mapinf.userisRed){
      y=9-y;
    }
    chessmans[i]=new Chessman(x,y,bgurl2,i);
  }
  else if(num==7){
    x=7;
    y=num2;
    if(!mapinf.userisRed){
      y=9-y;
    }
    chessmans[i]=new Chessman(x,y,bgurl2,i);
  }
  else {
    x=num-8;
    y=num3;
    if(!mapinf.userisRed){
      y=9-y;
    }
    chessmans[i]=new Chessman(x,y,bgurl3,i);
  }
  ans[x][y]=i;

}
// var json = JSON.stringify(chessmans);
//console.log(chessmans);




mapinf.chessmans=chessmans;
mapinf.diannum=-1;//当前出现的光点号
mapinf.chooseman=-1;//当前闪烁的棋子号
mapinf.isRed=true;//当前的下棋方（红色为true，黑色为false）
mapinf.guangnum=-1;//地图上光圈依附的棋子号
mapinf.ans=ans;//表示地图的每个点信息
mapinf.redName='沙皇';//红方的昵称
mapinf.redTime=1200;//红方剩余的时间
mapinf.blackName='法兰西';//黑方的昵称
mapinf.blackTime=1200;//黑方剩余时间



mapinf.isJiangjun=function(isRed){//isRed表示某一方，判断isRed是否将军对面
  for(var i=1;i<=16;++i){
    if(i==10||i==11||i==13||i==14) continue;
    let x,y;
    var num1;
    if(isRed){//如果是红方
      num1=i*2-1;
      x=this.chessmans[24].x;
      y=this.chessmans[24].y;

    }
    else{//如果是黑方
      num1=i*2;
      x=this.chessmans[23].x;
      y=this.chessmans[23].y;
    }
    if(this.chessmans[num1].isexit&&this.isOK(num1,x,y)){
      return true;
    }

  }
  return false;

}
mapinf.setXY=function (i,x,y) {//移动棋子
  if(i>0){
    if(!this.isOK(i,x,y)){//判断当前棋子移动是否合法
      return false;
    }

    let nowchessNum=ans[x][y];

    let beforex=this.chessmans[i].x;
    let beforey=this.chessmans[i].y;
    this.chessmans[i].setXY(x,y);//设置当前棋子的位置
    this.ans[beforex][beforey]=0;//修改map的信息
    this.ans[x][y]=i;
    if(nowchessNum>0){
      this.chessmans[nowchessNum].isexit=false;
    }

    if(this.isJiangjun(!this.isRed)){//判断如果走这一步是否会被对面将军
      this.chessmans[i].setXY(beforex,beforey);//设置当前棋子的位置
      this.ans[x][y]=nowchessNum;//修改map的信息
      this.ans[beforex][beforey]=i;
      if(nowchessNum>0){
        this.chessmans[nowchessNum].isexit=true;
      }

      Message( {
        message:'这样走可能会被将军哦！',
        type: 'warning',
        duration:1000
      });
      return false;
    }


    this.diannum=beforey*9+beforex+1;
    this.chooseman=-1;//设置当前没有被选中任何棋子
    this.guangnum=i;//设置当前光圈所在的棋子位置
    this.isRed=!this.isRed;//转换下棋方

    let audioGo = document.getElementById('audioGo');//播放棋子走的声音
    audioGo.play();

    if(this.isJiangjun(!this.isRed)){//走完这一步后，是否会造成将军的局面

      let audioJiangjun = document.getElementById('audioJiangjun');//播放棋子走的声音
      audioJiangjun.play();
      let countNum;
      if(this.isRed){
        if(this.chessmans[23].y>4){
          countNum=3;
        }
        else {
          countNum=1;
        }
      }
      else{
        if(this.chessmans[24].y>4){
          countNum=3;
        }
        else {
          countNum=1;
        }
      }

      imgarrys.push({imziNum:countNum});

    }
    else if(nowchessNum>0){

      let audioEat = document.getElementById('audioEat');
      audioEat.play();
      imgarrys.push({
        imziNum:2
      });
    }


  }

}
mapinf.isOK=function(i,x,y){//判断当前棋子的行为是否合法
  let num=i;
  let nowX=this.chessmans[i].x;
  let nowY=this.chessmans[i].y;
  let belongRed;
  if(num%2==0){
    num/=2;
    belongRed=false;
  }
  else{
    num++;
    num/=2;
    belongRed=true;
  }
  num=parseInt(num);
  let shuaiChess;
  if(belongRed){
    shuaiChess=this.chessmans[23];
  }
  else{
    shuaiChess=this.chessmans[24];
  }
  let OK=true;

  if(num<=5){
    OK=this.bing(nowX,nowY,x,y,shuaiChess);
  }
  else if(num==6||num==7){
    OK=this.pao(nowX,nowY,x,y,shuaiChess);
  }
  else if(num==8||num==16){
    OK=this.ju(nowX,nowY,x,y,shuaiChess);
  }
  else if(num==9||num==15){
    OK=this.ma(nowX,nowY,x,y,shuaiChess);
  }
  else if(num==10||num==14){
    OK=this.xiang(nowX,nowY,x,y,shuaiChess);
  }
  else if(num==11||num==13){
    OK=this.shi(nowX,nowY,x,y,shuaiChess);
  }
  else if(num==12){
    OK=this.shuai(nowX,nowY,x,y,shuaiChess);
  }
  return OK;
}

mapinf.bing=function(nowX,nowY,x,y,shuaiChess) {//兵的规则

  let shuaiX=shuaiChess.x;
  let shuaiY=shuaiChess.y;
  if(shuaiY<4){
    if(nowY<5&&x==nowX&&y-nowY==1){
      return  true;
    }
    else if(nowY>=5&&((nowY-y==0&&Math.abs(x-nowX)==1)||(nowX-x==0&&y-nowY==1))){
      return true;
    }
    console.log(Math.abs(x-nowX));
  }
  else{
    if(nowY>=5&&x==nowX&&y-nowY==-1){
      return  true;
    }
    else if(nowY<5&&((nowY-y==0&&Math.abs(x-nowX)==1)||(nowX-x==0&&y-nowY==-1))){
      return true;
    }
  }
  return false;

}

mapinf.pao=function (nowX,nowY,x,y,shuaiChess) {//炮的规则
      let count=0;
      if(nowX!=x&&nowY!=y){
        return false;
      }
      if(nowX==x&&nowY!=y){
        let ey=Math.max(nowY,y);
        let sy=Math.min(nowY,y);
        for(var i=sy+1;i<ey;++i){
          if(ans[x][i]!=0){
            count++;
          }
        }
      }
      else if(nowX!=x&&nowY==y){
        let ex=Math.max(nowX,x);
        let sx=Math.min(nowX,x);
        for(var i=sx+1;i<ex;++i){
          if(ans[i][y]!=0){
            count++;
          }
        }
      }
      if(count==0&&ans[x][y]==0){
        return true;
      }
      else if(count==1&&ans[x][y]>0&&(ans[x][y]+ans[nowX][nowY])%2!=0){
        return true;
      }
      return false;
}

mapinf.ju=function (nowX,nowY,x,y,shuaiChess) {//车的规则

  if(nowX!=x&&nowY!=y){
    return false;
  }
  if(nowX==x&&nowY!=y){
    let ey=Math.max(nowY,y);
    let sy=Math.min(nowY,y);
    for(var i=sy+1;i<ey;++i){
      if(ans[x][i]!=0){

        return false;
      }
    }
  }
  else if(nowX!=x&&nowY==y){
    let ex=Math.max(nowX,x);
    let sx=Math.min(nowX,x);
    for(var i=sx+1;i<ex;++i){
      if(ans[i][y]!=0){
        return false;
      }
    }
  }
  if(ans[x][y]==0){
    return true;
  }
  else if(ans[x][y]>0&&(ans[x][y]+ans[nowX][nowY])%2!=0){
    return true;
  }
  return false;

}

mapinf.ma=function (nowX,nowY,x,y,shuaiChess) {//马的规则
      if((Math.abs(nowX-x)==1&&Math.abs(nowY-y)==2)){

        let sy=Math.min(nowY,y)+1;
        if(ans[nowX][sy]>0){
          return false;
        }
      }
      else if(Math.abs(nowX-x)==2&&Math.abs(nowY-y)==1){
        let sx=Math.min(nowX,x)+1;
        if(ans[sx][nowY]>0){
          return false;
        }
      }
      else {
        return false;
      }
      if(ans[x][y]==0){
        return true;
      }
      else if(ans[x][y]>0&&(ans[x][y]+ans[nowX][nowY])%2!=0){
        return true;
      }
      return false;
}

mapinf.xiang=function (nowX,nowY,x,y,shuaiChess) {//相的规则
  let shuaiY=shuaiChess.y;
  if(Math.abs(nowX-x)==2&&Math.abs(nowY-y)==2){
    if((shuaiY<=4&&y<=4)||(shuaiY>4&&y>4)){
      let cx=(nowX+x)/2;
      let cy=(nowY+y)/2;
      cx=parseInt(cx);
      cy=parseInt(cy);
      if(ans[cx][cy]>0) return false;
      if(ans[x][y]==0){
        return true;
      }
      else if(ans[x][y]>0&&(ans[x][y]+ans[nowX][nowY])%2!=0){
        return true;
      }
    }

  }


  return false;

}

mapinf.shi=function (nowX,nowY,x,y,shuaiChess) {//士的规则
  let shuaiY=shuaiChess.y;
  if(Math.abs(nowX-x)==1&&Math.abs(nowY-y)==1&&x>=3&&x<=5){
    if((shuaiY<=4&&y<=2&&y>=0)||(shuaiY>4&&y<=9&&y>=7)){
      if(ans[x][y]==0){
        return true;
      }
      else if(ans[x][y]>0&&(ans[x][y]+ans[nowX][nowY])%2!=0){
        return true;
      }
    }
  }
  return false;
}

mapinf.shuai=function (nowX,nowY,x,y,shuaiChess) {//帅的规则
  if(x>=3&&x<=5){
    if((Math.abs(nowX-x)==1&&Math.abs(nowY-y)==0)||(Math.abs(nowX-x)==0&&Math.abs(nowY-y)==1)){
      if((nowY<=4&&y<=2&&y>=0)||(nowY>4&&y<=9&&y>=7)){
        if(ans[x][y]==0){
          return true;
        }
        else if(ans[x][y]>0&&(ans[x][y]+ans[nowX][nowY])%2!=0){
          return true;
        }
      }
    }
    if( nowX==x&&
        (
          (nowY<=4&&x==this.chessmans[23].x&&y==this.chessmans[23].y)||
          (nowY>4&&x==this.chessmans[24].x&&y==this.chessmans[24].y)
        )
    ){
      var count=0;
      for(var i=this.chessmans[24].y+1;i<this.chessmans[23].y;++i){
        if(ans[x][i]>0){
          count++;
          return false;
        }
      }
      if(count==0) return true;
    }

  }
  return false;
}
export default {
  mapinf,imgarrys
}

