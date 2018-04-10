<template>
  <div>
    <el-menu router
             :default-active="activeIndex2"
             class="el-menu-demo"
             mode="horizontal"
             @select="handleSelect"
             background-color="#545c64"
             text-color="#fff"
             active-text-color="#ffd04b">

      <el-menu-item index="gameLobby" >游戏大厅</el-menu-item>

      <el-menu-item index="Record" >游戏记录</el-menu-item>
      <el-menu-item index="userInform">基本信息</el-menu-item>
      <el-menu-item index="#" style="color: white;font-size: 17px;float: right;margin-right: 20px">
        <el-dropdown style="color: white" @command="handleCommand">
    <span class="el-dropdown-link" >
      欢迎，aaaa
    </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="a">修改密码</el-dropdown-item>

            <el-dropdown-item divided command="c">退出</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-menu-item>
    </el-menu>

  </div>

</template>

<script>
    export default {
        name: "Uheader",
      data() {
        return {
          activeIndex: '1',
          activeIndex2: 'gameLobby',
          handleCommand:''
        };
      },
      methods: {
        handleSelect(key, keyPath) {
          console.log(key, keyPath);
        }
      }
    }
</script>

<style scoped>

</style>
