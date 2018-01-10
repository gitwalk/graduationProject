//登录界面
<template>
  <div class="container demo-1">
    <div class="content">
      <div id="large-header" class="large-header">
        <canvas id="demo-canvas"></canvas>
        <div class="logo_box">
          <h3>欢迎你</h3>
          <form action="#" name="f" method="post">
            <div class="input_outer">
              <span class="u_user"></span>
              <input name="logname" class="text" style="color: #FFFFFF !important" type="text" placeholder="请输入账户">
            </div>
            <div class="input_outer">
              <span class="us_uer"></span>
              <input name="logpass" class="text" style="color: #FFFFFF !important; position:absolute; z-index:100;"value="" type="password" placeholder="请输入密码">
            </div>
            <div class="mb2"><a @click="submitForm('ruleForm')" class="act-but submit"  style="color: #FFFFFF">登录</a></div>
          </form>
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
   export default {
     methods: {
       submitForm(formName) {
         const self = this;
         this.$router.push('/home')
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
<style >
  @import "../../../static/css/normalize.css";
  @import "../../../static/css/demo.css";
  @import "../../../static/css/component.css";

  a:hover{
    cursor:pointer
  }
</style>
