<template>
 <div>
    <chatboard  :contextitems="items"></chatboard>
 </div>
</template>

<script>

    import Chatboard from "./chessComponets/chatboard";
    export default {
        name: "login",
      components: {Chatboard},
      data(){
        return{
          items:[]
        }
      },
      mounted(){
          let thisvue=this;
          // var ws = new WebSocket("ws://127.0.0.1:8081/ssm/myHandler");
          //   ws.onopen = function () {
          //     console.log("onpen");
          //     ws.send("大家好");
          //   }
          //   ws.onclose = function () {
          //     console.log("onclose");
          //   }
          //
          //   ws.onmessage = function (msg) {
          //     var obj = eval('(' +  msg.data + ')');
          //     console.log(obj);
          //     thisvue.items.push(obj);
          //
          //   }
      }
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
