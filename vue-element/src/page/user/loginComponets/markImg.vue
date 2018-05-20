<template>
  <div>
    <img style="height: 40px" @click="changeImgMark()" :src='imgUrl'  />
  </div>
</template>

<script>
    export default {
        name: "markImg",
      data(){
        return {
          imgUrl: 'http://' + this.Ipaddress.ipdata + '/ssm/captcha/captcha-image'
        }
      },
      methods:{
        changeImgMark(){

            this.imgUrl='http://'+this.Ipaddress.ipdata+'/ssm/captcha/captcha-image?num='+Math.round(Math.random()*1000);
        }

      }


    }
</script>

<style scoped>

</style>
