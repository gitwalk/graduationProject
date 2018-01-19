
//管理员信息管理
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
        <el-input spellcheck="false" clearable v-model.trim="dynamicValidateForm.name" placeholder="账号" style="width: 200px"></el-input>
      </el-form-item>
      <el-form-item label="注册时间" >
        <el-date-picker value-format="yyyy-MM-dd HH:mm:ss"  v-model="pickerdata"  type="daterange" align="right" unlink-panels range-separator="至"
                        start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions2">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="真实姓名">
        <el-input v-model="dynamicValidateForm.realName" placeholder="真实姓名" style="width: 190px"></el-input>
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
    <el-table :data="listInform1" style="width: 90%;height:70%;margin-left: 5%" >
      <el-table-column label="注册日期" width="190px">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.registerTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="账号" width="180px" align="center">
        <template slot-scope="scope">
          <!--<el-popover trigger="hover" placement="top">-->
            <!--<p>性别: {{ scope.row.sex }}</p>-->
            <!--<p>上线次数: {{ scope.row.loginNum }}次</p>-->
            <!--<p>对局次数: {{ scope.row.gameNum }}次</p>-->
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.name }}</el-tag>
            </div>
          <!--</el-popover>-->
        </template>
      </el-table-column>
      <el-table-column label="真实姓名" width="180px" align="center">
        <template slot-scope="scope">{{scope.row.realName}}</template>
      </el-table-column>
      <el-table-column label="密码" width="180px" >
        <template slot-scope="scope">{{scope.row.password}}</template>
      </el-table-column>

      <el-table-column label="账号状态" width="180px" align="center">
        <template slot-scope="scope">{{scope.row.isDeleted}}</template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
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
        name: "admin-accountmanage",
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
          },results:[],
          pickerdata:'',
          dynamicValidateForm: {
            realName: '',
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
        this.$pagesHelp(this,7,1,"/listAdminInform","dealInfom");
      },
      methods: {
        /*分页点击事件*/
        handleCurrentChange(val){

          this.$UserserInform(val,this,"/AdminInform","dealInfom");
        },
        /*启用用户*/
        handleEnable(index, row) {
          var thisVue=this;
          console.log(index, row);
          var json={
            "id":row.id,
            "isDeleted":0,
          };
          thisVue.$updataInform(json,"/updateAdminInfrom",this,thisVue,"updateInfom");
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
            thisVue.$updataInform(json,"/updateAdminInfrom",this,thisVue,"updateInfom");

          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消禁用'
            });
          });
        },
        handleEdit(index, row) {
          console.log(index, row);
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
                var endTime = Date.parse(new Date(this.pickerdata[1]))+86400000;

                this.dynamicValidateForm.endTime=this.$timeFormat(endTime,'yyyy-MM-dd HH:mm:ss');

              }

              this.$UserserInform(1,this,"/AdminInform","dealInfom");
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
