<template>
  <div  style="width: 1350px">
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
        <el-row>
            <chess style="height: 716px;width: 616px;margin-top: 10px"></chess>
        </el-row>
      </el-col>

      <el-col :span="4" :offset="3">
        <el-row>
          <el-button v-if="!isPlay"  icon="iconfont icon-bofang"   @click="playGame" type="success" style="width: 100%"> 播放</el-button>
          <el-button v-else icon="iconfont icon-stop"   @click="stopGame" type="warning" style="width: 100%"> 暂停</el-button>
        </el-row>

        <el-row>
          <el-container>
            <el-main style="height:610px;background-color: #EEEEEE">
              <el-col >
                <el-steps  direction="vertical" :active="activeStep" >
                  <el-step style="cursor:pointer" :data="0"  :title="'开局'" :icon="(activeStep==0)?'el-icon-caret-right':'el-icon-caret-bottom'"></el-step>

                  <template   v-for="item1 in stepSize">
                      <el-step style="cursor:pointer" v-if="item1!=stepSize" :data="item1" @click="chooseStep(item1)"  :title="'第'+item1+'步'" :icon="(activeStep==item1)?'el-icon-caret-right':'el-icon-caret-bottom'"></el-step>
                      <el-step style="cursor:pointer" v-else :data="item1" @click="chooseStep(item1)"  :title="'第'+item1+'步(结束)'" :icon="(activeStep==item1)?'el-icon-caret-right':'el-icon-caret-bottom'"></el-step>
                  </template>

                </el-steps>
              </el-col>
            </el-main>
          </el-container>
        </el-row>
        <el-row style="background-color:#EEEEEE;padding-bottom: 3px">
          <el-col :span="9" :offset="1" style="margin-top: 5px">
            <span  style="color: black;">播放速度：</span>

          </el-col>
          <el-col :span="1">
            <el-input-number  style="width: 100px;"  :controls="false" @focus="focusSpeed"   :min="speedV.min" :max="speedV.max" v-model="speedV.value"  :step="speedV.step" size="small" >
            </el-input-number>
          </el-col>

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

            speedV:{
              max:2.5,
              min:0.5,
              step:0.1,
              value:1
            },
            oldIsplay:false,
            isRed:true,
            value2:'50',
            isPlay:false,
            activeStep:0,
            rePlayInform:this.BoardValue.rePlayInform,
            timer:'',
            dialogTableVisible:this.BoardValue.dialogTableVisible
          }
      },
      mounted(){
        this.setClick();
      },
      methods:{
        focusSpeed(value){
          this.oldIsplay=this.isPlay;
          this.stopGame();
        },
        blurSpeed(value){

          if(this.oldIsplay){
            this.playGame();
          }

        },
        setClick(){

            let vm=this;
            $(document).ready(function(){
              $(".el-step.is-vertical").click(function(){
                let data=this.getAttribute("data");
                data=parseInt(data);
                vm.chooseStep(data);

              });
            });
          },
        stopGame(){
          let vum=this;
          this.isPlay=false;

        },
        playGame(){
          let vum=this;
          this.isPlay=true;

        },
        chooseStep(data){
          this.stopGame();
          this.activeStep=parseInt(data);
          this.BoardValue.mapinf.initGame();
          for(var i=0;i<data;++i){
            let chessNum= this.rePlayInform.stepInform[i].chessNum;
            let x= this.rePlayInform.stepInform[i].pointX;
            let y= this.rePlayInform.stepInform[i].pointY;
            this.BoardValue.mapinf.setXYInform(chessNum,x,y);

          }
          if(data==0){
            this.isRed=true;
          }else if(data%2==1){
            this.isRed=true;
          }
          else{
            this.isRed=false;
          }
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
              vum.isRed=true;
              vum.speedV.value=1;
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

            if(chessNum%2==1){
              vum.isRed=true;
            }
            else{
              vum.isRed=false;
            }
            vum.activeStep++;

          }
      },
      computed:{
          playSpeed(){
            return (1500/this.speedV.value);
          },
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
        'rePlayInform.stepInform':function(val, oldVal){
          this.setClick();

        },
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
              console.log(vum.playSpeed);
            },vum.playSpeed);
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
