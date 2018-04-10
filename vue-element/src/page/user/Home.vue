<template>
  <div style="height: 100%">
    <div id="div1">
      <img src="../../../static/img/background1.jpg" />
    </div>
    <el-container >
      <el-header style="width: 900px;margin-left: 20%;padding: 0">
        <Uheader></Uheader>
      </el-header>
      <el-main style="width: 900px;margin-left: 20%;padding-bottom: 0">
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>



</template>

<script>
  import Uheader from './Uheader.vue';


    export default {
        name: "Home",
      components:{
        Uheader
      }
    }
</script>

<style scoped>
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
