<template>

  <div>
    <div id="div1">
      <img src="../../../static/img/login.png" />
    </div>

    <div style="margin-left: 1000px;width: 350px;padding-top: 120px ">
<!-------------------------登录框------------------------------------------------------------------------>

          <div v-if="typeNum==1" class="signin-form-grid" >
            <div class="signin-form" >
              <h2>用户登录</h2>
              <el-form :rules="rules1" status-icon :model="dynamicValidateForm" status-icon ref="dynamicValidateForm"  class="demo-ruleForm">
                <el-form-item prop="name">
                  <input @keyup.enter="submit('dynamicValidateForm')" type="text" v-model.trim="dynamicValidateForm.name" name="User Name" placeholder="昵称" required="">
                </el-form-item>
                <el-form-item  prop="password">
                  <input @keyup.enter="submit('dynamicValidateForm')" type="password" v-model.trim="dynamicValidateForm.password" name="Password" placeholder="密码" required="">

                </el-form-item>
                <el-form-item  prop="mark">
                  <el-row>
                    <el-col :span="12">
                      <input  @keyup.enter="submit('dynamicValidateForm')" type="text" v-model.trim="dynamicValidateForm.mark" name="mark" placeholder="验证码" required="" >

                    </el-col>
                    <el-col :span="10" :offset="2">
                      <mark-img></mark-img>
                    </el-col>
                  </el-row>

                </el-form-item>

                <el-form-item>
                  <input style="margin-top: 25px"  type="button" @click="submit('dynamicValidateForm')" value="登录">
                </el-form-item>
              </el-form>

                <div class="signin-agileits-bottom" >
                  <p>
                    <a href="javascript:void(0)" style="margin-left: 0" @click="signin"> 注册新帐号</a>
                    <span style="color: #EEEEEE">|</span>
                    <a href="javascript:void(0)" style="margin-left: 0" @click="forget">忘记密码 ?</a>
                  </p>
                </div>
            </div>
            <div class="signin-firm">

            </div>
          </div>
<!-------------------------注册------------------------------------------------------------------------>
          <div  v-if="typeNum==2" class="signin-form signup-form" style="margin-top: 0">
            <h3>免费注册</h3>

              <el-form  :model="ruleForm2" status-icon :rules="rules2" ref="ruleForm2"  class="demo-ruleForm">
                <el-form-item  prop="name">
                  <div class="el-input">
                    <input @keyup.enter="submit2('ruleForm2')"  type="text" v-model.trim="ruleForm2.name" auto-complete="off" name="User Name" placeholder="昵称" />
                  </div>
                </el-form-item>

                <el-form-item  prop="pass">

                  <input  @keyup.enter="submit2('ruleForm2')" style="margin-bottom: 12.6px" type="password"  v-model.trim="ruleForm2.pass" auto-complete="off" name="Password" placeholder="密码" />
                </el-form-item>
                <el-form-item  prop="checkpass">
                  <input style="margin-bottom: 12.6px" type="password" v-model.trim="ruleForm2.checkpass" auto-complete="off" name="rest Password" placeholder="确认密码" />
                </el-form-item>
                <el-form-item  prop="email">
                  <input @keyup.enter="submit2('ruleForm2')"   type="text" v-model.trim="ruleForm2.email" auto-complete="off"   name="Email" placeholder="电子邮件" />
                </el-form-item>
                <el-form-item  prop="mark">
                  <el-row>
                    <el-col :span="10">
                      <input  @keyup.enter="submit2('ruleForm2')" type="text" v-model.trim="ruleForm2.mark" name="mark" placeholder="邮箱验证码" required="" >
                    </el-col>
                    <el-col :span="11" :offset="3">
                      <el-button :disabled="buttonContent[0].disabled" icon="el-icon-message" @click="sendMail(0)" type="success"  >{{buttonContent[0].text}}</el-button>
                    </el-col>
                  </el-row>
                </el-form-item>
                <el-form-item  >
                  <input  type="button" value="提交" @click="submit2('ruleForm2')"   />
                </el-form-item>
              </el-form>
              <div class="signin-agileits-bottom">
                <p><a href="javascript:void(0)" @click="signout"><i class="fa fa-arrow-left"  aria-hidden="true"></i> 返回登录</a></p>
              </div>

          </div>

<!-------------------------忘记密码------------------------------------------------------------------------>
          <div  v-if="typeNum==3" class="signin-form signup-form" style="margin-top: 0">
            <h3>密码找回</h3>

            <el-form  :model="ruleForm3" ref="ruleForm3" :rules="rules4" class="demo-dynamic">
              <el-form-item prop="name">
                <input  @keyup.enter="nextStep('ruleForm3')"  type="text"  v-model.trim="ruleForm3.name"  name="User Name"  placeholder="昵称"  />
              </el-form-item>
              <el-form-item  prop="emailAddress">
                <input @keyup.enter="nextStep('ruleForm3')" type="email" v-model.trim="ruleForm3.emailAddress" name="Email" placeholder="电子邮件"  />
              </el-form-item>
              <el-form-item  prop="mark">
                <el-row>
                  <el-col :span="10">
                    <input  @keyup.enter="nextStep('ruleForm3')" type="text" v-model.trim="ruleForm3.mark" name="mark" placeholder="邮箱验证码" required="" >
                  </el-col>
                  <el-col :span="11" :offset="3">
                    <el-button :disabled="buttonContent[1].disabled" icon="el-icon-message" @click="sendMail(1)" type="success"  >{{buttonContent[1].text}}</el-button>
                  </el-col>
                </el-row>
              </el-form-item>
              <el-form-item  >
                <input type="button" @click="nextStep('ruleForm3')"  value="下一步" style="margin-top: 30px" />
              </el-form-item>
            </el-form>
              <div class="signin-agileits-bottom">
                <p><a href="javascript:void(0)" @click="signout"><i class="fa fa-arrow-left"  aria-hidden="true"></i> 返回登录</a></p>
              </div>
          </div>
<!-------------------------密码重置------------------------------------------------------------------------>
          <div v-if="typeNum==4" class="signin-form reset-password" style="margin-top: 0">
            <h3>密码重置</h3>

            <el-form  :model="ruleForm3" :rules="rules3" status-icon  ref="ruleForm3"  class="demo-ruleForm">
              <el-form-item  prop="password">
                <input @keyup.enter="submit3('ruleForm3')" style="margin-bottom: 12.6px" type="password" v-model.trim="ruleForm3.password" placeholder="新密码" name="Password" >
              </el-form-item>
              <el-form-item  prop="repassword">
                <input @keyup.enter="submit3('ruleForm3')" style="margin-bottom: 12.6px" type="password" v-model.trim="ruleForm3.repassword" placeholder="确认密码" name="Repeat Password">
              </el-form-item>
              <el-form-item  >
                <input style="margin-top: 20px" type="button" class="send" @click="submit3('ruleForm3')"  value="确认">
              </el-form-item>
            </el-form>
            <div class="signin-agileits-bottom">
              <p><a href="javascript:void(0)" @click="signout"><i class="fa fa-arrow-left"   aria-hidden="true"></i> 返回登录</a></p>
            </div>
          </div>
        </div>

  </div>
</template>


<script>
import $ from 'jquery'
import MarkImg from "./loginComponets/markImg";

  export default {
        name: "gamePlay",
    components: {MarkImg},
    data(){
      var checkName = (rule, value, callback) => {
          let userInform={
            "name":value
          }

          if(value === ''){
            return callback(new Error('请输入昵称'));
          }
          else if(this.checkInform(userInform)>0){

            return callback(new Error('该昵称已存在'));
          }
         return callback();

      };
      var checkMark1=(rule, value, callback) => {
          let vm=this;
        if(value === ''){
          return callback(new Error('请输入验证码'));
        }
        else {
          vm.checkMark(value,callback);

        }

      };
      var checkEmail = (rule, value, callback) => {
        let userInform={
          "emailAddress":value
        }
        if(value === ''){
          return callback(new Error('请输入电子邮件'));
        }
        else if(this.checkInform(userInform)>0){

          return callback(new Error('该电子邮件已存在'));
        }
        return callback();
      };
      var checkPass = (rule, value, callback) => {
          if (value === '') {
            callback(new Error('请输入密码'));
          } else {
            if (this.ruleForm2.checkpass !== '') {

              this.$refs.ruleForm2.validateField('checkpass');
            }
            callback();
          }
      };
      var checkCheckpass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.ruleForm2.pass) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };

      var checkPass1 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.ruleForm3.password !== '') {

            this.$refs.ruleForm3.validateField('repassword');
          }
          callback();
        }
      };

      var checkCheckpass1 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.ruleForm3.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };

      var checkMailmark1=(rule, value, callback) => {

        let vm=this;
        if(value === ''){
          return callback(new Error('请输入验证码'));
        }
        else {
          value=value+"#"+vm.ruleForm2.email;
          vm.checkMailmark(value,callback,"checkMailcode");
        }

      };
      var checkMailmark2=(rule, value, callback) => {

        let vm=this;
        if(value === ''){
          return callback(new Error('请输入验证码'));
        }
        else {
          value=value+"#"+vm.ruleForm3.emailAddress;
          vm.checkMailmark(value,callback,"checkMailcode1");
        }

      };
      return{
        buttonContent:[
          {
            disabled:false,
            text:'发送验证码',
          },
          {
            disabled:false,
            text:'发送验证码',
          },
        ],

        route:this.$route,
        typeNum:'1',//1是登录，2是注册，3是忘记密码，4是密码重置
        dynamicValidateForm: {
          name:'',
          password:'',
          mark:''
        },
        ruleForm2:{
          name:'',
          email:'',
          pass:'',
          checkpass:'',
          mark:''
        },
        ruleForm3:{
          name:'',
          emailAddress:'',
          password:'',
          repassword:'',
          mark:''
        },
        rules1:{
          mark: [
            { validator: checkMark1, trigger: 'blur' },

          ],
          name: [
            { required: true, message: '请输入昵称', trigger: 'blur' },
            { min: 6, max: 13, message: '长度在 6 到 13 个字符', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 6, max: 13, message: '长度在 6 到 13 个字符', trigger: 'blur' }
          ]
        },
        rules2: {
          name: [
            { validator: checkName, trigger: 'blur' },
            { min: 6, max: 13, message: '长度在 6 到 13 个字符', trigger: 'blur' }
          ],
          email: [
            { validator: checkEmail, trigger: 'blur' },
            { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }

          ],
          mark: [
            { validator: checkMailmark1, trigger: 'blur' },

          ],
          pass: [
            { validator: checkPass, trigger: 'blur' },
            { min: 6, max: 13, message: '长度在 6 到 13 个字符', trigger: 'blur' }
          ],
          checkpass: [
            { validator: checkCheckpass, trigger: 'blur' },
            { min: 6, max: 13, message: '长度在 6 到 13 个字符', trigger: 'blur' }
          ]
        },
        rules3: {
          password: [
            { validator: checkPass1, trigger: 'blur' },
            { min: 6, max: 13, message: '长度在 6 到 13 个字符', trigger: 'blur' }
          ],
          repassword: [
            { validator: checkCheckpass1, trigger: 'blur' },
            { min: 6, max: 13, message: '长度在 6 到 13 个字符', trigger: 'blur' }
          ]
        },
        rules4: {
          mark: [
            { validator: checkMailmark2, trigger: 'blur' },

          ],
          name: [

            { required: true, message: '请输入昵称', trigger: 'blur' },
            { min: 6, max: 13, message: '长度在 6 到 13 个字符', trigger: 'blur' }
          ],
          emailAddress: [
            { required: true, message: '请输入邮箱地址', trigger: 'blur' },
            { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] },

          ]
        },

      }
    },


    mounted(){
      $(document).ready(function(){
        $(".el-form").submit(function(){
          return false
        });
      });
    },
    watch:{
      typeNum:function (val,oldval) {
        if(val==1){
          this.ruleForm2.name='';
          this.ruleForm2.checkpass='';
          this.ruleForm2.email='';
          this.ruleForm2.pass='';
          this.ruleForm2.mark='';
          this.ruleForm3.name='';
          this.ruleForm3.mark='';
          this.ruleForm3.emailAddress='';
          this.ruleForm3.password='';
          this.ruleForm3.repassword='';
        }
        else {
          this.dynamicValidateForm.name='';
          this.dynamicValidateForm.password='';
          this.dynamicValidateForm.mark='';
        }
      }
    },
    methods:{
      ajaxMail(data,url){
        var jsonData =data;
        let thisVue=this;
        thisVue.$message.success('验证码已发送');

        this.$axios({
          method: 'post',
          headers: {
            'Content-Type': 'application/json;charset=UTF-8',
          },

          withCredentials : true,
          url: '/ssm/captcha/'+url,//listUserInform
          data: jsonData,
        }).then(function (response) {
          if(response.data=="success"){

          }

        })
          .catch(function (error) {
            console.log(error);
          });

      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      submit3(formName){
        this.$refs[formName].validate((valid) => {
          if (valid) {

            let data={
              'name':this.ruleForm3.name,
              'emailAddress':this.ruleForm3.emailAddress,
              'password':this.ruleForm3.password,
            }
            this.updataUserPassword(data);

          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      nextStep(formName){
        let vm=this;
        vm.$refs[formName].validate((valid) => {
          if (valid) {

            let data={
              'name':this.ruleForm3.name,
              'emailAddress':this.ruleForm3.emailAddress
            }
            if(vm.checkInform(data)>0){

              // console.log('data',this.checkInform(data))
              vm.typeNum=4;
            }
            else {
              vm.$message.error('错了哦，账号或电子邮件错误');
            }
          } else {
            console.log('error submit!!');
            return false;
          }
        });

      },
      rePassword(){
        this.typeNum=4;
      },
      forget(){
        this.typeNum=3;
      },

      signin(){
        this.typeNum=2;
      },
      signout(){

        this.typeNum=1;
      },
      signinSubmit(){

      },
      timeDown(data,countdown){

        let thisVue=this;
        if(countdown==0){
          thisVue.buttonContent[data].disabled=false;
          thisVue.buttonContent[data].text="发送验证码";
        }
        else{
          thisVue.buttonContent[data].disabled=true;
          thisVue.buttonContent[data].text="重新发送("+countdown+")";
          countdown--;
          setTimeout(function() {
            thisVue.timeDown(data,countdown);
          },1000);
        }
      },
      sendMail(data){
        let thisVue=this;
        if(data==0){
          thisVue.$refs.ruleForm2.validateField('email',(valid) => {
            if(valid==""){
              thisVue.ajaxMail(thisVue.ruleForm2.email,"sendMailcode");
              thisVue.timeDown(data,60);
            }
          });
        }
        else if(data==1){
          thisVue.$refs.ruleForm3.validateField('emailAddress',(valid) => {
            if(valid==""){
              thisVue.ajaxMail(thisVue.ruleForm3.emailAddress,"sendMailcode1");
              thisVue.timeDown(data,60);
            }
          });
        }


      },
      submit2(formName){
        let vum=this;
        this.$refs[formName].validate((valid) => {
            if (valid) {
              vum.EmailMark=true;
              let data={
                'name':vum.ruleForm2.name,
                'emailAddress':vum.ruleForm2.email,
                'password':vum.ruleForm2.pass,
              };

              vum.adduser(data);
            } else {
              console.log('error submit!!');
              return false;
            }
          });
        }
      ,
      updataUserPassword(data){
        var jsonData = JSON.stringify(data);
        let thisVue=this;
        this.$axios({
          method: 'post',
          headers: {
            'Content-Type': 'application/json;charset=UTF-8',
          },

          withCredentials : true,
          url: '/ssm/updataUserPassword',//listUserInform
          data: jsonData,
        }).then(function (response) {
          if(response.data!="error"&&response.data>0){
            thisVue.typeNum=1;
            thisVue.$notify({
              title: '修改成功',
              message: '请登录',
              type: 'success'
            });
          }
        })
          .catch(function (error) {
            console.log(error);
          });
      },
      adduser(data){
        var jsonData = JSON.stringify(data);
        let thisVue=this;
        this.$axios({
          method: 'post',
          headers: {
            'Content-Type': 'application/json;charset=UTF-8',
          },

          withCredentials : true,
          url: '/ssm/addUser',//listUserInform
          data: jsonData,
        }).then(function (response) {
          if(response.data!="error"){
            thisVue.typeNum=1;
            thisVue.$notify({
              title: '注册成功',
              message: '请登录',
              type: 'success'
            });
          }
        })
          .catch(function (error) {
            console.log(error);
          });
      }
      ,
      checkInform(data){
        var jsonData = JSON.stringify(data);
        let count=0;
        $.ajax({
          url:'http://'+this.Ipaddress.ipdata+'/ssm/checkInform',
          type:'POST', //GET
          async:false,    //或false,是否异步

          crossDomain: true,
          data:jsonData,
          timeout:5000,    //超时时间
          dataType:'json',    //返回的数据格式：
          headers: {
                'Content-Type': 'application/json;charset=UTF-8',
              },
          withCredentials : true,
          success:function(data,textStatus,jqXHR){
            count=data;
          },
          error:function(xhr,textStatus){
          },
          complete:function(){
          }
        })
        return count;
      }
      ,
      checkMailmark(data,callback,url){

        this.$axios({
          method: 'post',
          headers: {
            'Content-Type': 'application/json;charset=UTF-8',
          },

          withCredentials : true,
          url: '/ssm/captcha/'+url,//listUserInform
          data: data,
        }).then(function (response) {

          if(response.data=="error"){

            callback(new Error('验证码错误'));
          }
          else if(response.data=="timeout"){
            callback(new Error('验证码超时'));
          }
          else if(response.data=="success"){
            callback();
          }

        })
          .catch(function (error) {
            callback(new Error('服务器错误'));
            console.log(error);
          });
      },
      checkMark(data,callback){

        this.$axios({
          method: 'post',
          headers: {
            'Content-Type': 'application/json;charset=UTF-8',
          },

          withCredentials : true,
          url: '/ssm/captcha/loginMark',//listUserInform
          data: data,
        }).then(function (response) {

          if(response.data=="error"){

            callback(new Error('验证码错误'));
          }
          else {
            callback();
          }

        })
          .catch(function (error) {
            console.log(error);
          });

      },
      userLogin(){
        var jsonData = JSON.stringify(this.dynamicValidateForm);
        let thisVue=this;
        this.$axios({
          method: 'post',
          headers: {
            'Content-Type': 'application/json;charset=UTF-8',
          },

          withCredentials : true,
          url: '/ssm/loginUserInfrom',//listUserInform
          data: jsonData,
        }).then(function (response) {

           if(response.data=="error"){
            thisVue.$message.error('错了哦，昵称或密码错误');
          }
          else if(response.data=="isDelete"){
             thisVue.$alert('当前账号已被禁用，请联系管理员', '消息', {
              confirmButtonText: '确定',
              callback: action => {
                this.$message({
                  type: 'error',
                  message: `action: ${ action }`
                });
              }
            });
          }
           else {
             thisVue.BoardValue.onlineUser.roomId=-1;

             thisVue.BoardValue.onlineUser.userInform=response.data
             thisVue.$router.push('/gameLobby');
           }


        })
          .catch(function (error) {
            console.log(error);
          });
      },
      submit(formName){
        let vum=this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            vum.userLogin();
          } else {
            console.log('error submit!!');
            return false;
          }
        });

      }
    }

  }


</script>

<style scoped src="../../../static/css/style1.css" />

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
