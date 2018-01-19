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
    <el-form  ref="dynamicValidateForm" :inline="true" :model="dynamicValidateForm" class="demo-form-inline" style="margin-top: 3%;margin-left: 1%">
      <el-form-item label="账号">
        <el-input spellcheck="false" clearable v-model.trim="dynamicValidateForm.name" placeholder="昵称" style="width: 200px"></el-input>
      </el-form-item>
      <el-form-item label="注册时间" >
        <el-date-picker value-format="yyyy-MM-dd HH:mm:ss"  v-model="pickerdata"  type="daterange" align="right" unlink-panels range-separator="至"
                         start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions2">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="电子邮件" prop="emailAddress" :rules="[
          { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur,change' }
          ]">
        <el-autocomplete spellcheck="false" :fetch-suggestions="querySearch"
                          :trigger-on-focus="false"
                         v-model.trim="dynamicValidateForm.emailAddress"
                          placeholder="电子邮件" style="width: 190px"></el-autocomplete>
      </el-form-item>
      <el-form-item label="用户状态" >
        <el-select  v-model="dynamicValidateForm.isDeleted" placeholder="状态" style="width: 100px">
          <el-option label="——" value="-1"></el-option>
          <el-option label="正常" value="0"></el-option>
          <el-option label="禁用" value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('dynamicValidateForm')">查询</el-button>
      </el-form-item>
    </el-form>

<!--------------------------------------------表格------------------------------------------------------------------->
  <el-table :data="listInform1" style="width: 90%;margin-left: 5%;height:70%;">
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
        <el-button v-if="scope.row.isDeleted==='禁用'" type="success" size="mini" @click="handleEnable(scope.$index, scope.row)">启用</el-button>
        <el-button v-else size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">禁用</el-button>
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

  </div>
</template>

<script>


    export default {
        name: "account-manage",

      data() {
        return {
          listInform1:[],
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
          },
          results:[],
          pickerdata:'',
          dynamicValidateForm: {
            emailAddress: '',
            name:'',
            isDeleted:'-1',
            starTime:'',
            endTime:'',
            page:''
          }

        }
      },
      mounted(){
          /*初始化页面用户信息*/
          this.$pagesHelp(this,7,1,"/listUserInform","dealInfom");
      },
      methods: {
        /*邮箱匹配*/
        querySearch(queryString, cb) {
            var str1=queryString;
            var str2="";
            for(var i in queryString){
              if(queryString[i]==="@"){
                str1=queryString.substring(0,i);
                str2=queryString.substring(i,queryString.length);
                break;
              }
            }
            this.results = this.loadAll();
            var restaurants = this.results;

            var results = str2 ? restaurants.filter(
              this.createFilter(str2)) : restaurants;
              for(var i in results){
                  results[i].value=str1+results[i].value;
              }
            // 调用 callback 返回建议列表的数据
            cb(results);
        },
        loadAll() {
          return [
            { "value": "@qq.com"},
            { "value": "@gmail.com" },
            { "value": "@163.com"},
            { "value": "@163.net" },
            { "value": "@googlemail.com"},
            { "value": "@mail.com" },
            { "value": "@yahoo.com"},
            { "value": "@msn.com"},
            { "value": "@hotmail.com"},
            { "value": "@aol.com" },
            { "value": "@ask.com"},
            { "value": "@live.com" },
          ];
        },
        createFilter(queryString) {
          return (restaurant) => {
            return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
          };
        },
        /*分页点击事件*/
        handleCurrentChange(val){

          this.$UserserInform(val,this,"/UserInform","dealInfom");
        },
        /*启用用户*/
        handleEnable(index, row) {
          var thisVue=this;
          console.log(index, row);
          var json={
            "id":row.id,
            "isDeleted":0,
          };
          thisVue.$updataInform(json,"/updateUserInfrom",this,thisVue,"updateInfom");

        },
        /*禁用用户*/
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
              thisVue.$updataInform(json,"/updateUserInfrom",this,thisVue,"updateInfom");

          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消禁用'
            });
          });
        },
        /*提交查询用户信息*/
        submitForm(formName) {

          this.$refs[formName].validate((valid) => {
            if (valid) {
                if(this.pickerdata==null){
                  this.dynamicValidateForm.starTime=""
                  this.dynamicValidateForm.endTime=""
                }else{
                  this.dynamicValidateForm.starTime=this.pickerdata[0];

                  //this.dynamicValidateForm.endTime=this.pickerdata[1];
                  var endTime = Date.parse(new Date(this.pickerdata[1]))+86400000;

                  this.dynamicValidateForm.endTime=this.$timeFormat(endTime,'yyyy-MM-dd HH:mm:ss');

                }

              this.$UserserInform(1,this,"/UserInform","dealInfom");
              alert('submit!');
            } else {
              console.log('error submit!!');
              return false;
            }
          });

        }
      }
    }


</script>

<style scoped>

</style>
