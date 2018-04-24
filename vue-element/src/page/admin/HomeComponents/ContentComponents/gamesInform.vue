
//对局信息查看
<template>
  <div class="gamesInform" style="height:603.6px;width: 1266px">
    <!----------------------------------导航栏--------------------------------------------------------------------->
    <el-breadcrumb separator-class="el-icon-arrow-right" style="font-size:15px;" >
      <el-breadcrumb-item :to="{ path: '/gamesInform' }">
        对局信息
      </el-breadcrumb-item>

    </el-breadcrumb>
    <!---------------------------搜索栏---------------------------------------------------------------------->
    <el-form ref="dynamicValidateForm" :inline="true" :model="dynamicValidateForm" class="demo-form-inline" style="margin-top: 3%;margin-left: 5%">
      <el-form-item label="红方账号">
        <el-input clearable spellcheck="false" v-model.trim="dynamicValidateForm.selectRedName" placeholder="昵称" style="width: 200px"></el-input>
      </el-form-item>
      <el-form-item label="黑方账号">
        <el-input clearable spellcheck="false" v-model.trim="dynamicValidateForm.selectBlackName" placeholder="昵称" style="width: 200px"></el-input>
      </el-form-item>
      <el-form-item label="对局日期" >
        <el-date-picker  v-model="pickerdata"  type="daterange" align="right" unlink-panels range-separator="至"
                         start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions2">
        </el-date-picker>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm('dynamicValidateForm')">查询</el-button>
      </el-form-item>
    </el-form>
    <!--------------------------------------------表格------------------------------------------------------------------->
    <el-table :data="gameInform" style="width: 90%;height:70%;margin-left: 5%" >
      <el-table-column label="游戏时间" width="200px">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.startTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="红方账号" width="180px" align="center">
        <template slot-scope="scope">
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.redUserInform.name }}</el-tag>
            </div>
        </template>
      </el-table-column>
      <el-table-column label="黑方账号" width="180px" align="center">
        <template slot-scope="scope">
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.blackUserInform.name }}</el-tag>
            </div>
        </template>
      </el-table-column>
      <el-table-column label="游戏时长" width="180px">
        <template slot-scope="scope">{{scope.row.gameTime}}</template>
      </el-table-column>
      <el-table-column label="对局结果" width="180px" align="center">
        <template slot-scope="scope">{{scope.row.gameState}}</template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">

          <el-button size="mini" icon="el-icon-search" @click="handleEdit(scope.$index, scope.row)" type="success">查看详情</el-button>

        </template>
      </el-table-column>
    </el-table>
    <!------------------------------------------------------分页条------------------------------------------------------>
    <el-row :gutter="24" style="margin-top: 2%">
      <el-col :span="8" :offset="6">
        <el-pagination    @current-change="handleCurrentChange"
                          :current-page.sync="pagesInform.pageNum"
                          :page-size="pagesInform.count"
                          background layout="total,prev, pager, next, jumper"
                          :total="pagesInform.pagetotal" ></el-pagination>
      </el-col>
    </el-row>
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
    import RePlay from "../../../user/chessComponets/rePlay";
    export default {
        name: "games-inform",
      components: {RePlay},
      mounted(){
        /*初始化页面用户信息*/
        this.$pagesHelp(this,7,1,"/admin/listGameInform","dealGameInfom");
      },
      data() {
        return {
          dialogTableVisible: this.BoardValue.dialogTableVisible,
          results:[],
          pickerdata:[],
          dynamicValidateForm: {
            selectName:'',
            starStarTime:'',
            endStarTime:'',
            page:''
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
      methods: {
        handleClose(done) {
          this.$confirm('确认关闭？')
            .then(_ => {
              done();
            })
            .catch(_ => {});
        },
        /*分页点击事件*/
        handleCurrentChange(val){
          this.$UserserInform(val,this,"/admin/GameInform","dealGameInfom");
        },
        handleEdit(index, row) {
          let thisVue=this;
          var jsonData = JSON.stringify(row.id);
          this.$axios({
            method: 'post',
            headers: {
              'Content-Type': 'application/json;charset=UTF-8',

            },
            url: '/ssm/admin/rePlay',//listUserInform
            withCredentials : true,
            data: jsonData,
          }).then(function (response) {
            if(response.data==""){
              thisVue.$alert('请重新登录', '消息', {
                confirmButtonText: '确定',
                callback: action => {
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
        handleDelete(index, row) {
          console.log(index, row);
        },
        /*提交查询用户信息*/
        submitForm(formName) {

          this.$refs[formName].validate((valid) => {
            if (valid) {
              if(this.pickerdata==null){
                this.dynamicValidateForm.starStarTime="";
                this.dynamicValidateForm.endStarTime="";
              }else{
                this.dynamicValidateForm.starStarTime=this.pickerdata[0];
                var endStarTime = Date.parse(new Date(this.pickerdata[1]))+86400000;

                this.dynamicValidateForm.endStarTime=this.$timeFormat(endStarTime,'yyyy-MM-dd HH:mm:ss');

              }

              this.$UserserInform(1,this,"/admin/GameInform","dealGameInfom");

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

  .gamesInform>.el-dialog__wrapper>.el-dialog>.el-dialog__body{
    background-color:transparent ;
    padding: 0;
  }
  .gamesInform>.el-dialog__wrapper>.el-dialog{
    background-color:transparent ;
    margin-left: 100px;
    margin-bottom: 0;
  }
  .gamesInform>.el-dialog__wrapper>.el-dialog>.el-dialog__header{
    display:none;
  }

</style>
