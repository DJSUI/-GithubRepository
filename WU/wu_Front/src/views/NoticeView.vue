<template>
  <div>
    <el-input
      v-model="params.phone"
      style="width: 200px"
      placeholder="请输入公告标题"
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
    <el-button
      type="primary"
      style="margin-left: 10px"
      @click="add()"
    >新增</el-button>


    <div class="about">
      <el-table
        :data="tableData"
        style="width: 100%"
      >

        <el-table-column
          prop="name"
          label="公告标题"
          width="180"
        >
        </el-table-column>
        <el-table-column
          prop="content"
          label="公告内容"
        >
        </el-table-column>

        <el-table-column
          prop="time"
          label="发布时间"
        >
        </el-table-column>

        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              type="primary"
              @click="edit(scope.row)"
            >编辑</el-button>
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
    <div>
      <el-dialog
        title="请填发布内容信息"
        :visible.sync="dialogTableVisible"
        width="30%"
      >
        <el-form :data="form">
          <el-form-item
            label="公告标题"
            label-width="15%"
          >
            <el-input
              v-model="form.name"
              aria-autocomplete="off"
              style="width: 90%;"
            ></el-input>
          </el-form-item>

          <el-form-item
            label="公告内容"
            label-width="15%"
          >
            <el-input
              type="textarea"
              v-model="form.content"
              aria-autocomplete="off"
              style="width: 90%;"
            ></el-input>
          </el-form-item>
        </el-form>
        <div
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="dialogTableVisible = false">cancel</el-button>
          <el-button
            type="primary"
            @click="submit()"
          >yes</el-button>


        </div>

      </el-dialog>
    </div>
  </div>
</template>


<script>
import request from '@/utils/request';

export default {
  data() {
    return {
      tableData: [],
      params: {
        name: '',
        pageNum: 1,
        pageSize: 5
      },
      total: 0,
      dialogTableVisible: false,
      form: {}
    }
  },
  created() {
    this.findBySearch();

  },
  methods: {


    findBySearch() {
      request.get("/notice/search", {
        params: this.params
      }).then(res => {
        if (res.code === '0') {
          this.tableData = res.data.list;
          this.total = res.data.total;

        } else {

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
      request.post("/notice", this.form).then(res => {
        if (res.code === '0') {
          this.$message({
            message: "操作成功",
            type: 'success'
          });
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
      request.delete("/notice/" + id).then(res => {
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
