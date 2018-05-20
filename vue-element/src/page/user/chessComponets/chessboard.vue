<template>
  <div>
<!--x:66 y:68 start:8 11-->

    <div class="chessboard" >
      <template v-for="imgarry in imgarrys">
        <imgzi :imzi-num="imgarry.imziNum"></imgzi>
      </template>
      <template v-for="item in 32">
        <qi  :chessnum="item"  ></qi>
      </template>

      <div id="div1" >
        <template v-for="item1 in 90">
          <guang-dian :diannum="item1"></guang-dian>
        </template>
        <img src="../../../../static/img/M_ChessBoard_Board_JDXQ.png" @click.prevent="chooseBoard($event)"/>

      </div>
    </div>
    <audio id="audioGo"
           src="../../../../static/audios/go.mp3">
    </audio>
    <audio id="audioJiangjun"
           src="../../../../static/audios/Man_jiangjun.mp3">
    </audio>
    <audio id="audioEat"
           src="../../../../static/audios/eat.mp3">
    </audio>
    <audio id="audioOver"
           src="../../../../static/audios/gamewin.mp3">
    </audio>
  </div>

</template>

<script>

    import Qi from "./qi";
    import GuangDian from "./guangDian";
    import Imgzi from "./imgzi";
    export default {
        name: "chessboard",
      data() {
          return{
            imgarrys: this.BoardValue.imgarrys,
            mapinf: this.BoardValue.mapinf
          }

      },
      methods:{
        openInf(){

        },
        chooseBoard(event){

          var num= this.BoardValue.mapinf.chooseman;
          if(num>0){
            var x=(event.offsetX-4)/66;
            var y=(event.offsetY-4)/68;
            x=parseInt(x);
            y=parseInt(y);
            // console.log("X 坐标: " + event.offsetX + ", Y 坐标: " + event.offsetY);
            // console.log("X 坐标: " + x + ", Y 坐标: " + y);
            if(x>8||y>9||x<0||y<0) return;

            this.BoardValue.mapinf.chooseman=-1;
            this.BoardValue.mapinf.chessmans[num].isflash=false;
            this.BoardValue.mapinf.setXY(num,x,y);

          }
        }
      },
      components: {Imgzi, GuangDian, Qi}
    }
</script>

<style scoped>
  div#div1{
    height:100%;
    width:100%;
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
  .chessboard{
  height: 100%;
  width: 100%;

  z-index: -4;
  }
</style>
