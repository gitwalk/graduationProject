
//对局信息查看
<template>
  <div class="rePlayRoom" style="height:630px;">

    <!---------------------------搜索栏---------------------------------------------------------------------->
    <el-form ref="dynamicValidateForm" :inline="true" :model="dynamicValidateForm" class="demo-form-inline" >

      <el-form-item label="对局日期" >
        <el-date-picker   v-model="pickerdata"  type="daterange" align="right" unlink-panels range-separator="至"
                         start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions2">
        </el-date-picker>
      </el-form-item>

      <!--<el-form-item>-->
        <!--<el-button type="primary" @click="submitForm('dynamicValidateForm')">查询</el-button>-->
      <!--</el-form-item>-->
    </el-form>
    <!--------------------------------------------表格------------------------------------------------------------------->
    <el-table
                  :data="gameInform" height="90%" style="width: 100%;" >
      <el-table-column label="游戏时间" width="190px">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.startTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="红方账号" width="90px" align="center">
        <template slot-scope="scope">
          <div slot="reference" class="name-wrapper">
            <el-tag size="medium">{{ scope.row.redUserInform.name }}</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="黑方账号" width="90px" align="center">
        <template slot-scope="scope">
          <div slot="reference" class="name-wrapper">
            <el-tag size="medium">{{ scope.row.blackUserInform.name }}</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="游戏时长" width="175px">
        <template slot-scope="scope">{{scope.row.gameTime}}</template>
      </el-table-column>
      <el-table-column label="对局结果" width="175px" align="center">
        <template slot-scope="scope">{{scope.row.gameState}}</template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">

          <el-button size="mini" icon="el-icon-search" @click="handleEdit(scope.$index, scope.row)" type="success">查看详情</el-button>

        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      title="回放"
      :visible.sync="dialogTableVisible.data1"

      top="0"
      :before-close="handleClose">
      <re-play ></re-play>


    </el-dialog>


  </div>
</template>

<script>
  import RePlay from "../chessComponets/rePlay";
  import ChessPlay from "../chessPlay";
  export default {
    name: "Record",
    components: {ChessPlay, RePlay},
    mounted(){
      /*初始化页面用户信息*/
      //this.$pagesHelp(this,7,1,"/admin/listGameInform","dealGameInfom");

      this.getinform(this.dynamicValidateForm);

    },
    data() {
      return {

        dialogTableVisible: this.BoardValue.dialogTableVisible,
        results:[],
        pickerdata:[],
        dynamicValidateForm: {
          starStarTime:'',
          endStarTime:'',
        },
        rePlayInform:[],
        gameInform:[],
        pagesInform:[],
        pickerOptions2: {
          disabledDate(time) {
            return time.getTime() >= Date.now();
          },
          shortcuts: [{
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          }]
        }

      }
    },
    watch:{
      pickerdata:function (val2,oldval) {

        var val=this.dynamicValidateForm;
        if(val.starStarTime!=null&&val.starStarTime!=''){
          if(val.endStarTime!=null&&val.endStarTime!=''){
            this.submitForm('dynamicValidateForm');
          }
        }
        else {
          if(val.endStarTime==null||val.endStarTime==''){
            this.submitForm('dynamicValidateForm');
          }
        }
      }
    },

    methods: {
      // /*分页点击事件*/
      // handleCurrentChange(val){
      //   this.$UserserInform(val,this,"/admin/GameInform","dealGameInfom");
      // },
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },
      getinform(data){
        var jsonData = JSON.stringify(data);
        let thisVue=this;
        this.$axios({
          method: 'post',
          headers: {
            'Content-Type': 'application/json;charset=UTF-8',

          },
          url: '/ssm/user/userGameInform',//listUserInform
          withCredentials : true,
          data: jsonData,
        }).then(function (response) {
          if(response.data==""){
            thisVue.$alert('请重新登录', '消息', {
              confirmButtonText: '确定',
              callback: action => {
                if(thisVue.BoardValue.ws!=null){
                  thisVue.BoardValue.ws.close();
                }
                thisVue.$router.push('/gamePlay');
              }
            });
          }
          else if(response.data=="error"){
            thisVue.gameInform=[];
          }
          else if(response.data!="error"){
            thisVue.dealInform(response);

          }
        })
          .catch(function (error) {
            console.log(error);
          });
      },
      dealInform(response){
        let thisVue=this;
        thisVue.gameInform=[];
        for(var i in response.data) {
          if (response.data[i].startTime == response.data[i].endTime) continue;
          response.data[i].gameTime=thisVue.$formatDuring(response.data[i].gameTime);
          response.data[i].setStepTime=thisVue.$formatDuring(response.data[i].setStepTime);
          response.data[i].setTime=thisVue.$formatDuring(response.data[i].setTime);
          response.data[i].startTime=thisVue.$timeFormat(response.data[i].startTime,'yyyy-MM-dd HH:mm:ss');
          response.data[i].endTime=thisVue.$timeFormat(response.data[i].endTime,'yyyy-MM-dd HH:mm:ss');
          switch(response.data[i].gameState) {
            case 0:
              response.data[i].gameState="红方胜利（绝杀）"
              break;
            case 1:
              response.data[i].gameState="黑方胜利（绝杀）"
              break;
            case 2:
              response.data[i].gameState="红方胜利（黑方超步时）"
              break;
            case 3:
              response.data[i].gameState="黑方胜利（红方超步时）"
              break;
            case 4:
              response.data[i].gameState="红方胜利（黑方超局时）"
              break;
            case 5:
              response.data[i].gameState="黑方胜利（红方超局时）"
              break;
            case 6:
              response.data[i].gameState="红方胜利（黑方认输）"
              break;
            case 7:
              response.data[i].gameState="黑方胜利（红方认输）"
              break;
            case 8:
              response.data[i].gameState="和局"
              break;
          }

          thisVue.gameInform.push(response.data[i]);


        }
        console.log(thisVue.gameInform);
      },
      handleEdit(index, row) {
        let thisVue=this;
        var jsonData = JSON.stringify(row.id);
        this.$axios({
          method: 'post',
          headers: {
            'Content-Type': 'application/json;charset=UTF-8',

          },
          url: '/ssm/user/rePlay',//listUserInform
          withCredentials : true,
          data: jsonData,
        }).then(function (response) {
          if(response.data==""){
            thisVue.$alert('请重新登录', '消息', {
              confirmButtonText: '确定',
              callback: action => {
                if(thisVue.BoardValue.ws!=null){
                  thisVue.BoardValue.ws.close();
                }
                thisVue.$router.push('/gamePlay');
              }
            });
          }
          else if(response.data!="error"){
            thisVue.BoardValue.imgarrys.splice(0,thisVue.BoardValue.imgarrys.length);//清空数组

            thisVue.BoardValue.dialogTableVisible.data1 = true;
            thisVue.BoardValue.rePlayInform.gameInform=row;
            thisVue.BoardValue.rePlayInform.stepInform=response.data;

            console.log(index, thisVue.rePlayInform);
          }
        })
          .catch(function (error) {
            console.log(error);
          });


      },

      /*提交查询用户信息*/
      submitForm(formName) {
        let thisVue=this;
        thisVue.$refs[formName].validate((valid) => {
          if (valid) {
            if(thisVue.pickerdata==null){
              thisVue.dynamicValidateForm.starStarTime="";
              thisVue.dynamicValidateForm.endStarTime="";
            }else{
              thisVue.dynamicValidateForm.starStarTime=thisVue.pickerdata[0];
              var starStarTime=Date.parse(new Date(thisVue.pickerdata[0]));
              var endStarTime = Date.parse(new Date(thisVue.pickerdata[1]))+86400000;

              thisVue.dynamicValidateForm.endStarTime=thisVue.$timeFormat(endStarTime,'yyyy-MM-dd HH:mm:ss');
              thisVue.dynamicValidateForm.starStarTime=thisVue.$timeFormat(starStarTime,'yyyy-MM-dd HH:mm:ss');

            }

            thisVue.getinform(thisVue.dynamicValidateForm);

          } else {

            console.log('error submit!!');
            return false;
          }
        });

      }
    }
  }
</script>

<style >

  .rePlayRoom>.el-dialog__wrapper>.el-dialog>.el-dialog__body{
    background-color:transparent ;
    padding: 0;
  }
  .rePlayRoom>.el-dialog__wrapper>.el-dialog{
    background-color:transparent ;
    margin-left: 100px;
    margin-bottom: 0;
  }
  .rePlayRoom>.el-dialog__wrapper>.el-dialog>.el-dialog__header{
    display:none;
  }

</style>
