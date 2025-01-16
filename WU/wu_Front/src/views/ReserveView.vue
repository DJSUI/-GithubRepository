<template>
  <div>
    <el-input
      v-model="params.name"
      style="width: 200px"
      placeholder="请输入酒店名称"
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
      v-if="user.role == 'ROLE_ADMIN'"
    >清空</el-button>
    <el-button
      type="primary"
      style="margin-left: 10px"
      @click="add()"
      v-if="user.role == 'ROLE_ADMIN'"
    >新增</el-button>


    <div class="about">
      <el-table
        :data="tableData"
        style="width: 100%"
      >
        <el-table-column
          prop="hotelName"
          label="酒店名称"
          width="180"
        >
        </el-table-column>

        <el-table-column
          prop="userName"
          label="预定人"
        >
        </el-table-column>
        <el-table-column
          prop="time"
          label="预定时间"
        >
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-popconfirm
              title="are you sure"
              @confirm="del(scope.row.id)"
            >
              <el-button
                slot="reference"
                type="danger"
                v-if="user.role === 'ROLE_ADMIN'"
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
        author: '',
        phone: '',
        pageNum: 1,
        pageSize: 5
      },
      total: 0,
      dialogTableVisible: false,
      form: {

      },
      typeObjs: [],
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    // this.findTypes();
    this.findBySearch();

  },
  methods: {

    findBySearch() {
      request.get("/reserve/search", {
        params: this.params
      }).then(res => {
        if (res.code === '0') {
          this.tableData = res.data.list;
          console.log("teable tata console", this.tableData);

          this.total = res.data.total;

        } else {
          this.$message.error(res.msg)
        }
      })
    },

    add() {
      this.form = {}
      this.dialogTableVisible = true
    },
    edit(obj) {
      this.form = obj;
      this.dialogTableVisible = true
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
    submit() {
      request.post("/reserve", this.form).then(res => {
        if (res.code === '0') {
          this.$message.success("操作成功");
          this.dialogTableVisible = false;
          this.findBySearch();
        } else {
          this.$message({
            message: res.msg,    // 这里需要和后端所定义的属性名保持对其
            type: 'error'
          });
        }
      })
    },
    del(id) {
      request.delete("/reserve/" + id).then(res => {
        if (res.code === '0') {
          this.$message.success("删除成功");
          this.findBySearch();
        } else {
          this.$message({
            message: res.message,
            type: 'error'
          });
        }

      })
    },

  },


}
</script>
