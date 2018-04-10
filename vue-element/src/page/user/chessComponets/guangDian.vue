<template>
  <div v-if="isShow" class="chessBox" @click="clickdian">
    <div class="guang" :style="cpStyle">
      <div class="dian" ></div>
    </div>
  </div>
</template>

<script>
    export default {
      name: "guangDian",
      data(){
        return{
          mapinf:this.BoardValue.mapinf

        }

      },
      props:['diannum'],
      methods:{
        clickdian(){

          let num=this.diannum-1;
          let x=num%9;
          let y=num/9;
          x=parseInt(x);
          y=parseInt(y);
          //需要添加一个判断当前行为是否合法的if
          this.mapinf.setXY(this.mapinf.chooseman,x,y);
        }
      },
      computed:{
        isShow(){
          return (this.diannum==this.mapinf.diannum);
        },
        cpStyle(){
          let num=this.diannum-1;
          let x=num%9;
          let y=num/9;
          x=parseInt(x);
          y=parseInt(y);
          // console.log("x:"+x+",y:"+y);
          x=4+x*66;
          y=4+y*68;

          return {left:x+'px',top:y+'px'};
        }
      }

    }
</script>

<style scoped>
  .chessBox{
    display:inline;

    position:relative;
  }
  .guang{
    height: 67px;
    width: 67px;
    /*!*x 4 66*!*/
    /*left: 70px;*/
    /*!*y 4 68*!*/
    /*top:344px;*/
    padding-top: 23px;
    padding-left: 23px;

    opacity:1;
    position:absolute;
    z-index: 1;
  }
  .dian{
    height: 30px;
    width: 30px;
    background-size:100% 100%;
    background-image: url("../../../../static/img/gdian.png");
  }


</style>
