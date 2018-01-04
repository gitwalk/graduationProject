//管理界面
<template>
      <el-container style="height: 730px;background-color:#2E363F;">
        <el-header height="12%" style="background-color:black">
          <mheader></mheader>
        </el-header>
        <el-container >
          <el-aside width="15%" >
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

</style>
