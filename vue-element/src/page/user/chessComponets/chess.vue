<template>
 <div>
   <chessboard style="width: 100%;height: 100%"></chessboard>
 </div>
</template>

<script>
  import Chessboard from "./chessboard"

    export default {
        name: "chess",
      components: {Chessboard},

    }
</script>

<style scoped>

</style>
