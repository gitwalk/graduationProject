//登录界面
<template>
  <div class="container demo-1">
    <div class="content">
      <div id="large-header" class="large-header">
        <canvas id="demo-canvas"></canvas>
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

   // var category={"name":"test","id":3};
   // var jsonData = JSON.stringify(category);
   export default {
     data() {
       return {

         dynamicValidateForm: {
           name:'',
           password:'',

         },
         rules: {
           name: [
             { required: true, message: '请输入账号', trigger: 'blur' },
             { min: 1, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
           ],
           password:[
             { required: true, message: '请输入密码', trigger: 'blur' },
             { min: 1, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
           ]
         }
       }
     },
     methods: {
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

  a:hover{
    cursor:pointer
  }
</style>
