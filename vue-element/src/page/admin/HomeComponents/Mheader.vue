//页面头部
<template>
  <div>
    <h1 style="color: white;font-size:30px;margin: 0;padding-left: 20px"> 信息管理系统</h1>
    <span style="color: white;font-size: 17px;margin-right: 20px;float: right">
     {{role}}
    </span>
    <el-dropdown style="color: white;font-size: 17px;float: right;margin-right: 20px" @command="handleCommand">
    <span class="el-dropdown-link" style="color: #2ae6d8">
      欢迎，{{dynamicValidateForm.name}}
    </span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item command="a">修改密码</el-dropdown-item>
        <el-dropdown-item  v-if="dynamicValidateForm.role==2" divided command="b">添加普通管理员</el-dropdown-item>
        <el-dropdown-item divided command="c">退出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>

    <el-dialog title="添加管理员" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
      <el-form :model="ruleForm3" status-icon :rules="rules3" ref="ruleForm3" label-width="100px" class="demo-ruleForm">
        <el-form-item label="账号" prop="name">
          <el-input type="name" v-model="ruleForm3.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="真实姓名" prop="realName">
          <el-input type="name" v-model="ruleForm3.realName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="ruleForm3.password" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPassword">
          <el-input type="password" v-model="checkPassword" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm1('ruleForm3')">提交</el-button>
        <el-button  @click="resetForm('ruleForm3')">重置</el-button>
      </span>
    </el-dialog>




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
    name: "Mheader",
    data() {
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入原密码'));
        } else {
          if (this.ruleForm2.oldPassWord !== '') {
            this.dynamicValidateForm.password=this.ruleForm2.oldPassWord;

            var jsonData = JSON.stringify(this.dynamicValidateForm);
            this.$axios({
              method: 'post',
              headers: {
                'Content-type': 'application/json;charset=UTF-8'},
              withCredentials : true,
              url: '/ssm/loginAdminInfrom',
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
      var validatePass4 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('账号不能为空'));
        } else {

          var jsonData = JSON.stringify(this.ruleForm3);
          this.$axios({
            method: 'post',
            headers: {
              'Content-type': 'application/json;charset=UTF-8'},
            withCredentials : true,
            url: '/ssm/admin/checkAdminName',
            data: jsonData
          }).then(function (response){
            if(response.data=="error"){
              callback(new Error('账号已存在'));
              var a=0;
            }else {
              callback();
            }
          }).catch(function (error) {
            console.log(error);
          });

        }
      };
      var validatePass5 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('真实姓名不能为空'));
        } else {
          callback();
        }
      };
      var validatePass6 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('密码不能为空'));
        } else {
          callback();
        }
      };
      var validatePass7 = (rule, value, callback) => {
        if (this.checkPassword === '') {
          callback(new Error('请再次输入密码'));
        } else if (this.checkPassword !== this.ruleForm3.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        rules2: {
          oldPassWord: [
            { validator: validatePass, trigger: 'blur' },
            { min: 6, max: 13, message: '长度在 6 到 13 个字符', trigger: 'blur' }

          ],
          passWord: [
            { validator: validatePass2, trigger: 'blur' },
            { min: 6, max: 13, message: '长度在 6 到 13 个字符', trigger: 'blur' }
          ],
          checkPassword: [
            { validator: validatePass3, trigger: 'blur' },
            { min: 6, max: 13, message: '长度在 6 到 13 个字符', trigger: 'blur' }
          ]
        },
        rules3: {
          name: [
            { validator: validatePass4, trigger: 'blur' },
            { min: 6, max: 13, message: '长度在 6 到 13 个字符', trigger: 'blur' }
          ],
          realName: [
            { validator: validatePass5, trigger: 'blur' },
            { min: 6, max: 13, message: '长度在 6 到 13 个字符', trigger: 'blur' }
          ],
          password: [
            { validator: validatePass6, trigger: 'blur' },
            { min: 6, max: 13, message: '长度在 6 到 13 个字符', trigger: 'blur' }
          ],
          checkPassword: [
            { validator: validatePass7, trigger: 'blur' },
            { min: 6, max: 13, message: '长度在 6 到 13 个字符', trigger: 'blur' }
          ]
        },
        dialogVisible1:false,
        dialogVisible: false,
        ruleForm3:{
          password:'',
          realName:'',
          name:''
        },
        ruleForm2:{
          oldPassWord:'',
          passWord:'',
          checkPassword:''
        },
        dynamicValidateForm: {
          id:'',
          name:'',
          password:'',
          role:''
        },
        role:'',
        checkPassword:''
      }
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.dynamicValidateForm.password=this.ruleForm2.passWord;
            var jsonData = JSON.stringify(this.dynamicValidateForm);
            this.$updataInform(jsonData,"/admin/updateAdminInfrom","",this,"updataAdminPassWord");
            this.dialogVisible1 = false;

          } else {
            console.log('error submit!!');
            return false;
          }
        });

      },
      submitForm1(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {

            var jsonData = JSON.stringify(this.ruleForm3);
            this.$updataInform(jsonData,"/admin/addAdminInfrom","",this,"addAdminInfrom");
            this.dialogVisible = false;

          } else {
            console.log('error submit!!');
            return false;
          }
        });

      },
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
              if(response.data[0].role=="1"){
                thisVue.role="普通管理员";
              }else if(response.data[0].role=="2"){
                thisVue.role="超级管理员";
              }
            }
          }else if(callback=="logoutAdminInfrom"){
            thisVue.$alert('退出成功', '消息', {
              confirmButtonText: '确定',
              callback: action => {
                thisVue.$router.push('/');
              }
            });

          }else if(callback=="updataAdminPassWord"){

            thisVue.$alert('修改成功', '消息', {
              confirmButtonText: '确定',
              callback: action => {
                thisVue.$message({
                  type: 'info',
                  message: '请重新登录'
                });
                thisVue.$router.push('/');
              }
            });
          }

        })
          .catch(function (error) {
            console.log(error);
          });
      },
      handleCommand(command) {
        if(command=="a"){
          this.dialogVisible1 = true;

        }else if(command=="b"){
          this.dialogVisible = true;

        }else if(command=="c"){
          this.logInOrOut(this,"/logoutAdminInfrom","logoutAdminInfrom");


        }
      }
    },
    mounted(){
      this.logInOrOut(this,"/getSession","getSession");
    }

  }
</script>

<style scoped>

</style>
