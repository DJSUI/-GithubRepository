<template>
  <div>
    <el-input
      v-model="params.username"
      style="width: 200px"
      placeholder="请输入操作人姓名"
    ></el-input>


    <el-button
      type="warning"
      style="margin-left: 10px"
      @click="findBySearch()"
    >查询</el-button>
    <el-button
      type="warning"
      style="margin-left: 10px"
      @click="reset()"
    >清空</el-button>



    <div class="about">
      <el-table
        :data="tableData"
        style="width: 100%"
      >

        <el-table-column
          prop="name"
          label="操作内容"
          width="180"
        >
        </el-table-column>
        <el-table-column
          prop="time"
          label="操作时间"
        >
        </el-table-column>

        <el-table-column
          prop="username"
          label="操作人"
        >
        </el-table-column>

        <el-table-column
          prop="ip"
          label="IP"
        >
        </el-table-column>



        <el-table-column label="操作">
          <template slot-scope="scope">

            <!-- <el-button type="danger">删除</el-button> -->
            <el-popconfirm
              title="are you sure"
              @confirm="del(scope.row.id)"
            >
              <el-button
                slot="reference"
                type="danger"
              >删除</el-button>
            </el-popconfirm>

          </template>

        </el-table-column>
      </el-table>
    </div>
    <div style="margin-top: 10px">
      <span class="demonstration"></span>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="params.pageNum"
        :page-sizes="[5, 10, 15]"
        :page-size="5"
        layout="total, sizes, prev, pager, next"
        :total="total"
      >
      </el-pagination>
    </div>

  </div>
</template>


<script>
import request from '@/utils/request';

export default {
  data() {
    return {
      input: '',
      tableData: [],
      params: {
        name: '',
        username: '',
        phone: '',
        pageNum: 1,
        pageSize: 5
      },
      total: 0,
      dialogTableVisible: false,
      form: {

      }
    }
  },
  created() {
    this.findBySearch();

  },
  methods: {


    findBySearch() {
      request.get("/log/search", {
        params: this.params
      }).then(res => {
        if (res.code === '0') {
          this.tableData = res.data.list;
          this.total = res.data.total;

        } else {

        }
      })
    },



    reset() {
      this.params = {
        name: "",
        phone: "",
        pageNum: 1,
        pageSize: 5
      }
      this.findBySearch();
    },
    handleSizeChange(pageSize) {
      this.params.pageSize = pageSize;
      this.params.pageNum = 1; // 重置为第一页
      console.log("Page size changed to", pageSize);
      this.findBySearch();
    },
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum;
      console.log("Changed to page number", pageNum);
      this.findBySearch();

    },

    del(id) {
      request.delete("/log/" + id).then(res => {
        if (res.code === '0') {

          this.$message({
            message: "操作成功",
            type: 'success'
          });
          this.findBySearch();
        } else {
          this.$message({
            message: res.message,
            type: 'error'
          });
        }

      })
    }



  },


}
</script>
