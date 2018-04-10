<template>
  <div style="width: 1536px">
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
          房间号:999
        </el-card>
        <el-row :gutter="24" style="margin-top: 30px;">
          <user-card :chessType="(!this.mapinf.userisRed)?'红方':'黑方'"
                     :gametime="(!this.mapinf.userisRed)?this.mapinf.redTime:this.mapinf.blackTime"
                     :steptime="steptimeMsg1"
                     :username="(!this.mapinf.userisRed)?this.mapinf.redName:this.mapinf.blackName"
                     :isReady="(!this.mapinf.userisRed)?this.mapinf.redisReady:this.mapinf.blackisReady"
                     :style="upstyle"></user-card>
        </el-row>
        <el-row :gutter="24" style="margin-top: 100px;">
          <user-card :chessType="this.mapinf.userisRed?'红方':'黑方'"
                     :gametime="this.mapinf.userisRed?this.mapinf.redTime:this.mapinf.blackTime"
                     :steptime="steptimeMsg2"
                     :username="this.mapinf.userisRed?this.mapinf.redName:this.mapinf.blackName"
                     :isReady="this.mapinf.userisRed?this.mapinf.redisReady:this.mapinf.blackisReady"
                     :style="downstyle"></user-card>
        </el-row>
      </el-col>
      <el-col :span="10" :offset="2" style="margin-top: 10px">
        <chess style="height: 716px;"></chess>
      </el-col>
      <el-col :span="2"   >
        <el-row>
          <el-button v-if="!userisReady" type="success" @click="readyGo"  style="margin-top: 500px;width: 100%">准备</el-button>
          <el-button v-else :disabled="isStar"   type="info" @click="FreadyGo"  style="margin-top: 500px;width: 100%">取消准备</el-button>
        </el-row>
        <el-row>
          <el-button v-if="!isStar" @click="toGamelobby" type="danger" style="margin-top: 60px;width: 100%">退出</el-button>
        </el-row>
        <el-row>
          <el-button v-if="isStar" type="danger" style="margin-top: 20px;width: 100%">认输</el-button>
        </el-row>
        <el-row>
        <el-button v-if="isStar" type="warning" size="medium" style="margin-top: 20px;width: 100%">和棋</el-button>
      </el-row>
        <el-row>
          <el-button v-if="isStar" type="primary" style="margin-top: 20px;width: 100%">悔棋</el-button>
        </el-row>
      </el-col>

      <el-col :span="5" style="margin-top: 00px" >
        <el-row >

          <el-table
            :data="tableData6"
            border
            height="300"
            size="small "
            style="width: 100%;margin-top: 10px;font-size: 15px ">
            <el-table-column label="观战玩家" >
              <el-table-column
                prop="id"
                label="账号"
                >
              </el-table-column>
              <el-table-column
                prop="amount3"
                label="状态"
                >
              </el-table-column>
            </el-table-column>

          </el-table>
        </el-row>
        <el-row style="margin-top: 20px" >
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
            steptimeMsg1:180,
            steptimeMsg2:180,
            time1:'',
            time2:'',
            settimes:'1000',
            tableData6: [{
              id: '12987122',
              name: '王小虎',
              amount1: '234',
              amount2: '3.2',
              amount3: 10
            },

               {
                id: '12987125',
                name: '王小虎',
                amount1: '621',
                amount2: '2.2',
                amount3: 17
              }, {
                id: '12987126',
                name: '王小虎',
                amount1: '539',
                amount2: '4.1',
                amount3: 15
              }],
          }
      },
      mounted(){

      },
      methods:{
        toGamelobby(){

          this.$confirm('是否退出该房间?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$router.push('/gameLobby');
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

        },
        readyGo(){
          if(this.mapinf.userisRed){
            this.mapinf.redisReady=true;
          }
          else{
            this.mapinf.blackisReady=true;
          }

        },
        changetime1(vum){
          if(vum.time2!=''||vum.time2!=null){
            clearInterval(vum.time2);

            vum.steptimeMsg2=180;
          }
          vum.time2=setInterval(function () {
            if(vum.steptimeMsg1==0){
              clearInterval(vum.time2);
              clearInterval(vum.time1);

            }
            if(vum.steptimeMsg1>0)
              vum.steptimeMsg1--;
          }, vum.settimes);
        },
        changetime2(vum){
          if(vum.time2!=''||vum.time2!=null){
            clearInterval(vum.time2);

            vum.steptimeMsg1=180;
          }
          vum.time2=setInterval(function () {

            if(vum.steptimeMsg2==0){
              clearInterval(vum.time2);
              clearInterval(vum.time1);

            }
            if(vum.steptimeMsg2>0)
              vum.steptimeMsg2--;

          }, vum.settimes);
          }
      },
      watch:{

        'mapinf.isRed':function (val, oldVal) {
          let vum=this;
          if(vum.mapinf.isRed){//控制红色方的总时间
            if(vum.time1!=''||vum.time1!=null){

              clearInterval(vum.time1);
            }
            vum.time1=setInterval(function () {
                if(vum.mapinf.redTime==0){
                  clearInterval(vum.time2);
                  clearInterval(vum.time1);
                }
              if(vum.mapinf.redTime>0)
                vum.mapinf.redTime--;
              }, vum.settimes);
            if(vum.mapinf.userisRed){//改变2
              vum.changetime2(vum);
            }
            else{//改变1
              vum.changetime1(vum);
            }
          }
          else{//控制黑色方的总时间

            if(vum.time1!=''||vum.time1!=null){
              clearInterval(vum.time1);
            }
            vum.time1=setInterval(function () {
              if(vum.mapinf.blackTime==0) {
                clearInterval(vum.time2);
                clearInterval(vum.time1);
              }
              if(vum.mapinf.blackTime>0)
                vum.mapinf.blackTime--;
            }, vum.settimes);
            if(vum.mapinf.userisRed){//改变1
              vum.changetime1(vum);
            }
            else{
              //改变2
              vum.changetime2(vum);
            }
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
            if(this.mapinf.redisReady&&this.mapinf.blackisReady){
              return true;
            }
            else{
              return false;
            }
          },
          upstyle(){
            let num;
            if(this.mapinf.isRed){
              num=0.3;
            }
            else{
              num=0.8;
            }
            if(!this.mapinf.userisRed){
                return {backgroundColor:'red',opacity:num}
            }
            else{
              return {backgroundColor:'black',opacity:num}
            }

          },
          downstyle(){
            let num;
            if(this.mapinf.isRed){
              num=0.8;
            }
            else{
              num=0.3;
            }
            if(!this.mapinf.userisRed){
              return {backgroundColor:'black',opacity:num}
            }
            else{
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
