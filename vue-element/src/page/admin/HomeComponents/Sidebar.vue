//页面侧边栏
<template>
  <div >
    <el-row class="tac" >
      <el-col :span="24">
        <el-menu router   :default-active="getRoutePath()" class="el-menu-vertical-demo" @open="handleOpen"
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
              <el-menu-item v-show="dynamicValidateForm.role==2" index="adminAccountmanage">管理员账号管理</el-menu-item>
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
      data(){
        return{
          dynamicValidateForm: {
            id:'',
            name:'',
            role:''
          },
        }
      },
      mounted(){
        this.logInOrOut(this,"/getSession","getSession");
      },
      methods: {
        logInOrOut(thisVue,api,callback){
          this.$axios({
            method: 'post',
            headers: {
              'Content-type': 'application/json;charset=UTF-8'
            },
            withCredentials : true,
            url: '/ssm/admin'+api,

          }).then(function (response) {
            console.log(response);
            if(callback=="getSession"){
              if(response.data=="error"){
                thisVue.$alert('请重新登录', '消息', {
                  confirmButtonText: '确定',
                  callback: action => {
                    thisVue.$router.push('/');
                  }
                });

              }else {
                thisVue.dynamicValidateForm.id=response.data[0].id;
                thisVue.dynamicValidateForm.name=response.data[0].name;
                thisVue.dynamicValidateForm.role=response.data[0].role;

              }
            }
          })
            .catch(function (error) {
              console.log(error);
            });
        },
        getRoutePath(){
          var path=this.$route.path.split("/");
          return path[1];
        },
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

</style>
