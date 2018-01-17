
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
        <el-form  :inline="true" :model="formInline" class="demo-form-inline" >
          <el-form-item label="账号">
            <el-input v-model="formInline.user" placeholder="昵称"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">查询</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <!--------------------------------------------表格------------------------------------------------------------------->
  <el-table :data="tableData5"  style="width: 100%" height="440" >
    <el-table-column type="expand" >
      <template slot-scope="props" >
        <el-table   :data="tableData3"   height="250" border  style="width: 820px;margin-left: 200px" >
          <el-table-column :resizable=false width="200" prop="date" label="登录IP地址" ></el-table-column>
          <el-table-column  :resizable=false width="200" prop="date" label="上线时间" ></el-table-column>
          <el-table-column :resizable=false width="200" prop="name" label="下线时间"></el-table-column>
          <el-table-column  :resizable=false width="200" prop="address" label="在线时长"></el-table-column>
        </el-table>
      </template>
    </el-table-column>
    <el-table-column label="用户账号" prop="id"></el-table-column>
    <el-table-column label="在线时间" prop="name"></el-table-column>
    <el-table-column label="对局次数" prop="desc"></el-table-column>
    <el-table-column label="登录次数" prop="desc"></el-table-column>
    <el-table-column label="胜率" prop="desc"></el-table-column>
  </el-table>
    <!------------------------------------------------------分页条------------------------------------------------------>
    <el-row :gutter="24" style="margin-top: 2%">
      <el-col :span="8" :offset="8">
        <el-pagination  background layout="prev, pager, next" :total="1000" ></el-pagination>
      </el-col>
    </el-row>
  </div>
</template>
<script>

    export default {
        name: "user-informstatistics",
      methods:{
        onSubmit() {

          console.log('submit!');
        }
      },
      data() {
        return {

          formInline: {
            user: '',
            region: ''
          },
          tableData5: [{
            id: '12987122',
            name: '好滋好味鸡蛋仔',
            category: '江浙小吃、小吃零食',
            desc: '荷兰优质淡奶，奶香浓而不腻',
            address: '上海市普陀区真北路',
            shop: '王小虎夫妻店',
            shopId: '10333'
          }, {
            id: '12987122',
            name: '好滋好味鸡蛋仔',
            category: '江浙小吃、小吃零食',
            desc: '荷兰优质淡奶，奶香浓而不腻',
            address: '上海市普陀区真北路',
            shop: '王小虎夫妻店',
            shopId: '10333'
          }, {
            id: '12987122',
            name: '好滋好味鸡蛋仔',
            category: '江浙小吃、小吃零食',
            desc: '荷兰优质淡奶，奶香浓而不腻',
            address: '上海市普陀区真北路',
            shop: '王小虎夫妻店',
            shopId: '10333'
          }, {
            id: '12987123',
            name: '好滋好味鸡蛋仔',
            category: '江浙小吃、小吃零食',
            desc: '荷兰优质淡奶，奶香浓而不腻',
            address: '上海市普陀区真北路',
            shop: '王小虎夫妻店',
            shopId: '10333'
          }, {
            id: '12987123',
            name: '好滋好味鸡蛋仔',
            category: '江浙小吃、小吃零食',
            desc: '荷兰优质淡奶，奶香浓而不腻',
            address: '上海市普陀区真北路',
            shop: '王小虎夫妻店',
            shopId: '10333'
          },{
            id: '12987123',
            name: '好滋好味鸡蛋仔',
            category: '江浙小吃、小吃零食',
            desc: '荷兰优质淡奶，奶香浓而不腻',
            address: '上海市普陀区真北路',
            shop: '王小虎夫妻店',
            shopId: '10333'
          },{
            id: '12987123',
            name: '好滋好味鸡蛋仔',
            category: '江浙小吃、小吃零食',
            desc: '荷兰优质淡奶，奶香浓而不腻',
            address: '上海市普陀区真北路',
            shop: '王小虎夫妻店',
            shopId: '10333'
          }],
          tableData3: [{
            date: '2016-05-03',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1518 弄'
          }, {
            date: '2016-05-02',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1518 弄'
          }, {
            date: '2016-05-04',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1518 弄'
          }, {
            date: '2016-05-01',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1518 弄'
          }, {
            date: '2016-05-08',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1518 弄'
          }, {
            date: '2016-05-06',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1518 弄'
          }, {
            date: '2016-05-07',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1518 弄'
          }]
        }
      }
    }
</script>

<style >

  .el-table__expanded-cell{
    padding: 0!important;

    /*background-color: black;*/
  }


</style>
