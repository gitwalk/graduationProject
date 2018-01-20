
//用户信息统计
<template>
  <div style="height:603.6px;width: 1266px">
    <!----------------------------------导航栏--------------------------------------------------------------------->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>活动管理</el-breadcrumb-item>
      <el-breadcrumb-item>活动列表</el-breadcrumb-item>
      <el-breadcrumb-item>活动详情</el-breadcrumb-item>
    </el-breadcrumb>
    <!---------------------------搜索栏---------------------------------------------------------------------->
    <el-row type="flex" justify="end" class="row-bg" style="margin-top: 2%">
      <el-col :span="8" >
        <el-form  :inline="true" ref="dynamicValidateForm" :model="dynamicValidateForm" class="demo-form-inline" >
          <el-form-item label="账号">
            <el-input spellcheck="false" v-model="dynamicValidateForm.name" placeholder="昵称"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('dynamicValidateForm')">查询</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <!--------------------------------------------表格------------------------------------------------------------------->
  <el-table :data="userInform"  style="width: 100%" height="440" >
    <el-table-column type="expand" >
      <template slot-scope="props" >
        <el-table   :data=" props.row.loginInformList"   height="250" border  style="width: 820px;margin-left: 200px" >
          <el-table-column :resizable=false width="200" prop="loginIp" label="登录IP地址" ></el-table-column>
          <el-table-column  :resizable=false width="200" prop="loginTime" label="上线时间" ></el-table-column>
          <el-table-column :resizable=false width="200" prop="logoutTime" label="下线时间"></el-table-column>
          <el-table-column  :resizable=false width="200" prop="time" label="在线时长"></el-table-column>
        </el-table>
      </template>
    </el-table-column>
    <el-table-column label="用户账号" width="180px" align="center">
      <template slot-scope="scope">
        <div slot="reference" class="name-wrapper">
          <el-tag size="medium">{{ scope.row.name }}</el-tag>
        </div>
      </template>
    </el-table-column>
    <!--<el-table-column label="用户账号" prop="name"></el-table-column>-->
    <el-table-column label="总在线时间" prop="allLoginTime"></el-table-column>
    <el-table-column label="对局次数" prop="gameNum"></el-table-column>
    <el-table-column label="登录次数" prop="loginNum"></el-table-column>
    <el-table-column label="胜率" prop="winningRate"></el-table-column>
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
  </div>
</template>
<script>

    export default {
        name: "user-informstatistics",
    mounted(){
      /*初始化页面用户信息*/
      this.$pagesHelp(this,7,1,"/listUserInform","dealUserLoginInfom");
    },
      methods:{
        /*分页点击事件*/
        handleCurrentChange(val){

          this.$UserserInform(val,this,"/UserInform","dealUserLoginInfom");
        },
        /*提交查询用户信息*/
        submitForm(formName) {

              this.$refs[formName].validate((valid) => {
                if (valid) {

              this.$UserserInform(1,this,"/UserInform","dealUserLoginInfom");
              alert('submit!');
            } else {
              console.log('error submit!!');
              return false;
            }
          });

        }
      },
      data() {
        return {
          userInform:[],
          pagesInform:[],
          dynamicValidateForm: {
            name: '',
            page: ''
          }
        }
      }
    }
</script>

<style scoped>

  .el-table__expanded-cell{
    padding: 0!important;

    /*background-color: black;*/
  }


</style>
