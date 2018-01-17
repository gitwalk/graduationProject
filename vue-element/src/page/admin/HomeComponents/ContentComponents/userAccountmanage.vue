//用户信息管理
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
    <el-form  :inline="true" :model="formInline" class="demo-form-inline" style="margin-top: 3%;margin-left: 1%">
      <el-form-item label="账号">
        <el-input v-model="formInline.user" placeholder="昵称" style="width: 200px"></el-input>
      </el-form-item>
      <el-form-item label="注册时间" >
        <el-date-picker  v-model="value7"  type="daterange" align="right" unlink-panels range-separator="至"
                         start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions2">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="电子邮件" >
        <el-input v-model="formInline.user" placeholder="电子邮件" style="width: 190px"></el-input>
      </el-form-item>
      <el-form-item label="用户状态" >
        <el-select v-model="formInline.region" placeholder="状态" style="width: 100px">
          <el-option label="启用" value="0"></el-option>
          <el-option label="禁用" value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
      </el-form-item>
    </el-form>

<!--------------------------------------------表格------------------------------------------------------------------->
  <el-table :data="listUserInform1" style="width: 90%;margin-left: 5%;height:70%;">
    <el-table-column label="注册日期" width="190px" align="center">
      <template slot-scope="scope">
        <i class="el-icon-time"></i>
        <span style="margin-left: 10px"> {{ scope.row.registerTime }}</span>
      </template>
    </el-table-column>
    <el-table-column label="账号" width="180px" align="center">
      <template slot-scope="scope">
        <el-popover trigger="hover" placement="top">
          <p>性别: {{ scope.row.sex }}</p>
          <p>上线次数: {{ scope.row.loginNum }}次</p>
          <p>对局次数: {{ scope.row.gameNum }}次</p>
          <input hidden :value="scope.row.id"/>
          <div slot="reference" class="name-wrapper">
            <el-tag size="medium">{{ scope.row.name }}</el-tag>
          </div>
        </el-popover>
      </template>
    </el-table-column>
    <el-table-column label="密码" width="180px" >
      <template slot-scope="scope">{{scope.row.password}}</template>
    </el-table-column>
    <el-table-column label="电子邮件" width="180px" >
      <template slot-scope="scope">{{scope.row.emailAddress}}</template>
    </el-table-column>
    <el-table-column label="用户状态" width="180px" align="center">
      <template slot-scope="scope">{{scope.row.isDeleted}}</template>
    </el-table-column>
    <el-table-column label="操作" align="center">
      <template slot-scope="scope">
        <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
        <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">禁用</el-button>
      </template>
    </el-table-column>
  </el-table>

<!------------------------------------------------------分页条------------------------------------------------------>
    <el-row :gutter="24" style="margin-top: 2%">
      <el-col :span="8" :offset="6">
        <el-pagination    @current-change="handleCurrentChange" current-page.sync="pagesInform.pageNum"
                         :page-size="pagesInform.count" background layout="total,prev, pager, next, jumper"
                        :total="pagesInform.pagetotal" ></el-pagination>
      </el-col>
    </el-row>

  </div>
</template>

<script>


  function dealInfom(thisVue,response) {

    for(var i in response.data[0]) {
      //转换性别
      if(response.data[0][i].sex=="0"){
        response.data[0][i].sex="男";
      }else if(response.data[0][i].sex=="1"){
        response.data[0][i].sex="女";
      }else if(response.data[0][i].sex=="2"){
        response.data[0][i].sex="保密";
      }
      //转换状态
      if(response.data[0][i].isDeleted=="0"){
        response.data[0][i].isDeleted="正常";
      }else{
        response.data[0][i].isDeleted="禁用";
      }

      response.data[0][i].registerTime=thisVue.$timeFormat(response.data[0][i].registerTime,'yyyy-MM-dd HH:mm:ss');
      console.log(response.data[0][i].sex)
    }
    response.data[1].pageNum++;
    thisVue.listUserInform1=response.data[0];
    thisVue.pagesInform=response.data[1];
  }
    export default {
        name: "account-manage",
      data() {
        return {
          listUserInform1:[],
          pagesInform:[],
          pickerOptions2: {
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
          },
          value6: '',
          value7: '',
          formInline: {
            user: '',
            region: ''
          },
          tableData: [{
            registerTime: '2016-05-02',
            name: 'a794922102',
            sex: '男',
            loginNum:'10',
            gameNum:'20',
            emailAdress:'123456@qq.com',
            userState:'启用',
            Password:'794952112'
          }, {
            registerTime: '2016-05-02',
            name: '王小虎',
            sex: '男',
            loginNum:'10',
            gameNum:'20',
            emailAdress:'123456@qq.com',
            userState:'启用',
            Password:'794952112'
          }, {
            registerTime: '2016-05-02',
            name: '王小虎',
            sex: '男',
            loginNum:'10',
            gameNum:'20',
            emailAdress:'123456@qq.com',
            userState:'启用',
            Password:'794952112'
          }, {
            registerTime: '2016-05-02',
            name: '王小虎',
            sex: '男',
            loginNum:'10',
            gameNum:'20',
            emailAdress:'123456@qq.com',
            userState:'启用',
            Password:'794952112'
          }, {
            registerTime: '2016-05-02',
            name: '王小虎',
            sex: '男',
            loginNum:'10',
            gameNum:'20',
            emailAdress:'123456@qq.com',
            userState:'启用',
            Password:'794952112'
          }, {
            registerTime: '2016-05-02',
            name: '王小虎',
            sex: '男',
            loginNum:'10',
            gameNum:'20',
            emailAdress:'123456@qq.com',
            userState:'启用',
            Password:'794952112'
          }, {
            registerTime: '2016-05-02',
            name: '王小虎',
            sex: '男',
            loginNum:'10',
            gameNum:'20',
            emailAdress:'123456@qq.com',
            userState:'启用',
            Password:'794952112'
          }]
        }
      },

      mounted(){
          this.$pagesHelp(this,7,0,"/listUserInform",dealInfom);
      },
      methods: {
        handleCurrentChange(val){

          this.$pagesHelp(this,7,val,"/listUserInform",dealInfom);
        },
        handleEdit(index, row) {
          console.log(index, row);
        },
        handleDelete(index, row) {
          var thisVue=this;
          console.log(index, row);
          var json={
            "id":row.id,
            "isDeleted":1,
          }
          this.$confirm('此操作将禁用该用户, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
              thisVue.$updataInform(json,"/updateUserInfrom",this,thisVue);
            console.log(thisVue.listUserInform1);
              var a=1;
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消禁用'
            });
          });
        },
        onSubmit() {

          console.log('submit!');

        }
      }
    }


</script>

<style scoped>

</style>
