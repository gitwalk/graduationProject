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

      <el-menu-item index="/gameLobby" >游戏大厅</el-menu-item>

      <el-menu-item index="/Record" >游戏记录</el-menu-item>
      <el-menu-item index="/userInform">基本信息</el-menu-item>
      <el-menu-item index="#" style="color: white;font-size: 17px;float: right;margin-right: 20px">
        <el-dropdown style="color: white" @command="handleCommand">
        <span class="el-dropdown-link" >
          欢迎，{{this.username}}
        </span>
          <el-dropdown-menu slot="dropdown" >
            <el-dropdown-item command="a">修改密码</el-dropdown-item>

            <el-dropdown-item divided command="b">退出</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-menu-item>
    </el-menu>
    <el-dialog title="修改密码" :visible.sync="dialogVisible1" width="30%" :before-close="handleClose">
      <el-form :model="ruleForm2" status-icon :rules="rules2" ref="ruleForm2" label-width="100px" class="demo-ruleForm">
        <el-form-item label="原密码" prop="oldPassWord">
          <el-input type="password" v-model="ruleForm2.oldPassWord" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="passWord">
          <el-input type="password" v-model="ruleForm2.passWord" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPassword">
          <el-input type="password" v-model="ruleForm2.checkPassword" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm('ruleForm2')">提交</el-button>
        <el-button  @click="resetForm('ruleForm2')">重置</el-button>
      </span>
    </el-dialog>
  </div>

</template>

<script>
    export default {
        name: "Uheader",
      data() {
        var validatePass = (rule, value, callback) => {
          if (value === '') {
            callback(new Error('请输入原密码'));
          } else {
            if (this.ruleForm2.oldPassWord !== '') {
              let dynamicValidateForm={
                'password':this.ruleForm2.oldPassWord
              };

              var jsonData = JSON.stringify(dynamicValidateForm);
              this.$axios({
                method: 'post',
                headers: {
                  'Content-type': 'application/json;charset=UTF-8'},
                withCredentials : true,
                url: '/ssm/user/checkPassWord',
                data: jsonData
              }).then(function (response){
                if(response.data=="error"){
                  callback(new Error('原密码错误'));
                  var a=0;
                }else {
                  callback();
                }
              }).catch(function (error) {
                console.log(error);
              });
            }

          }
        };
        var validatePass2 = (rule, value, callback) => {
          if (value === '') {
            callback(new Error('新密码不能为空'));
          } else if (this.ruleForm2.oldPassWord === value){
            callback(new Error('新密码不能与原密码相同'));
          }else {
            callback();
          }
        };
        var validatePass3 = (rule, value, callback) => {
          if (value === '') {
            callback(new Error('请再次输入密码'));
          } else if (value !== this.ruleForm2.passWord) {
            callback(new Error('两次输入密码不一致!'));
          } else {
            callback();
          }
        };
        return {
          ruleForm2:{
            oldPassWord:'',
            passWord:'',
            checkPassword:''
          },
          rules2: {
            oldPassWord: [
              { validator: validatePass, trigger: 'blur' }
            ],
            passWord: [
              { validator: validatePass2, trigger: 'blur' }
            ],
            checkPassword: [
              { validator: validatePass3, trigger: 'blur' }
            ]
          },
          dialogVisible1:false,
          activeIndex: '1',
          activeIndex2: '',
          username:''
        };
      },
      mounted(){
        this.activeIndex2=this.$route.path;
          var thisVue=this;
        this.$axios({
          method: 'post',
          headers: {
            'Content-Type': 'application/json;charset=UTF-8',
          },
          withCredentials : true,
          url: '/ssm/user/getuserInform',//listUserInform
        }).then(function (response) {
            if(response.data!="error"){
            console.log(response.data);
            thisVue.BoardValue.onlineUser.userInform=response.data;
            thisVue.username=response.data.name;
          }
        })
          .catch(function (error) {
            console.log(error);
          });
      },
      methods: {
        resetForm(formName) {
          this.$refs[formName].resetFields();
          this.checkPassword="";
        },
        handleClose(done) {
          this.$confirm('确认关闭？')
            .then(_ => {
              done();
            })
            .catch(_ => {});
        },
        submitForm(formName) {
          this.$refs[formName].validate((valid) => {
            if (valid) {
              let thisVue=this;
              let data={
                'password':this.ruleForm2.passWord,
              }
              var jsonData = JSON.stringify(data);
              thisVue.$axios({
                method: 'post',
                headers: {
                  'Content-Type': 'application/json;charset=UTF-8',
                },
                withCredentials : true,
                url: '/ssm/user/updataUserPassword',//listUserInform
                data:jsonData
              }).then(function (response) {
                if(response.data==''){
                  thisVue.$alert('请重新登录', '消息', {
                    confirmButtonText: '确定',
                    callback: action => {
                      if(thisVue.BoardValue.ws!=null){
                        thisVue.BoardValue.ws.close();
                      }
                      thisVue.$router.push('/gamePlay');
                    }
                  });
                }
                else if(response.data!="error"&&response.data>0){
                  thisVue.dialogVisible1 = false;
                  thisVue.$axios({
                    method: 'post',
                    headers: {
                      'Content-Type': 'application/json;charset=UTF-8',
                    },
                    withCredentials : true,
                    url: '/ssm/logoutUserInfrom',//listUserInform

                  }).then(function (response) {
                    thisVue.BoardValue.ws.close();
                    thisVue.$router.push('/gamePlay');
                  })
                    .catch(function (error) {
                    });
                  thisVue.$notify({
                    title: '修改成功',
                    message: '请重新登录',
                    type: 'success'
                  });
                }

              })
                .catch(function (error) {
                  console.log(error);
                });


            } else {
              console.log('error submit!!');
              return false;
            }
          });

        },
        handleCommand(command) {
          let thisVue=this;
          if(command=="a"){
            this.dialogVisible1 = true;

          }else if(command=="b"){
            thisVue.$axios({
              method: 'post',
              headers: {
                'Content-Type': 'application/json;charset=UTF-8',
              },
              withCredentials : true,
              url: '/ssm/logoutUserInfrom',//listUserInform

            }).then(function (response) {
              thisVue.$alert('退出成功', '消息', {
                confirmButtonText: '确定',
                callback: action => {
                  thisVue.BoardValue.ws.close();
                  thisVue.$router.push('/gamePlay');
                }
              });
            })
              .catch(function (error) {
              });

          }
        },
        handleSelect(key, keyPath) {
          console.log(key, keyPath);
        }
      }
    }
</script>

<style scoped>

</style>
