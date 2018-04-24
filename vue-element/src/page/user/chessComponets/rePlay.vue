<template>
  <div  style="width: 1230px">
    <el-row :gutter="20">
      <el-col :span="3" :offset="1"  >
        <el-row :gutter="24" style="margin-top: 70px;">
          <user-card :chessType="'黑方'"
                     :gametime="1200"
                     :steptime="180"
                     :username="this.BoardValue.rePlayInform.gameInform.blackUserInform.name"
                     :isReady="true"
                     :style="upStyle"></user-card>
        </el-row>
        <el-row :gutter="24" style="margin-top: 100px;">
          <user-card :chessType="'红方'"
                     :gametime="1200"
                     :steptime="180"
                     :username="this.BoardValue.rePlayInform.gameInform.redUserInform.name"
                     :isReady="true"
                     :style="downStyle"></user-card>
        </el-row>
      </el-col>
      <el-col :span="10" :offset="2">
        <chess style="height: 716px;width: 616px;margin-top: 10px"></chess>
      </el-col>

      <el-col :span="4" :offset="4">
        <el-row>
          <el-button v-if="!isPlay"  icon="iconfont icon-bofang"   @click="playGame" type="success" style="width: 100%"> 播放</el-button>
          <el-button v-else icon="iconfont icon-stop"   @click="stopGame" type="warning" style="width: 100%"> 暂停</el-button>
        </el-row>

        <el-row>
          <el-container>
            <el-main style="height:650px;background-color: #EEEEEE">
              <el-col >
                <el-steps direction="vertical" :active="activeStep" >
                  <template v-for="item1 in stepSize">
                    <el-step :title="'第'+item1+'步'" icon="el-icon-caret-bottom"></el-step>
                  </template>

                </el-steps>
              </el-col>
            </el-main>
          </el-container>
        </el-row>

        <el-row>
          <el-button  @click="goOut"  type="danger" style="width: 100%">退出</el-button>
        </el-row>
      </el-col>


    </el-row>
  </div>

</template>

<script>
    import Chess from "./chess";
    import UserCard from "./userCard";
    export default {
        name: "rePlay",

      data(){
          return{
            isRed:true,
            value2:'50',
            isPlay:false,
            activeStep:0,
            rePlayInform:this.BoardValue.rePlayInform,
            timer:''
          }
      },
      methods:{
        stopGame(){
          let vum=this;
          this.isPlay=false;

        },
        playGame(){
          let vum=this;
          this.isPlay=true;

        },

          goOut(){
            var oldisplay=this.isPlay;
            var vum=this;
            vum.stopGame();

            this.$confirm('是否退出?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {

              vum.BoardValue.dialogTableVisible.data1=false;
              vum.activeStep=0;
              vum.BoardValue.mapinf.initGame();

              this.$message({
                type: 'success',
                message: '成功退出'
              });
            }).catch(() => {
              if(oldisplay){
                vum.playGame();
              }
              else {
                vum.stopGame();
              }

              this.$message({
                type: 'info',
                message: '已取消'
              });
            });
          },
          setChess(vum){

            let chessNum= vum.rePlayInform.stepInform[vum.activeStep].chessNum;
            let x= vum.rePlayInform.stepInform[vum.activeStep].pointX;
            let y= vum.rePlayInform.stepInform[vum.activeStep].pointY;
            vum.BoardValue.mapinf.setXY(chessNum,x,y);
            vum.isRed=!vum.isRed;
            vum.activeStep++;
          }
      },
      computed:{
          upStyle(){
            if(!this.isRed){
              return {backgroundColor:'black',opacity:0.8}
            }
          },
        downStyle(){
          if(this.isRed){
            return {backgroundColor:'red',opacity:0.8}

          }

        },
          stepSize(){
            return this.rePlayInform.stepInform.length;
          }
      },
      watch:{

        isPlay:function (val, oldVal) {
          let vum=this;
          if(vum.isPlay){
            vum.timer=setInterval(function () {
              if(vum.activeStep==vum.stepSize){
                vum.BoardValue.mapinf.initGame();
                vum.activeStep=0;

              }
              else {
                vum.setChess(vum);
              }

              if(vum.activeStep==vum.stepSize){
                vum.isPlay=false;
                clearInterval(vum.timer);
              }
            },1500);
          }
          else {
            clearInterval(vum.timer);
          }
        }
      },
      components: {UserCard, Chess}
    }
</script>

<style >
  .el-step__icon{
    background-color: transparent;
  }
</style>
