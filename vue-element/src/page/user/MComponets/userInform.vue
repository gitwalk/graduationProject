<template>
<div style="background-color: rgba(252,252,252,0.5);height: 626px">
  <el-row :gutter="20">
    <el-col :span="15" :offset="7" >

      <div class=" person-info-agileits-w3layouts" style="padding:0px 0 0 0;">

        <ul>
          <li><span>昵称</span>:{{ruleForm.name}}</li>

          <li><span>注册日期</span>:{{ruleForm.registerTime}}</li>
          <li><span>登录次数</span>: {{ruleForm.loginNum}}次</li>
          <li><span>在线时长</span>: {{ruleForm.allLoginTime}}</li>
          <li><span>对局次数</span>: {{ruleForm.gameNum}}次</li>
          <li><span>电子邮件</span>:
            <a href="mailto:info@example.com">{{ruleForm.emailAddress}}</a>
          </li>
          <li>
            <span>性别</span>:
            <el-radio-group v-model="ruleForm.sex">
            <el-radio :label="0">男</el-radio>
            <el-radio :label="1">女</el-radio>
            <el-radio :label="2">保密</el-radio>
          </el-radio-group></li>


          <li><span>胜率</span>: <el-progress :width="100" color="green" type="circle" :percentage="ruleForm.winningRate"></el-progress></li>
          <li><a href="javascript:void(0)"  @click="submitForm()" class="botton-w3ls" style="margin-left: 100px">保存</a>

          </li>
        </ul>
      </div>
    </el-col>

  </el-row>
</div>
</template>

<script>
    export default {
        name: "userInform",
      data() {
        return {

          ruleForm: {
            name:'',//昵称
            emailAddress:'',//电子邮件
            sex:'0',//性别
            registerTime:'',//注册时间
            loginNum:'',//登录次数
            gameNum:'',//对局次数
            allLoginTime:'',//在线时长
            winningRate:''//胜率

          }

        };
      },
      mounted(){

        var vum=this;
        this.$axios({
          method: 'post',
          headers: {
            'Content-Type': 'application/json;charset=UTF-8',
          },

          withCredentials : true,
          url: '/ssm/user/selectUserInform',//listUserInform

        }).then(function (response) {
            if(response.data==""){
              vum.$alert('请重新登录', '消息', {
                confirmButtonText: '确定',
                callback: action => {
                  if(vum.BoardValue.ws!=null){
                    vum.BoardValue.ws.close();
                  }
                  vum.$router.push('/gamePlay');
                }
              });
            }
            else if(response.data!="error"){
              vum.ruleForm.name= response.data[0].name;
              vum.ruleForm.emailAddress= response.data[0].emailAddress;
              vum.ruleForm.sex= response.data[0].sex;
              vum.ruleForm.registerTime= response.data[0].registerTime.split(' ')[0];
              vum.ruleForm.loginNum= response.data[0].loginNum;
              vum.ruleForm.gameNum= response.data[0].gameNum;
              vum.ruleForm.allLoginTime= vum.$formatDuring(response.data[0].allLoginTime);
              vum.ruleForm.winningRate= (response.data[0].winningRate*100).toFixed(2);

            }

        })
          .catch(function (error) {
            console.log(error);
          });
      },
      methods: {
        submitForm() {
          let data={
            'sex':this.ruleForm.sex
          };
          var jsonData = JSON.stringify(data);
          var vum=this;
          this.$axios({
            method: 'post',
            headers: {
              'Content-Type': 'application/json;charset=UTF-8',
            },

            withCredentials : true,
            url: '/ssm/user/updataUsersex',//listUserInform
            data:jsonData
          }).then(function (response) {
            if(response.data==""){
              vum.$alert('请重新登录', '消息', {
                confirmButtonText: '确定',
                callback: action => {
                  if(vum.BoardValue.ws!=null){
                    vum.BoardValue.ws.close();
                  }
                  vum.$router.push('/gamePlay');
                }
              });
            }
            else if(response.data!="error"&&response.data>0){
              vum.$message({
                message: '保存成功',
                type: 'success'
              });

            }

          })
            .catch(function (error) {
              console.log(error);
            });
        }

      }
    }
</script>

<style scoped src="../../../../static/css/style2.css" />
<style scoped>
 li{
   margin-top: 30px;
 }
</style>
