<template>
  <div v-if="this.isexit" class="chessBox" :style="stylePositon">
    <div :class="{zoomIn: isflipInX,fadeOut:isfadeOut}"  class="animated  box ">
      <div id="quanzi" :style="styleUrl"> </div>
      <div id="quan"  ></div>
    </div>

  </div>
</template>

<script>

    export default {
        name: "imgzi",
      props:['imziNum'],
        data(){
          return{
            isflipInX:true,
            isfadeOut:false,
            mapinf:this.BoardValue.mapinf,
            isexit:true,
            bgurl01:'../../../../static/img/jiang/__auto_jiang_jiang5.png',
            bgurl02:'../../../../static/img/chi/__auto_chi_chi5.png',
            bgurl03:'../../../../static/img/kai_ju/__auto_kaiju_kaiju5.png',

          }
        },
      computed:{
        stylePositon(){
          if(this.imziNum==1){ //上将
            return {
              left: 250+'px',
              top:125+'px'
            }
          }
          else if(this.imziNum==2){ //中吃
            return {
              left: 250+'px',
              top:300+'px'
            }
          }
          else if(this.imziNum==3){//下将
            return {
              left: 250+'px',
              top:490+'px'
            }
          }
          else if(this.imziNum==4){//中开局
            return {
              left: 250+'px',
              top:300+'px'
            }
          }
        },
        styleUrl(){
          if(this.imziNum==1){
            return {
              backgroundImage:'url('+this.bgurl01+')', width: 128+'px',
              height: 148+'px',
              marginTop: '-'+15+'px',
            }
          }
          else if(this.imziNum==2){
            return {
              backgroundImage:'url('+this.bgurl02+')', width: 126+'px',
              height: 117+'px',
              marginTop: 5+'px',
            }
          }
          else if(this.imziNum==3){
            return {
              backgroundImage:'url('+this.bgurl01+')', width: 128+'px',
              height: 148+'px',
              marginTop: '-'+15+'px',
            }
          }
          else if(this.imziNum==4){
            return {
              backgroundImage:'url('+this.bgurl03+')', width: 166+'px',
              height: 76+'px',
              marginTop: 30+'px',
              marginLeft:-25+'px'
            }
          }

        }
      },
    mounted(){
          var vum =this;
        setTimeout(function () {
          vum.isflipInX=false;
          vum.isfadeOut=true;
        },500);
        setTimeout(function () {
          vum.isexit=false;

        },1000);


    }
    ,
      update (){
          alert("aa");

      }
    }


</script>

<style scoped>


  .chessBox{
    display:inline;

    position:relative;
    z-index: 10;
  }
  .box{
    animation-duration:0.5s;
    position:absolute;
  }

  #quanzi{
    background-size:100% 100%;
    position: absolute;
    z-index: 10;
  }


  #quan{

    height: 137px;
    width: 142px;
    margin-left: -10px;

    background-size:100% 100%;

    background-image: url("../../../../static/img/__auto___Auto_CommonPacker_1.png");
  }
</style>
