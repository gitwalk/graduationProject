//页面侧边栏
<template>
  <div >
    <el-row class="tac" >
      <el-col :span="24">
        <el-menu router    default-active="userInformstatistics" class="el-menu-vertical-demo" @open="handleOpen"
                 @close="handleClose" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
          <el-menu-item index="userInformstatistics">
            <i class="el-icon-tickets"></i>
            <span slot="title">用户信息统计</span>
          </el-menu-item>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-setting"></i>
              <span>账号管理</span>
            </template>
            <el-menu-item-group >
              <el-menu-item index="userAccountmanage">用户账号管理</el-menu-item>
              <el-menu-item index="adminAccountmanage">管理员账号管理</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-menu-item index="gamesInform">
            <i class="el-icon-info"></i>
            <span slot="title">对局信息</span>
          </el-menu-item>
        </el-menu>
      </el-col>
    </el-row>
  </div>
</template>

<script>
    export default {
        name: "sidebar",
      methods: {
        handleOpen(key, keyPath) {
          console.log(key, keyPath);
        },
        handleClose(key, keyPath) {
          console.log(key, keyPath);
        }
      }
    }
</script>

<style scoped>
  /*.sidebar{*/
    /*display: block;*/
    /*position: absolute;*/
    /*width: 250px;*/
    /*left: 0;*/
    /*top: 70px;*/
    /*bottom:0;*/
    /*background: #2E363F;*/
  /*}*/
  /*.sidebar > ul {*/
    /*height:100%;*/
  /*}*/
</style>
