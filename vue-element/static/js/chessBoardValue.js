
import chessboard from '../../src/page/user/chessComponets/chessboard'
import $axios from'axios';
import { Message } from 'element-ui';

//存储用户信息
var userInform=new Object();
userInform.id;
userInform.name;
userInform.password;
userInform.emailAddress;

//当前在线用户的信息
var onlineUser=new Object();
//存储用户信息
onlineUser.userInform=userInform;
//用当前的默认状态在大厅
onlineUser.userState=4;
//用户的默认房间是大厅
onlineUser.roomId=-1;

var messageInform1=new Object();
messageInform1.data='';
messageInform1.userId=-1;
messageInform1.userName='';
messageInform1.roomId=-1;
messageInform1.time=-1;

var mapinf=new Object();
mapinf.userisRed=true;//当前登录用户的红黑方
mapinf.redisReady=false;//红方是否已准备
mapinf.blackisReady=false;//黑方是否已准备
mapinf.redSteptime=180;//红方的步时
mapinf.blackSteptime=180;//黑方的步时

var chessmans = [];
var imgarrys=[];
var ans=new Array();


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

mapinf.initInform=function(){
  for(var i=0;i<9;i++){
    ans[i]=new Array(i);
    for(var j=0;j<10;j++){
      ans[i][j]=0;
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
  mapinf.roomId;
  mapinf.chessmans=chessmans;
  mapinf.diannum=-1;//当前出现的光点号
  mapinf.chooseman=-1;//当前闪烁的棋子号
  mapinf.isRed=true;//当前的下棋方（红色为true，黑色为false）
  mapinf.guangnum=-1;//地图上光圈依附的棋子号
  mapinf.ans=ans;//表示地图的每个点信息
  mapinf.redName;//红方的昵称
  mapinf.redTime=1200;//红方剩余的时间
  mapinf.blackName;//黑方的昵称
  mapinf.blackTime;//黑方剩余时间
  mapinf.roomState=0;
}
mapinf.initInform();

mapinf.initGame=function(){

  for(var i=0;i<9;i++){
    mapinf.ans[i]=new Array(i);
    for(var j=0;j<10;j++){
      mapinf.ans[i][j]=0;
    }
  }
  for(var i=1;i<=32;++i){//初始化当前的棋盘

    var num;
    var num1;
    var num2;
    var num3;
    mapinf.chessmans[i].isexit=true;
    if(i%2===0){
      num=i/2;
      num1=3;
      num2=2;
      num3=0;
    }
    else {
      num=(i+1)/2;
      num1=6;
      num2=7;
      num3=9;


    }
    let x;
    let y;
    if(num<=5){
      x=(num-1)*2;
      y=num1;
      if(!mapinf.userisRed){
        y=9-y;
      }
      mapinf.chessmans[i].setXY(x,y);

    }
    else if(num==6){
      x=1;
      y=num2;
      if(!mapinf.userisRed){
        y=9-y;
      }
      mapinf.chessmans[i].setXY(x,y);
    }
    else if(num==7){
      x=7;
      y=num2;
      if(!mapinf.userisRed){
        y=9-y;
      }
      mapinf.chessmans[i].setXY(x,y);
    }
    else {
      x=num-8;
      y=num3;
      if(!mapinf.userisRed){
        y=9-y;
      }
      mapinf.chessmans[i].setXY(x,y);
    }
    mapinf.ans[x][y]=i;

  }
  mapinf.diannum=-1;//当前出现的光点号
  mapinf.chooseman=-1;//当前闪烁的棋子号
  mapinf.isRed=true;//当前的下棋方（红色为true，黑色为false）
  mapinf.guangnum=-1;//地图上光圈依附的棋子号
}

// var json = JSON.stringify(chessmans);
//console.log(chessmans);






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

    //如果这步棋是我走的，则判断我是否将军
    let ch=false;
    if((this.userisRed&&i%2!=0)||(!this.userisRed&&i%2==0)){
      ch=this.isJiangjun(!this.userisRed)
    }


    if(ch){//判断如果走这一步是否会被对面将军
      this.chessmans[i].setXY(beforex,beforey);//设置当前棋子的位置
      this.ans[x][y]=nowchessNum;//还原map的信息
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
    //this.isRed=!this.isRed;//转换下棋方

    let audioGo = document.getElementById('audioGo');//播放棋子走的声音
    audioGo.play();
    //如果这步棋是我走的，则判断我是否将军
    let fn=false;
    if((i%2==0&&!this.userisRed)||(i%2!=0&&this.userisRed)){
      fn=this.isJiangjun(this.userisRed)
    }
    else{
      fn=this.isJiangjun(!this.userisRed)
    }
    if(fn){//走完这一步后，是否会造成将军的局面

      let audioJiangjun = document.getElementById('audioJiangjun');//播放棋子走的声音
      audioJiangjun.play();
      let countNum;
      if(i%2==0){
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
  var  stepInform=new Object();
  stepInform.i=i;
  if(!mapinf.userisRed){
    stepInform.x=x;
    stepInform.y=9-y;
  }
  else {
    stepInform.x=x;
    stepInform.y=y;
  }

  stepInform.roomInform=mapinf;
  if(onlineUser.userState!=5&&onlineUser.userState!=6){

  }
  else if((mapinf.userisRed&&i%2!=0)||(!mapinf.userisRed&&i%2==0)){

    var jsonData = JSON.stringify(stepInform);
    $axios({
      method: 'post',
      headers: {
        'Content-Type': 'application/json;charset=UTF-8',
      },
      withCredentials : true,
      url: '/ssm/user/submitStepinform',//listUserInform
      data: jsonData,
    }).then(function (response) {
      if(!mapinf.isJueSha(!mapinf.userisRed)){
        let fnum;
        if(mapinf.userisRed){
          fnum=mapinf.roomId+'0';
        }
        else {
          fnum=mapinf.roomId+'1';
        }

          var jsonData = JSON.stringify(fnum);
          $axios({
            method: 'post',
            headers: {
              'Content-Type': 'application/json;charset=UTF-8',
            },
            withCredentials : true,
            url: '/ssm/user/gameResult',//listUserInform
            data: jsonData,
          }).then(function (response) {

          })
            .catch(function (error) {

            });
        }


    })
      .catch(function (error) {
        console.log(error);
      });
  }
}


}

mapinf.isJueSha=function(isRed){//判断isREd是否有棋可走

  //当isRed走完这步以后，是否会被isRed的对面一方将军，
  //如果会被对面将军，则还原这步，再去模拟下一步，
  //如果不好被对面将军，代表还有棋可走，则没有绝杀
  for(let i=1;i<=16;++i){
    let num;
    if(isRed){//奇数棋
      num=i*2-1;

    }
    else{//偶数棋
      num=i*2;
    }
    if(!chessmans[num].isexit) continue;
    let arry=mapinf.canGo(num);
    for(var key=0;key<arry.length;++key){

      let x=arry[key].x
      let y=arry[key].y
      let nowchessNum=mapinf.ans[x][y];
      let beforex=mapinf.chessmans[num].x;
      let beforey=mapinf.chessmans[num].y;
      mapinf.chessmans[num].setXY(x,y);//设置当前棋子的位置
      mapinf.ans[beforex][beforey]=0;//修改map的信息
      mapinf.ans[x][y]=num;
      if(nowchessNum>0){
        mapinf.chessmans[nowchessNum].isexit=false;
      }

      if(!mapinf.isJiangjun(!isRed)){
        mapinf.chessmans[num].setXY(beforex,beforey);//设置当前棋子的位置
        mapinf.ans[x][y]=nowchessNum;//还原map的信息
        mapinf.ans[beforex][beforey]=num;
        if(nowchessNum>0){
          mapinf.chessmans[nowchessNum].isexit=true;
        }
        return true;
      }
      mapinf.chessmans[num].setXY(beforex,beforey);//设置当前棋子的位置
      mapinf.ans[x][y]=nowchessNum;//还原map的信息
      mapinf.ans[beforex][beforey]=num;
      if(nowchessNum>0){
        mapinf.chessmans[nowchessNum].isexit=true;
      }


    }
  }

return false;


}


//哪一方，返回这个棋子可走的位置
mapinf.canGo=function(num){
  //判端这是谁的棋子
  let isRed=(num%2!=0)?true:false;

  let i=parseInt((num+1)/2);

  let nowX=chessmans[num].x;
  let nowY=chessmans[num].y;
  let arry=[];
  if(i>=1&&i<=5){//5兵
    for(var k=nowY-1;k<=nowY+1;++k){
      for(var j=nowX-1;j<=nowX+1;++j){
        if(k==nowY&&j==nowX) continue;
        if(k>=0&&k<=9&&j>=0&&j<=8&&this.isOK(num,j,k)){
          let temp=new Object();
          temp.x=j;
          temp.y=k;
          arry.push(temp);
        }
      }
    }
    return arry;

  }
  else if(i==6||i==7){//双炮

    //x不动，y去遍历
    for(let j=0;j<=9;++j){
      if(j==nowY) continue;
      if(this.isOK(num,nowX,j)){
        let temp=new Object();
        temp.x=nowX;
        temp.y=j;
        arry.push(temp);
      }
    }
    //y不动，x去遍历
    for(let j=0;j<=8;++j){
      if(j==nowX) continue;
      if(this.isOK(num,j,nowY)){
        let temp=new Object();
        temp.x=j;
        temp.y=nowY;
        arry.push(temp);
      }
    }
    return arry;
  }
  else if(i==8||i==16){//双车
    //x不动，y去遍历
    for(let j=0;j<=9;++j){
      if(j==nowY) continue;
      if(this.isOK(num,nowX,j)){
        let temp=new Object();
        temp.x=nowX;
        temp.y=j;
        arry.push(temp);
      }
    }
    //y不动，x去遍历
    for(let j=0;j<=8;++j){
      if(j==nowX) continue;
      if(this.isOK(num,j,nowY)){
        let temp=new Object();
        temp.x=j;
        temp.y=nowY;
        arry.push(temp);
      }
    }
    return arry;
  }
  else if(i==9||i==15){//双马
    for(var k=nowY-2;k<=nowY+2;++k){
      for(var j=nowX-2;j<=nowX+2;++j){
        if(k==nowY&&j==nowX) continue;
        if(k>=0&&k<=9&&j>=0&&j<=8&&this.isOK(num,j,k)){
          let temp=new Object();
          temp.x=j;
          temp.y=k;
          arry.push(temp);
        }
      }
    }
    return arry;
  }
  else if(i==10||i==14){//双相
    for(var k=nowY-2;k<=nowY+2;k+=4){
      for(var j=nowX-2;j<=nowX+2;j+=4){
        if(k==nowY&&j==nowX) continue;
        if(k>=0&&k<=9&&j>=0&&j<=8&&this.isOK(num,j,k)){
          let temp=new Object();
          temp.x=j;
          temp.y=k;
          arry.push(temp);
        }
      }
    }
    return arry;
  }
  else if(i==11||i==13){//双士
        let tempY;
        let tempX;
        if(nowY>4){
          tempY=8;
        }
        else {

          tempY=1;
        }
    tempX=4;
    for(var k=tempY-1;k<=tempY+1;k+=2){
      for(var j=tempX-1;j<=tempX+1;j+=2){
        if(k==nowY&&j==nowX) continue;
        if(k>=0&&k<=9&&j>=0&&j<=8&&this.isOK(num,j,k)){
          let temp=new Object();
          temp.x=j;
          temp.y=k;
          arry.push(temp);
        }
      }
    }
    if(nowX!=tempX&&this.isOK(num,tempX,tempY)){
      let temp=new Object();
      temp.x=tempX;
      temp.y=tempY;
      arry.push(temp);
    }
    return arry;
  }
  else if(i==12){//单将
    let tempY;
    let tempX;
    if(nowY>4){
      tempY=8;
    }
    else {

      tempY=1;
    }
    tempX=4;
    for(var k=nowY-2;k<=nowY+2;k++){
      for(var j=nowX-2;j<=nowX+2;j++){
        if(k==nowY&&j==nowX) continue;
        if(k>=0&&k<=9&&j>=0&&j<=8&&this.isOK(num,j,k)){
          let temp=new Object();
          temp.x=j;
          temp.y=k;
          arry.push(temp);
        }
      }
    }
    return arry;

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
    let othershuaiChess;
    if(belongRed){
      othershuaiChess=this.chessmans[24];
    }
    else{
      othershuaiChess=this.chessmans[23];
    }
    OK=this.shuai(nowX,nowY,x,y,othershuaiChess);
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

mapinf.shuai=function (nowX,nowY,x,y,othershuaiChess) {//帅的规则
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
    if( nowX==x&&othershuaiChess.x==x&&othershuaiChess.y){
      var count=0;
      let e=Math.max(nowY,y);
      let s=Math.min(nowY,y);
      for(var i=s+1;i<e;++i){
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

mapinf.setInf=function(data){


  onlineUser.userState=data.onlineUserList[onlineUser.userInform.id].userState;
  onlineUser.roomId=data.onlineUserList[onlineUser.userInform.id].roomId;
  mapinf.onlineUserList=data.onlineUserList;

  mapinf.roomId=data.roomId;
  mapinf.roomState= data.roomState;
  mapinf.diannum=data.diannum;
  mapinf.guangnum=data.guangnum;
  mapinf.chooseman=data.chooseman;
  mapinf.redisReady=data.redisReady;
  mapinf.blackisReady=data.blackisReady;
  mapinf.redName=data.redName;
  mapinf.blackName=data.blackName;
  mapinf.redTime=data.redTime;
  mapinf.redSteptime=data.redSteptime;
  mapinf.blackTime=data.blackTime;
  mapinf.blackSteptime=data.blackSteptime;
  mapinf.isRed= data.red;
  if(onlineUser.userInform.name==mapinf.blackName){
    mapinf.userisRed=false;
  }
  else {
    mapinf.userisRed=true;
  }

  for(var i=0;i<9;i++){

    for(var j=0;j<10;j++){
      if(mapinf.userisRed){
        mapinf.ans[i][j]=data.ans[i][j];
      }
      else {
        mapinf.ans[i][9-j]=data.ans[i][j];
      }

    }
  }
  for(var i=1;i<=32;++i){

    mapinf.chessmans[i].isexit=data.chessmans[i].isexit;
    mapinf.chessmans[i].isflash=data.chessmans[i].isflash;
    if(mapinf.userisRed){
      mapinf.chessmans[i].setXY(data.chessmans[i].x,data.chessmans[i].y);

    }
    else {
      mapinf.chessmans[i].setXY(data.chessmans[i].x,9-data.chessmans[i].y);
    }

    mapinf.chessmans[i].num=data.chessmans[i].num;
    mapinf.chessmans[i].bgurl=data.chessmans[i].bgurl;

  }

}




//
// $(document).ready(function(){
//   $.ajax({
//     type: "post",
//     url: "http://127.0.0.1:8081/ssm/getuserInform",
//     headers: {
//       'Content-Type': 'application/json;charset=UTF-8',
//     },
//     withCredentials : true,
//     dataType: "json",
//     success: function(data){
//       console.log(data);
//     }
//   });
// });
var dialogTableVisible=new Object();
dialogTableVisible.data=false;
dialogTableVisible.data1=false
dialogTableVisible.innerVisible=false;

var rePlayInform=new Object();
rePlayInform.gameInform=new Object();
rePlayInform.stepInform=[];

var ws=null;

export default {
  mapinf,imgarrys,onlineUser,dialogTableVisible,messageInform1,rePlayInform,ws
}

