<template>
  <div v-if="this.chessman.isexit" @click="chooseChess" :class="{flash:(this.mapinf.chooseman==this.chessnum)}" class="chessBox animated infinite"
       :style="{left:chessman.positionX+'px',top:chessman.positionY +'px' }" >
    <div class="guang" :style="{backgroundImage:
                              (this.mapinf.guangnum==this.chessnum)?
                                                ('url('+guangUrl+')') : ('url()' )}">
      <div class="mu">
        <div class="zi" :style="{backgroundImage: 'url(' + chessman.bgurl + ')'}" >
        </div>
      </div>
      <div class="shadow">
      </div>
    </div>
    <audio id="audioSelect"
           src="../../../../static/audios/select.wav">
    </audio>

  </div>
</template>

<script>


  export default {
    name: "qi",
    data(){
      return{
        chessman:this.BoardValue.mapinf.chessmans[this.chessnum],
        mapinf:this.BoardValue.mapinf,

        guangUrl:'../../../../static/img/0.png'
      }
    },
    props:['chessnum'],
    methods:{
      chooseChess(){
        if(this.BoardValue.onlineUser.userState!=5&&this.BoardValue.onlineUser.userState!=6) return;
        if((this.mapinf.userisRed&&this.mapinf.isRed&&this.chessman.num%2!=0)||
          (!this.mapinf.userisRed&&!this.mapinf.isRed&&this.chessman.num%2==0)){
          if((this.mapinf.isRed&&this.chessman.num%2!=0)||(!this.mapinf.isRed&&this.chessman.num%2==0)){

            var num= this.BoardValue.mapinf.chooseman;
            if(num>0){
              this.BoardValue.mapinf.chessmans[num].isflash=false;
            }
            this.BoardValue.mapinf.chooseman=this.chessnum;

            this.BoardValue.mapinf.chessmans[this.chessnum].isflash=true;
            let audioSelect = document.getElementById('audioSelect');
            audioSelect.play();
          }}
          //如果选择的棋子不是我方棋子，则判断是否可以吃棋
          else if((this.mapinf.isRed&&this.chessman.num%2==0)
                  ||(!this.mapinf.isRed&&this.chessman.num%2!=0)){

            if(this.BoardValue.mapinf.chooseman!=-1){//如果符合规则
              let x=this.chessman.x;
              let y=this.chessman.y;
              let num=this.BoardValue.mapinf.chooseman;
              if(this.mapinf.isOK(num,x,y)){

                // this.chessman.isexit=false;
                this.mapinf.setXY(num,x,y);

              }
            }
          }




      }
    },

    mounted(){

    },

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

    padding-top: 5px;
    padding-left: 5px;
    background-size:100% 100%;
    position:absolute;
    z-index: 2;
    /*background-image: url("../../../../static/img/0.png");*/
  }
  .mu{
    height: 60px;
    width: 55px;
    padding-top: 5px;
    padding-left: 10px;

    background-size:100% 100%;
    position:absolute;
    z-index: 4;
    background-image: url("../../../../static/img/123.png");
  }
  .zi{
    height: 80%;
    width: 85%;
    overflow:hidden;

    background-size:100% 100%;

  }
  .shadow{

    height: 70px;
    width: 70px;
    margin-top: 8px;
    margin-left: 8px;
    background-image: url("../../../../static/img/shader00.png");
    background-size:100% 100%;
    position:absolute;
    z-index: 3;
  }
</style>
