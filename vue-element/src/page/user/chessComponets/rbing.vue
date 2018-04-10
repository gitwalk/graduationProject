<template>
    <div class="qi" >
      <qi>

      </qi>
    </div>
</template>

<script>
    import Qi from "./qi";

    export default {
      name: "rbing",

      components: {Qi},
      methods:{
        chooseChess(){
          alert("nih");
        },
        upisFlash(val){
          this.isflash=val;

        }
      },
      computed: {

      },
      data(){
          return{
            burl:'../../../../static/img/redchess/redBing01.png',

          }
      }
    }

</script>

<style scoped>

</style>
