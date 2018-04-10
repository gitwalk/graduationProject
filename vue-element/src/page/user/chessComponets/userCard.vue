<template>
  <div >
    <el-card class="box-card" style="background-color: transparent">
      <div slot="header" class="clearfix">
        <span>{{chessType}}</span>
        <span>{{ready}}</span>
      </div>
      <div class="text item">
        {{'昵称： ' + username }}
      </div>
      <div class="text item">
        {{'步时： ' + steptime1 }}
      </div>
      <div class="text item">
        {{'局时： ' + gametime1 }}
      </div>

    </el-card>

  </div>
</template>

<script>
    export default {
        name: "userCard",
     methods:{
       settime(timer){
         let s=timer%60;
         let m=timer/60;
         m=parseInt(m);
         if(s<10) s='0'+s;
         if(m<10) m='0'+m;
         return (m+':'+s);
       }
     },
     computed:{
       ready(){
         if(this.username==""){
            return "未加入"
         }
         return (this.isReady?"已准备":"未准备");
       },
       steptime1(){
         return this.settime(this.steptime);
       },
       gametime1(){
         return this.settime(this.gametime);
       }
     },
      props: ['chessType','username','steptime','gametime','isReady']
    }
</script>

<style scoped>
  .text {
    font-size: 20px;
    color: #EEEEEE;
  }

  .item {
    margin-bottom: 18px;

  }
  .clearfix{
    color: #EEEEEE;
    font-size: 20px;
  }
  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";

  }
  .clearfix:after {
    clear: both
  }


</style>
