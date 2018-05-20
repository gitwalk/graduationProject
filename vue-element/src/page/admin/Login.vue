//登录界面
<template>
  <div id="bodyd" class="container demo-1" style="height: 100%">
    <div class="content" style="height: 100%;overflow-x: hidden;
       overflow-y: hidden;">
      <div id="large-header" class="large-header" style="height: 720px !important;">
        <canvas id="demo-canvas" style="height: 100%"></canvas>
        <div class="logo_box">
          <h3>欢迎你</h3>
          <el-form ref="dynamicValidateForm" :rules="rules" :model="dynamicValidateForm" >
            <div class="input_outer">
              <el-form-item  prop="name">
                <span class="u_user"></span>
                <input v-model.trim="dynamicValidateForm.name" class="text" style="color: #FFFFFF !important" type="text" placeholder="请输入账户" />
              </el-form-item>
            </div>
            <div class="input_outer">
              <el-form-item  prop="password">
                <span class="us_uer"></span>
                <input v-model.trim="dynamicValidateForm.password" class="text" style="color: #FFFFFF !important;" type="password" placeholder="请输入密码" />
              </el-form-item>
            </div>
            <el-row style="height: 55px">
              <el-col :span="12">
                <div class="input_outer" >
                  <el-form-item  prop="mark">
                    <input v-model.trim="dynamicValidateForm.mark" class="text" style="margin-left:20%;width:100%;color: #FFFFFF !important;" type="text" placeholder="请输入验证码" />
                  </el-form-item>
                </div>
              </el-col>
              <el-col :span="9" :offset="2">
                <mark-img></mark-img>
              </el-col>
            </el-row>
            <div class="mb2"><a @click="submitForm('dynamicValidateForm')" class="act-but submit"  style="color: #FFFFFF">登录</a></div>
          </el-form>
        </div>
      </div>
    </div>
  </div><!-- /container -->
</template>

<script>
   import  "../../../static/js/TweenLite.min.js"
   import "../../../static/js/EasePack.min.js"
   import "../../../static/js/rAF.js"
   import {demo1} from "../../../static/js/demo-1.js"
   import axios from 'axios';
   import MarkImg from "../user/loginComponets/markImg";

   // var category={"name":"test","id":3};
   // var jsonData = JSON.stringify(category);
   export default {
     components: {MarkImg},
     data() {
       var checkMark1=(rule, value, callback) => {
         let vm=this;
         if(value === ''){
           return callback(new Error('请输入验证码'));
         }
         else {
           vm.checkMark(value,callback);
         }

       };
       return {

         dynamicValidateForm: {
           name:'',
           password:'',
           mark:''
         },
         rules: {
           name: [
             { required: true, message: '请输入账号', trigger: 'blur' },
             { min: 6, max: 13, message: '长度在 6 到 13 个字符', trigger: 'blur' }
           ],
           password:[
             { required: true, message: '请输入密码', trigger: 'blur' },
             { min: 6, max: 13, message: '长度在 6 到 13 个字符', trigger: 'blur' }
           ],
           mark: [
             { validator: checkMark1, trigger: 'blur' },

           ],
         }
       }
     },
     methods: {
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
       submitForm(formName) {

         this.$refs[formName].validate((valid) => {
           if (valid) {

             this.$LoginAxios(this.dynamicValidateForm,this,"/loginAdminInfrom","adminLogin");
           } else {
             console.log('error submit!!');
             return false;
           }
         }
         );


       }
     }
     ,mounted () {
          demo1();
     }
   }
</script>

<!--<script src="/static/js/TweenLite.min.js"></script>-->
<!--<script src="/static/js/EasePack.min.js"></script>-->
<!--<script src="/static/js/rAF.js"></script>-->
<!--<script src="../../../static/js/demo-1.js"></script>-->
<style scoped src="../../../static/css/normalize.css" />
<style scoped src="../../../static/css/demo.css" />
<style scoped src="../../../static/css/component.css" />
<style>

  .input_outer .el-form-item__error{
    left: 40px !important;
    font-size: 13px;
  }

</style>


<style scoped>
  /*#bodyd{*/
    /*overflow-x: hidden;*/
    /*overflow-y: hidden;*/
  /*}*/
  a:hover{
    cursor:pointer
  }
</style>
