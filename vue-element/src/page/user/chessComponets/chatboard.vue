<template>
    <div >
      <el-tag style="background-color: #f4f6f9;
                     color: #8f94ae;
                     font-weight:bold;
                    height: 30px;
                    width: 100%;
                    font-size: 16px" >对话消息</el-tag>
      <div  id="convo" data-from="Sonu Joshi" style="border:1px black solid;">
        <ul class="chat-thread" style="margin: 0;padding: 0;width: 100%">
          <template v-for="item in this.contextitems">
            <li  :class="{myli:(item.userId==message.userId),otherli:(item.userId!=message.userId),}">
              {{((item.userId!=message.userId)?(item.userName+':'):'')+item.data
              +((item.userId==message.userId)?(':'+item.userName):'')
              }}
            </li>
          </template>

        </ul>
      </div>

      <el-input @keyup.enter.native="sendMessage"  v-model.trim="message.data"  placeholder="请输入内容"  class="input-with-select">
        <el-button slot="append" icon="el-icon-back" @click="sendMessage"></el-button>
      </el-input>
    </div>
</template>

<style scoped src="../../../../static/css/style.css" />

<script>
  import $ from 'jquery'
    export default {
        name: "chatboard",

      data(){
       return{
         contextitems:[],
         dialogTableVisible:this.BoardValue.dialogTableVisible,
         clearTime:this.BoardValue.clearTime,
         messageInform:this.BoardValue.messageInform1,
         message:{
           userId:this.BoardValue.onlineUser.userInform.id,
           userName:this.BoardValue.onlineUser.userInform.name,
           data:'',
           roomId:this.BoardValue.mapinf.roomId
         }
       }
      },

      watch:{
        'dialogTableVisible.data':function () {
          this.contextitems=[];

        },
        'messageInform.time':function () {
          let mgs=new Object();
          mgs.userId=this.messageInform.userId;
          mgs.userName=this.messageInform.userName;
          mgs.data=this.messageInform.data;
          mgs.roomId=this.messageInform.roomId;
          this.contextitems.push(mgs);

          $(document).ready(function(){
            var scrollHeight = $('.chat-thread').prop("scrollHeight");
            $('.chat-thread').animate({scrollTop:scrollHeight}, 400);

          });

        }
      },
      methods:{
        sendMessage(){
            if(this.message.data=="") return
            var jsonData = JSON.stringify(this.message);
            let thisVue=this;
            this.$axios({
              method: 'post',
              headers: {
                'Content-Type': 'application/json;charset=UTF-8',
              },
              withCredentials : true,
              url: '/ssm/user/allmessage',//listUserInform
              data: jsonData,
            }).then(function (response) {

                if(response.data==""){

                  thisVue.$alert('请重新登录', '消息', {
                    confirmButtonText: '确定',
                    callback: action => {
                      thisVue.$router.push('/gamePlay');
                    }
                  });
                }

              thisVue.message.data='';
            })
              .catch(function (error) {
                console.log(error);
              });

        }
      }
    }
</script>

<style scoped>


</style>
