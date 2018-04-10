<template>
  <div style="background-color: transparent">

    <el-row :gutter="20">
      <el-col :span="16">
<!--------------------------------------搜索房间号----------------------------------------------------->
        <div>

          <el-row :gutter="20" style="margin-bottom: 10px;">
            <el-col :span="8">
              <el-input
                placeholder="搜索房间号"
                v-model="input10"
                clearable>
              </el-input>
            </el-col>

            <el-col :span="2" :offset="10">
              <el-button type="danger" icon="el-icon-plus" round>创建房间</el-button>


            </el-col>
          </el-row>

        </div>
<!---------------------------------------------------房间列表---------------------------------------------->
        <div class="grid-content bg-purple roomdiv" style="background-color: transparent">
          <el-table
            :data="tableData"
            style="width: 100%;background-color: transparent" >
            <el-table-column label="房间列表">
              <el-table-column
                label="房间号"
                :span="5" >
                <template slot-scope="scope" >
                  <i class="el-icon-info"></i>
                  <span style="margin-left: 10px">{{ scope.row.date }}</span>
                </template>
              </el-table-column>
              <el-table-column
                label="红方"
                :span="5" >
                <template slot-scope="scope">
                  <div slot="reference" class="name-wrapper">
                    <el-tag size="medium">{{ scope.row.name }}</el-tag>
                  </div>
                </template>
              </el-table-column>
              <el-table-column
                label="黑方"
                :span="5">
                <template slot-scope="scope">
                  <div slot="reference" class="name-wrapper">
                    <el-tag size="medium">{{ scope.row.name }}</el-tag>
                  </div>
                </template>
              </el-table-column>
              <el-table-column
                label="游戏状态"
                :span="5">
                <template slot-scope="scope">

                  <span style="margin-left: 10px">{{ scope.row.date }}</span>
                </template>
              </el-table-column>
              <el-table-column
                label="旁观人数"
                :span="5">
                <template slot-scope="scope">

                  <span style="margin-left: 10px">{{ scope.row.date }}</span>
                </template>
              </el-table-column>
              <el-table-column label="操作" :span="5">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    @click="handleEdit(scope.$index, scope.row)">加入</el-button>

                </template>
              </el-table-column>
            </el-table-column>

          </el-table>
        </div>
<!-----------------------------------------------------分页条------------------------------------------>
        <el-row :gutter="24" style="margin-top: 2%">
          <el-col :span="8" :offset="3">
          <el-pagination
            background
            layout="prev, pager, next"
            :total="500">
          </el-pagination>
          </el-col>
        </el-row>
      </el-col>
<!-----------------------------------------在线列表--------------------------------------------------->
      <el-col :span="8">
<!---------------------------------------------------搜索在线玩家------------------------------------------->
        <div class="grid-content bg-purple">
          <div>
            <el-row :gutter="20" style="margin-bottom: 10px;">
              <el-col :span="20">
                <el-input
                  placeholder="搜索玩家昵称"
                  v-model="input10"
                  clearable>
                </el-input>
              </el-col>
            </el-row>
          </div>
<!------------------------------------------------在线表格------------------------------------------->
          <el-table
            :data="tableData6"
            border
            height="590"
            :summary-method="getSummaries"
            show-summary
            style="width: 100%; ">
            <el-table-column label="玩家列表">
              <el-table-column
                prop="id"
                label="账号"
                width="150">
              </el-table-column>
              <el-table-column
                prop="amount3"
                label="状态"
                width="100">
              </el-table-column>
            </el-table-column>

          </el-table>

        </div>
        <!--<el-button type="danger" round size="medium ">创建房间</el-button>-->
      </el-col>
    </el-row>
  </div>
</template>

<script>
    export default {
        name: "gameLobby",
      data() {
        return {
          tableData6: [{
            id: '12987122',
            name: '王小虎',
            amount1: '234',
            amount2: '3.2',
            amount3: 10
          },
            {
              id: '12987122',
              name: '王小虎',
              amount1: '234',
              amount2: '3.2',
              amount3: 10
            },
            {
              id: '12987122',
              name: '王小虎',
              amount1: '234',
              amount2: '3.2',
              amount3: 10
            },
            {
              id: '12987122',
              name: '王小虎',
              amount1: '234',
              amount2: '3.2',
              amount3: 10
            },
            {
              id: '12987122',
              name: '王小虎',
              amount1: '234',
              amount2: '3.2',
              amount3: 10
            },
            {
              id: '12987122',
              name: '王小虎',
              amount1: '234',
              amount2: '3.2',
              amount3: 10
            },
            {
              id: '12987122',
              name: '王小虎',
              amount1: '234',
              amount2: '3.2',
              amount3: 10
            },
            {
            id: '12987123',
            name: '王小虎',
            amount1: '165',
            amount2: '4.43',
            amount3: 12
          }, {
            id: '12987124',
            name: '王小虎',
            amount1: '324',
            amount2: '1.9',
            amount3: 9
          }, {
            id: '12987125',
            name: '王小虎',
            amount1: '621',
            amount2: '2.2',
            amount3: 17
          }, {
            id: '12987126',
            name: '王小虎',
            amount1: '539',
            amount2: '4.1',
            amount3: 15
          }],
          input10: '',
          tableData: [{
            date: '1',
            name: '王小虎',

          }, {
            date: '2',
            name: '王小虎',

          }, {
            date: '3',
            name: '王小虎',

          }, {
            date: '4',
            name: '王小虎',

          }, {
            date: '5',
            name: '王小虎',

          }, {
            date: '6',
            name: '王小虎',

          }, {
            date: '7',
            name: '王小虎',

          }, {
            date: '8',
            name: '王小虎',

          }
          ]
        }
      },
      methods: {
        handleEdit(index, row) {
          console.log(index, row);
        },
        handleDelete(index, row) {
          console.log(index, row);
        },
        getSummaries(param) {
          const { columns, data } = param;
          const sums = [];
          columns.forEach((column, index) => {
            if (index === 0) {
              sums[index] = '在线人数';
              return;
            }
            const values = data.map(item => Number(item[column.property]));
            if (!values.every(value => isNaN(value))) {
              sums[index] = values.reduce((prev, curr) => {
                const value = Number(curr);
                if (!isNaN(value)) {
                  return prev + curr;
                } else {
                  return prev;
                }
              }, 0);
              sums[index] += ' 元';
            } else {
              sums[index] = 'N/A';
            }
          });

          return sums;
        }

      }
    }
</script>

<style scoped>

</style>
<style>
  .roomdiv .roomdiv>* {
    background-color: transparent;
  }
  /*.el-row {*/
    /*margin-bottom: 20px;*/
  /*&:last-child {*/
     /*margin-bottom: 0;*/
   /*}*/
  /*}*/
  /*.el-col {*/
    /*border-radius: 4px;*/
  /*}*/
  /*.bg-purple-dark {*/
    /*background: #99a9bf;*/
  /*}*/
  /*.bg-purple {*/
    /*background: #d3dce6;*/
  /*}*/
  /*.bg-purple-light {*/
    /*background: #e5e9f2;*/
  /*}*/
  /*.grid-content {*/
    /*border-radius: 4px;*/
    /*min-height: 36px;*/
  /*}*/
  /*.row-bg {*/
    /*padding: 10px 0;*/
    /*background-color: #f9fafc;*/
  /*}*/
</style>
