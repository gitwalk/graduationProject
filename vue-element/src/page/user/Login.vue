<template>



   <!-- <div class="chessBox" style="margin-top: 70px">
      <div class="chess">
        <div class="chess1 mu div2" style=" background-image: url(../../../static/img/1/116.png);">
          <div class="chess1 zi div3" ></div>
        </div>
        &lt;!&ndash;<div class="chess1 huan div4" ></div>&ndash;&gt;
      </div>
      <div class="chess1 shader div5" ></div>
    </div>

    <div class="chessBox" style="margin-left: 75px">
      <div class="chess">
        <div class="chess1 mu div2" style=" background-image: url(../../../static/img/1/111.png);">
          <div class="chess1 zi div3" ></div>
        </div>
        &lt;!&ndash;<div class="chess1 huan div4" ></div>&ndash;&gt;
      </div>
      <div class="chess1 shader div5" ></div>
    </div>
    <div class="chessBox" style="margin-left: 140px">
      <div class="chess">
        <div class="chess1 mu div2" style=" background-image: url(../../../static/img/1/115.png);">
          <div class="chess1 zi div3" ></div>
        </div>
        &lt;!&ndash;<div class="chess1 huan div4" ></div>&ndash;&gt;
      </div>
      <div class="chess1 shader div5" ></div>
    </div>

    <div class="chessBox" style="margin-left: 75px;margin-top: 70px">
      <div class="chess">
        <div class="chess1 mu div2" style=" background-image: url(../../../static/img/1/19.png);">
          <div class="chess1 zi div3" ></div>
        </div>
        &lt;!&ndash;<div class="chess1 huan div4" ></div>&ndash;&gt;
      </div>
      <div class="chess1 shader div5" ></div>
    </div>-->
<div>
  </div>







</template>

<script>
    export default {
        name: "login"
    }
</script>

<style scoped>

  @keyframes mymove
  {
    from {top:0px;left: 10px}
    to {top:135px;left: 70px}
  }
  /*.chessBoxmove{*/
    /*animation:mymove 10s infinite;*/
    /*-webkit-animation:mymove 10s infinite; !*Safari and Chrome*!*/
  /*}*/
  .chessBox{
    display:inline;
    height: 70px;
    width: 70px;
    margin-left: 10px;
    position:absolute;

  }
  .shader{
    height: 70px;
    width: 70px;
    margin-left: 12px;
    margin-top: -55px;
    background-image: url(../../../static/img/shader00.png);
    background-size: contain;
    background-repeat: no-repeat;
    z-index: -5;
  }
  .b{
    height: 980px;
    width: 940px;
    position:absolute;

    z-index: -4;
  }
  .chess{

    height: 70px;
    width: 70px;
    position:relative;

  }
  .chess1{
    position:absolute;
    background-repeat: no-repeat;
  }
  .mu{

    width: 95%;
    height: 95%;
    margin-left: 4%;
    margin-top: 3%;
    /*background-image: url(../../../static/img/1/110.png);*/
    background-size: contain;

    z-index: -3;
  }
  .zi{

    display: table-cell;
    margin-top: 8%;
    margin-left: 10%;
    width: 75%;
    height: 75%;
    z-index: -2;
    background-size: contain;
    background-image: url(../../../static/img/redchess/redBing01.png);
  }
  .huan{

    height: 100%;
    width: 100%;
    background-image: url(../../../static/img/0.png);
    background-size: contain;
    z-index: -1;
    /*background-color: red;*/
  }





</style>
