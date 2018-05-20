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
      methods:{
        outInform(response){
          if(response.data==""){

            this.$alert('请重新登录', '消息', {
              confirmButtonText: '确定',
              callback: action => {
                this.$router.push('/gamePlay');
              }
            });
          }
        },
        togameLobby(){
          var jsonData = JSON.stringify( this.BoardValue.onlineUser);
          var vum=this;
          this.$axios({
            method: 'post',
            headers: {
              'Content-Type': 'application/json;charset=UTF-8',
            },

            withCredentials : true,
            url: '/ssm/user/togameLobby',//listUserInform
            data: jsonData,
          }).then(function (response) {

            // vum.outInform(response);
          })
            .catch(function (error) {
              console.log(error);
            });
        }
      },
      created(){

        this.BoardValue.ws = new WebSocket("ws://"+this.Ipaddress.ipdata+"/ssm/myHandler");
        this.BoardValue.ws.onopen=function () {
          console.log("连接打开");

        }
        this.BoardValue.ws.onclose = function () {
          // alert("自动关闭了");
        }
        if(this.$route.path!='/gameLobby'){
          this.togameLobby();
        }

      },
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
