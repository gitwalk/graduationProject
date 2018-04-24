<template>
  <div class="gameLobby" style="background-color: transparent">

    <el-row :gutter="20">
      <el-col :span="16">
<!--------------------------------------搜索房间号----------------------------------------------------->
        <div>
          <el-row :gutter="20" style="margin-bottom: 10px;">
            <el-col :span="8">
              <el-input
                placeholder="搜索房间号"
                v-model="input10"
                clearable>
              </el-input>
            </el-col>

            <el-col :span="2" :offset="10">
              <el-button type="danger" icon="el-icon-plus" round @click="buildRoom">创建房间</el-button>


            </el-col>
          </el-row>

        </div>
<!---------------------------------------------------房间列表---------------------------------------------->
        <div class="grid-content bg-purple roomdiv" style="background-color: transparent">
          <el-table
            :data="tableDataresult"
            style="width: 100%;background-color: transparent" >
            <el-table-column label="房间列表">
              <el-table-column
                label="房间号"
                :span="5" >
                <template slot-scope="scope" >
                  <i class="el-icon-info"></i>
                  <span style="margin-left: 10px">{{ scope.row.roomId }}</span>
                </template>
              </el-table-column>
              <el-table-column
                label="红方"
                :span="5" >
                <template slot-scope="scope">
                  <div slot="reference" class="name-wrapper">
                    <el-tag size="medium">{{ scope.row.redName }}</el-tag>
                  </div>
                </template>
              </el-table-column>
              <el-table-column
                label="黑方"
                :span="5">
                <template slot-scope="scope">
                  <div slot="reference" class="name-wrapper">
                    <el-tag size="medium">{{ scope.row.blackName }}</el-tag>
                  </div>
                </template>
              </el-table-column>
              <el-table-column
                label="游戏状态"
                :span="5">
                <template slot-scope="scope">

                  <span style="margin-left: 10px">{{ scope.row.roomState }}</span>
                </template>
              </el-table-column>
              <el-table-column
                label="旁观人数"
                :span="5">
                <template slot-scope="scope">

                  <span style="margin-left: 10px">{{ scope.row.watchNum }}</span>
                </template>
              </el-table-column>
              <el-table-column label="操作" :span="5">
                <template slot-scope="scope">
                  <el-button v-if="scope.row.blackName=='未加入'||scope.row.redName=='未加入' "
                    size="mini"
                    @click="handleEdit(scope.$index, scope.row)">加入</el-button>
                  <el-button v-else-if="scope.row.roomState=='进行中' "
                    size="mini"
                    @click="handleEdit(scope.$index, scope.row)">观战</el-button>

                </template>
              </el-table-column>
            </el-table-column>

          </el-table>
        </div>
<!-----------------------------------------------------分页条------------------------------------------>
        <el-row :gutter="24" style="margin-top: 2%">
          <el-col :span="8" :offset="3">
          <!--<el-pagination-->
            <!--background-->
            <!--layout="prev, pager, next"-->
            <!--:total="500">-->
          <!--</el-pagination>-->
          </el-col>
        </el-row>
      </el-col>
      <el-col :span="8">
<!-----------------------------------------在线列表--------------------------------------------------->

<!---------------------------------------------------搜索在线玩家------------------------------------------->
        <div class="grid-content bg-purple">
          <div>
            <el-row :gutter="20" style="margin-bottom: 10px;">
              <el-col :span="20">
                <el-input
                  placeholder="搜索玩家昵称"
                  v-model="input1"
                  clearable>
                </el-input>
              </el-col>
            </el-row>
          </div>
<!------------------------------------------------在线表格------------------------------------------->
          <el-table
            custom-class="gameRoom"
            :data="tableData6result"
            border
            height="570"
            :summary-method="getSummaries"
            show-summary
            style="width: 100%; ">
            <el-table-column label="玩家列表">
              <el-table-column
                prop="userInform.name"
                label="昵称"
                width="150">
              </el-table-column>
              <el-table-column
                prop="userState"
                label="状态"
                width="122">
              </el-table-column>
            </el-table-column>

          </el-table>

        </div>
        <!--<el-button type="danger" round size="medium ">创建房间</el-button>-->
      </el-col>
    </el-row>


    <el-dialog  :show-close="false"  title="游戏房间" fullscreen :visible.sync="dialogTableVisible.data">

      <chess-play></chess-play>
    </el-dialog>

  </div>
</template>

<script>
    import Chessboard from "../chessComponets/chessboard";
    import ChessPlay from "../chessPlay";
    export default {
        name: "gameLobby",
      components: {ChessPlay, Chessboard},
      data() {
        return {
          input1:'',
          tableDataresult:[],
          tableData6result:[],
          dialogTableVisible:this.BoardValue.dialogTableVisible,
          tableData6:[],
          input10: '',
          tableData: [],

        }
      },
      created (){
        this.inite();
      },
      watch:{
        input10:function (val,oldval) {

          let vum=this;
          vum.tableDataresult=[];
          for(var key in vum.tableData){
            if(vum.tableData[key].roomId.indexOf(val)==0){
              vum.tableDataresult.push(vum.tableData[key]);
            }
          }

        },
        input1:function (val,oldval) {

          let vum=this;
          vum.tableData6result=[];
          for(var key in vum.tableData6){
            if(vum.tableData6[key].userInform.name.indexOf(val)==0){
              vum.tableData6result.push(vum.tableData6[key]);
            }
          }

        }
      },
      methods: {

        outInform(response){
            if(response.data==""){

              this.$alert('请重新登录', '消息', {
                confirmButtonText: '确定',
                callback: action => {
                  if(this.BoardValue.ws!=null){
                    this.BoardValue.ws.close();
                  }
                  this.$router.push('/gamePlay');
                }
              });
            }
          },
        ajaxpace(num){
          var jsonData = JSON.stringify(num);
          var vum=this;
          this.$axios({
            method: 'post',
            headers: {
              'Content-Type': 'application/json;charset=UTF-8',
            },
            withCredentials : true,
            url: '/ssm/user/dealpace',//listUserInform
            data: jsonData,
          }).then(function (response) {
            vum.outInform(response);
          })
            .catch(function (error) {

            });
        },
        togameLobby(){
          var jsonData = JSON.stringify( this.BoardValue.onlineUser);
          var vum=this;
          this.$axios({
            method: 'post',
            headers: {
              'Content-Type': 'application/json;charset=UTF-8',
            },

            withCredentials : true,
            url: '/ssm/user/togameLobby',//listUserInform
            data: jsonData,
          }).then(function (response) {

            vum.outInform(response);
          })
            .catch(function (error) {
              console.log(error);
            });
        },
        buildRoom(){

          var jsonData = JSON.stringify( this.BoardValue.mapinf);
          var thisVue=this;
          this.$axios({
            method: 'post',
            headers: {
              'Content-Type': 'application/json;charset=UTF-8',
            },

            withCredentials : true,
            url: '/ssm/user/buildRoom',//listUserInform
            data: jsonData,
          }).then(function (response) {
            if(response.data==""){
              thisVue.outInform(response);
            }
            else if(response=="error"){
              alert("失败");
            }else{
              thisVue.BoardValue.imgarrys.splice(0,thisVue.BoardValue.imgarrys.length);//清空数组

              thisVue.dialogTableVisible.data = true;

            }
          })
            .catch(function (error) {
              console.log(error);
            });
        },
        inite(){
          let vum=this;
          vum.togameLobby();
          // var ws = new WebSocket("ws://127.0.0.1:8081/ssm/myHandler");
          vum.BoardValue.ws.onmessage = function (msg) {

            let str= msg.data.split("|");
            let obj = eval('(' +  str[0] + ')');
            if(str[1]=="kill"){
              if(obj==0){
                vum.$alert('绝杀', '红方胜利', {
                  confirmButtonText: '确定',
                  callback: action => {
                    vum.togameLobby();
                    vum.BoardValue.dialogTableVisible.data=false;
                  }
                });
              }
              else if(obj==1){
                vum.$alert('绝杀', '黑方胜利', {
                  confirmButtonText: '确定',
                  callback: action => {
                    vum.togameLobby();
                    vum.BoardValue.dialogTableVisible.data=false;
                  }
                });
              }
              let audioOver = document.getElementById('audioOver');//播放棋子走的声音
              audioOver.play();
            }
            else if(str[1]=="messageInform"){
              // vum.BoardValue.messageInform1=obj;
              vum.BoardValue.messageInform1.time=new Date().getTime();
              vum.BoardValue.messageInform1.data=obj.data;
              vum.BoardValue.messageInform1.roomId=obj.roomId;
              vum.BoardValue.messageInform1.userId=obj.userId;
              vum.BoardValue.messageInform1.userName=obj.userName;
            }
            else if(str[1]=="paceresult"){
              if(obj==1){
                vum.$alert('和局', '游戏结束', {
                  confirmButtonText: '确定',
                  callback: action => {
                    vum.togameLobby();
                    vum.BoardValue.dialogTableVisible.data=false;

                  }
                });
                let audioOver = document.getElementById('audioOver');//播放棋子走的声音
                audioOver.play();
              }
              else if(obj==0) {
                vum.$alert('对方拒绝和局', '信息', {
                  confirmButtonText: '确定',
                  callback: action => {

                  }
                });
              }

            }
            else if(str[1]=="peace"){
              //黑方求和
              if(obj==8&&vum.BoardValue.mapinf.userisRed){

                vum.$confirm('黑方求和, 是否同意?', '求和', {
                  confirmButtonText: '同意',
                  cancelButtonText: '拒绝',
                  type: 'warning'
                }).then(() => {
                  vum.$message({
                    type: 'success',
                    message: '已同意'
                  });

                  vum.ajaxpace(vum.BoardValue.mapinf.roomId+'81');


                }).catch(() => {
                  vum.$message({
                    type: 'info',
                    message: '已拒绝'
                  });
                  vum.ajaxpace(vum.BoardValue.mapinf.roomId+'80');
                });
              }
              //红方求和
              else if(obj==9&&!vum.BoardValue.mapinf.userisRed){
                vum.$confirm('红方求和, 是否同意?', '求和', {
                  confirmButtonText: '同意',
                  cancelButtonText: '拒绝',
                  type: 'warning'
                }).then(() => {
                  vum.$message({
                    type: 'success',
                    message: '已同意'
                  });
                  vum.ajaxpace(vum.BoardValue.mapinf.roomId+'91');

                }).catch(() => {
                  vum.$message({
                    type: 'info',
                    message: '已拒绝'
                  });
                  vum.ajaxpace(vum.BoardValue.mapinf.roomId+'90');
                });
              }
            }
            else if(str[1]=="surrender"){//有玩家投降
              if(obj==6){
                vum.$alert('黑方投降', '红方胜利', {
                  confirmButtonText: '确定',
                  callback: action => {
                    vum.togameLobby();
                    vum.BoardValue.dialogTableVisible.data=false;
                  }
                });
              }
              else if(obj==7){
                vum.$alert('红方投降', '黑方胜利', {
                  confirmButtonText: '确定',
                  callback: action => {
                    vum.togameLobby();
                    vum.BoardValue.dialogTableVisible.data=false;
                  }
                });
              }
              let audioOver = document.getElementById('audioOver');//播放棋子走的声音
              audioOver.play();
            }
            else if(str[1]=="roomuserout"){//用户退出房间
              vum.BoardValue.onlineUser.userState=obj.onlineUserList[vum.BoardValue.onlineUser.userInform.id].userState;
              vum.BoardValue.onlineUser.roomId=obj.onlineUserList[vum.BoardValue.onlineUser.userInform.id].roomId;
              vum.BoardValue.mapinf.onlineUserList=obj.onlineUserList;
              vum.BoardValue.mapinf.redName=obj.redName;
              vum.BoardValue.mapinf.blackName=obj.blackName;
              // vum.BoardValue.mapinf.redisReady=obj.redisReady;
              // vum.BoardValue.mapinf.blackisReady=obj.blackisReady;
            }
            else if(str[1]=="overtime"){//用户超时
              console.log(obj);
              if(obj==2){
                vum.$alert('黑方超过步时', '红方胜利', {
                  confirmButtonText: '确定',
                  callback: action => {
                    vum.togameLobby();
                    vum.BoardValue.dialogTableVisible.data=false;
                  }
                });
              }
              else if(obj==3){

                vum.$alert('红方超过步时', '黑方胜利', {
                  confirmButtonText: '确定',
                  callback: action => {
                    vum.togameLobby();
                    vum.$set(vum.BoardValue.dialogTableVisible,'data',false);
                  }
                });

              }
              else if(obj==4){
                vum.$alert('黑方超过局时', '红方胜利', {
                  confirmButtonText: '确定',
                  callback: action => {
                    vum.togameLobby();
                    vum.BoardValue.dialogTableVisible.data=false;
                  }
                });
              }
              else if(obj==5){
                vum.$alert('红方超过局时', '黑方胜利', {
                  confirmButtonText: '确定',
                  callback: action => {
                    vum.togameLobby();
                    vum.BoardValue.dialogTableVisible.data=false;
                  }
                });
              }
              vum.BoardValue.mapinf.roomState=3;

            }
            else if(str[1]=="steptime"){//广播步时
              vum.BoardValue.mapinf.redSteptime=obj.redSteptime;
              vum.BoardValue.mapinf.blackSteptime=obj.blackSteptime;
              vum.BoardValue.mapinf.blackTime=obj.blackTime;
              vum.BoardValue.mapinf.redTime=obj.redTime;

            }
            else if(str[1]=="submitStepinform"){
              let num=obj.chessNum;
              let oldX=vum.BoardValue.mapinf.chessmans[num].x;
              let oldY=vum.BoardValue.mapinf.chessmans[num].y;
                if(num%2==0){
                  vum.BoardValue.mapinf.isRed=true;
                }
                else {
                  vum.BoardValue.mapinf.isRed=false;
                }
                if(!vum.BoardValue.mapinf.userisRed){

                  obj.pointY=9-obj.pointY;
                }
                if(oldX!=obj.pointX||oldY!=obj.pointY){

                  vum.BoardValue.mapinf.setXY(num,obj.pointX,obj.pointY);
                }



            }
            else if(str[1]=="userReady"){
              vum.BoardValue.mapinf.setInf(obj);
              if(vum.BoardValue.mapinf.redisReady&&vum.BoardValue.mapinf.blackisReady)
              {
                vum.BoardValue.imgarrys.push({imziNum:4});

              }
            }
            else if(str[1]=="roomuser"){
              vum.BoardValue.mapinf.setInf(obj);

            }
            else if(str[1]=="joingameLobby"){

              //在线列表的初始化
              //obj[-1].onlineUserList
              vum.tableData6=[];
              vum.tableData=[];
              vum.tableDataresult=[];
              vum.tableData6result=[];
              for(var i in obj){//循环每个房间

                let count=0;

                for(var key in obj[i].onlineUserList){
                  if(obj[i].onlineUserList[key].userState=="3"){
                    count++;
                  }
                  vum.tableData6.push(obj[i].onlineUserList[key]);
                }
                if(i!=0){
                  let temp=new Object();
                  temp.roomId=i;
                  if(obj[i].redName==null||obj[i].redName==""){
                    if(obj[i].roomState=="2"){
                      temp.redName="已退出"
                    }
                    else {
                      temp.redName="未加入";
                    }

                  }
                  else{
                    temp.redName=obj[i].redName;
                  }
                  if(obj[i].blackName==null||obj[i].blackName==""){
                    if(obj[i].roomState=="2"){
                      temp.blackName="已退出"
                    }
                    else {
                      temp.blackName="未加入";
                    }

                  }
                  else{
                    temp.blackName=obj[i].blackName;
                  }

                  if(obj[i].roomState=="0"){
                    temp.roomState="等待";
                  }
                  else if(obj[i].roomState=="1"){
                    temp.roomState="进行中";
                  }
                  else if(obj[i].roomState=="2"){
                    temp.roomState="结束";
                  }
                  temp.watchNum=count;
                  vum.tableData.push(temp);
                  vum.tableDataresult.push(temp);
                }
              }

              for(var i in vum.tableData6){
                var userStNum=vum.tableData6[i].userState;
                if(userStNum=='4'){
                  userStNum="在线"
                }
                else if(userStNum=='3'){
                  userStNum="观战中"
                }
                else if(userStNum=='1'||userStNum=='2'){
                  userStNum="等待"
                }
                else if(userStNum=='5'||userStNum=='6'){
                  userStNum="对局中"
                }
                else if(userStNum=='0'){
                  userStNum="掉线"
                }
                vum.tableData6[i].userState=userStNum;
                vum.tableData6result.push(vum.tableData6[i]);
              }


            }

          }


        },
        handleEdit(index, row) {

          var thisVue=this;
          var jsonData = JSON.stringify( row.roomId);
          this.$axios({
            method: 'post',
            headers: {
              'Content-Type': 'application/json;charset=UTF-8',
            },

            withCredentials : true,
            url: '/ssm/user/joingame',//listUserInform
            data: jsonData,
          }).then(function (response) {
            if(response.data==""){
              thisVue.outInform(response);
            }
            else if(response=="error"){
              alert("失败");
            }else{
              thisVue.BoardValue.mapinf.setInf(response.data);
              thisVue.dialogTableVisible.data=true;
              thisVue.BoardValue.imgarrys.splice(0,thisVue.BoardValue.imgarrys.length);//清空数组
              // thisVue.$router.push('/chessPlay??roomId='+row.roomId);
            }
          })
            .catch(function (error) {
              console.log(error);
            });
        },
        handleDelete(index, row) {
          console.log(index, row);
        },
        getSummaries(param) {
          const { columns, data } = param;
          const sums = [];
          columns.forEach((column, index) => {
            if (index === 0) {
              sums[index] = '在线人数';
              return;
            }
            sums[index]=this.tableData6.length+"人";

          });

          return sums;
        }

      }
    }
</script>

<style >

  .gameLobby>.el-dialog__wrapper>.el-dialog>.el-dialog__body{
      background-image: url('../../../../static/img/background4.jpg');
    padding: 0;
    }
  .gameLobby>.el-dialog__wrapper>.el-dialog>.el-dialog__header{
      display:none;
    }
    .roomdiv .roomdiv>* {
      background-color: transparent;
    }



</style>
