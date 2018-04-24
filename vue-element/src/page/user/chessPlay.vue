<template>
  <div style="width: 1530px">
    <div id="div1">
      <img src="../../../static/img/background4.jpg" />
    </div>

    <el-row :gutter="24" style="margin: 0">

      <el-col :span="3" :offset="1"  >
        <el-card :body-style="{ paddingTop: '2px' }" id="roomid" shadow="hover"
                 style="margin-top:20px;
                       font-size:18px;
                       width: 140px;
                       height: 30px;
                       background-color: transparent">
          房间号:{{mapinf.roomId}}
        </el-card>
        <el-row :gutter="24" style="margin-top: 30px;">
          <user-card :chessType="(!this.mapinf.userisRed)?'红方':'黑方'"
                     :gametime="(!this.mapinf.userisRed)?this.mapinf.redTime:this.mapinf.blackTime"
                     :steptime="(!this.mapinf.userisRed)?this.mapinf.redSteptime:this.mapinf.blackSteptime"
                     :username="(!this.mapinf.userisRed)?this.mapinf.redName:this.mapinf.blackName"
                     :isReady="(!this.mapinf.userisRed)?this.mapinf.redisReady:this.mapinf.blackisReady"
                     :style="upstyle"></user-card>
        </el-row>
        <el-row :gutter="24" style="margin-top: 100px;">
          <user-card :chessType="this.mapinf.userisRed?'红方':'黑方'"
                     :gametime="this.mapinf.userisRed?this.mapinf.redTime:this.mapinf.blackTime"
                     :steptime="(this.mapinf.userisRed)?this.mapinf.redSteptime:this.mapinf.blackSteptime"
                     :username="this.mapinf.userisRed?this.mapinf.redName:this.mapinf.blackName"
                     :isReady="this.mapinf.userisRed?this.mapinf.redisReady:this.mapinf.blackisReady"
                     :style="downstyle"></user-card>
        </el-row>
      </el-col>
      <el-col :span="10" :offset="2" style="margin-top: 10px">
        <chess style="height: 716px;"></chess>
      </el-col>
      <el-col  :span="2"   >
        <template v-if="this.BoardValue.onlineUser.userState!=3">
          <el-row>
            <el-button v-if="!userisReady" type="success" @click="readyGo"  style="margin-top: 520px;width: 100%">准备</el-button>
            <el-button v-else :disabled="isStar"   type="info" @click="FreadyGo"  style="margin-top: 520px;width: 100%">取消准备</el-button>
          </el-row>

          <el-row>
            <el-button v-if="!isStar" @click="toGamelobby" type="danger" style="margin-top: 60px;width: 100%">退出</el-button>
          </el-row>
          <el-row>
            <el-button v-if="isStar" @click="surrender"  type="danger" style="margin-top: 20px;width: 100%">认输</el-button>
          </el-row>
          <el-row>
            <el-button v-if="isStar" @click="peace" type="warning" size="medium" style="margin-top: 20px;width: 100%">和棋</el-button>
          </el-row>

        </template>
        <el-row v-else>
          <el-button  @click="toGamelobby" type="danger" style="margin-top: 650px;width: 100%">退出</el-button>
        </el-row>

      </el-col>

      <el-col :span="5" style="margin-top: 00px" >
        <el-row >

          <el-table
            :data="tableData7"
            border
            height="300"
            size="small "
            style="width: 100%;margin-top: 10px;font-size: 15px ">
            <el-table-column label="观战玩家" >
              <el-table-column
                prop="name"
                label="账号"
                width="150"
                >
              </el-table-column>
              <el-table-column
                prop="state"
                label="状态"
                width="142"
                >
                观战中
              </el-table-column>
            </el-table-column>

          </el-table>
        </el-row>
        <el-row style="margin-top: 30px" >
          <chatboard ></chatboard>
        </el-row>
      </el-col>
    </el-row>

  </div>


</template>

<script>

  import Chess from "./chessComponets/chess";
  import UserCard from "./chessComponets/userCard";
  import Chatboard from "./chessComponets/chatboard";
    export default {
        name: "chessPlay",
      data(){
          return{

            mapinf:this.BoardValue.mapinf,
            textarea:'',

            time1:'',
            time2:'',
            settimes:'1000',
            tableData7:[],
          }
      },
      mounted(){

      },

      methods:{
          peace(){//求和
            let num;
            if(this.BoardValue.mapinf.userisRed){
              //9是红方求和
              num=this.BoardValue.mapinf.roomId+'9';

            }
            else{
              //8是黑方求和
              num=this.BoardValue.mapinf.roomId+'8';
            }
            this.ajaxResult(num);
          },
          surrender(){//投降

            this.$confirm('确认投降吗?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              //红方投降
              let num;
              if(this.BoardValue.mapinf.userisRed){
                num=this.BoardValue.mapinf.roomId+'7';

              }
              else{
                num=this.BoardValue.mapinf.roomId+'6';
              }
              this.ajaxResult(num);


            }).catch(() => {
              this.$message({
                type: 'info',
                message: '已取消'
              });
            });

          },
          ajaxroomInfor(api){
            var jsonData = JSON.stringify( this.BoardValue.mapinf);
            this.$axios({
              method: 'post',
              headers: {
                'Content-Type': 'application/json;charset=UTF-8',
              },

              withCredentials : true,
              url: '/ssm/user/'+api,//listUserInform
              data: jsonData,
            }).then(function (response) {

              if(response=="error"){
                alert("失败");
              }else{

              }
            })
              .catch(function (error) {
                console.log(error);
              });
          },
        ajaxGameLoby(){
          var jsonData = JSON.stringify( this.BoardValue.onlineUser);
          this.$axios({
            method: 'post',
            headers: {
              'Content-Type': 'application/json;charset=UTF-8',
            },

            withCredentials : true,
            url: '/ssm/user/togameLobby',//listUserInform
            data: jsonData,
          }).then(function (response) {

            if(response=="error"){
              alert("失败");
            }else{

            }
          })
            .catch(function (error) {
              console.log(error);
            });
        },
        toGamelobby(){
          var vum=this;
          this.$confirm('是否退出该房间?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {

            vum.BoardValue.dialogTableVisible.data=false;
            vum.ajaxGameLoby();
            this.$message({
              type: 'success',
              message: '成功退出'
            });
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消'
            });
          });
        },
        FreadyGo(){

          if(this.mapinf.userisRed){
            this.mapinf.redisReady=false;
          }
          else{
            this.mapinf.blackisReady=false;
          }
          this.ajaxroomInfor("userReady");
        },
        readyGo(){
          if(this.mapinf.userisRed){
            this.mapinf.redisReady=true;
          }
          else{
            this.mapinf.blackisReady=true;
          }
          this.ajaxroomInfor("userReady");
        },
        ajaxResult(num){
          var jsonData = JSON.stringify(num);
          this.$axios({
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

      },
      watch:{
          //当玩家列表更新时
          'mapinf.onlineUserList':function () {
            let vum=this;
            vum.tableData7=[];
            for(let key in vum.BoardValue.mapinf.onlineUserList){
              if(vum.BoardValue.mapinf.onlineUserList[key].userState==3){
                let name= vum.BoardValue.mapinf.onlineUserList[key].userInform.name;
                let temp=new Object()
                temp.name=name;
                temp.state='观战中';
                vum.tableData7.push(temp);
              }

            }
          },
          //黑方超过局时
        'mapinf.blackTime':function () {
            if(this.mapinf.blackTime==0){
              let num=this.BoardValue.mapinf.roomId+'4';
              this.ajaxResult(num);
            }
        },
        //红方超过局时
        'mapinf.redTime':function () {
            if(this.mapinf.redTime==0){
              let num=this.BoardValue.mapinf.roomId+'5';
              this.ajaxResult(num);
            }
        },
        //红方超过步时
        'mapinf.redSteptime':function () {
            if(this.mapinf.redSteptime==0){
              let num=this.BoardValue.mapinf.roomId+'3';
              this.ajaxResult(num);
            }
        },
        //黑方超过步时
        'mapinf.blackSteptime':function () {
            if(this.mapinf.blackSteptime==0){
              let num=this.BoardValue.mapinf.roomId+'2';
              this.ajaxResult(num);
            }
        }

      },
      computed:{

          userisReady(){
            if(this.mapinf.userisRed){
              if(this.mapinf.redisReady){
                return true;
              }
              else {
                return false;
              }
            }
            else{
              if(this.mapinf.blackisReady){
                return true;
              }
              else{
                return false;
              }

            }
          },
          isStar(){
            if(this.mapinf.roomState==1){
              return true;
            }
            else{
              return false;
            }
          },
          upstyle(){
            let num;

            if(!this.mapinf.userisRed){
              if(this.mapinf.isRed){
                num=0.8;
              }
              else{
                num=0.3;
              }
                return {backgroundColor:'red',opacity:num}
            }
            else{
              if(this.mapinf.isRed){
                num=0.3;
              }
              else{
                num=0.8;
              }
              return {backgroundColor:'black',opacity:num}
            }

          },
          downstyle(){
            let num;

            if(!this.mapinf.userisRed){
              if(this.mapinf.isRed){
                num=0.3;
              }
              else{
                num=0.8;
              }
              return {backgroundColor:'black',opacity:num}
            }
            else{
              if(this.mapinf.isRed){
                num=0.8;
              }
              else{
                num=0.3;
              }
              return {backgroundColor:'red',opacity:num}
            }
          }
      },
      components: {Chatboard, UserCard, Chess}

    }
</script>

<style scoped>
  #roomid > *{
      padding-top: 0 !important;
  }
  div#div1{
    position:fixed;
    top:0;
    left:0;
    bottom:0;
    right:0;
    z-index:-5;
  }
  div#div1 > img {

    height:100%;
    width:100%;
    border:0;
  }


</style>
