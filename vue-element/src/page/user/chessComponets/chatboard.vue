<template>
    <div >
      <el-tag style="background-color: #f4f6f9;
                     color: #8f94ae;
                     font-weight:bold;
                    height: 30px;
                    width: 100%;
                    font-size: 16px" >对话消息</el-tag>
      <div id="convo" data-from="Sonu Joshi" style="border:1px black solid;">
        <ul class="chat-thread" style="margin: 0;padding: 0;width: 100%">
          <li class="myli">你好啊实打实大师萨达所大所多</li>
          <li class="otherli">aa:你好啊实打实大师萨达所大所多</li>
          <li class="otherli">aa:你好啊实打实大师萨达所大所多</li>
          <li class="otherli">aa:你好啊实打实大师萨达所大所多</li>
          <li class="otherli">aa:你好啊实打实大师萨达所大所多</li>
          <li class="myli">你好啊实打实大师萨达所大所多</li>
          <li class="myli">你好啊实打实大师萨达所大所多</li>
          <li class="myli">你好啊实打实大师萨达所大所多</li>
          <li class="otherli">aa:你好啊实打实大师萨达所大所多</li>
          <li class="myli">你好啊实打实大师萨达所大所多</li>
          <li class="otherli">aa:你好啊实打实大师萨达所大所多</li>
          <li class="myli">你好啊实打实大师萨达所大所多</li>
        </ul>
      </div>


      <el-input placeholder="请输入内容"  class="input-with-select">
        <el-button slot="append" icon="el-icon-back"></el-button>
      </el-input>
    </div>
</template>

<style scoped src="../../../../static/css/style.css" />

<script>
    export default {
        name: "chatboard"
    }
</script>

<style scoped>


</style>
