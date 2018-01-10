//管理界面
<template>

      <el-container class="wrapper" style="background-color:#2E363F;">
        <el-header height="90px" style="background-color:black">
          <mheader></mheader>
        </el-header>
        <el-container >
          <el-aside width="230px" >
            <sidebar></sidebar>
          </el-aside >
          <el-main style="background-color:white;">
            <router-view></router-view>
          </el-main>
        </el-container>
      </el-container>

</template>

<script>
  import Mheader from './HomeComponents/Mheader';
  import Sidebar from './HomeComponents/Sidebar';

    export default {
        name: "home",
      components:{
        Mheader,Sidebar
           }
    }
</script>

<style scoped>
  .wrapper{
    height: 100%;
  }
</style>
