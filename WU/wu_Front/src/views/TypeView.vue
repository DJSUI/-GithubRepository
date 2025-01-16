<template>
  <div>

    <div>
      <el-input
        v-model="params.name"
        style="width: 200px"
        placeholder="请输入分类名称"
      ></el-input>
      <el-input
        v-model="params.description"
        style="width: 200px"
        placeholder="请输入分类描述"
      ></el-input>

    </div>

    <div>
      <el-button
        type="warning"
        style="margin-left: 5px"
        @click="findBySearch()"
      >查询</el-button>
      <el-button
        type="warning"
        style="margin-left: 5px"
        @click="reset()"
      >清空</el-button>
      <el-button
        type="primary"
        style="margin-left: 5px"
        @click="add()"
        v-if="user.role === 'ROLE_ADMIN'"
      >新增</el-button>

      <el-popconfirm
        title="确定删除这些数据吗？"
        @confirm="delBatch()"
      >
        <el-button
          slot="reference"
          type="danger"
          style="margin-left: 5px;"
          v-if="user.role === 'ROLE_ADMIN'"
        >批量删除</el-button>
      </el-popconfirm>
      <el-button
        type="success"
        style="margin-left: 5px"
        @click="exp()"
      >导出报表</el-button>

      <el-upload
        action="http://localhost:8080/api/type/upload"
        style="display: inline-block; margin-left: 5px; "
        :show-file-list="false"
        :on-success="successUpload"
      >
        <el-button type="primary">批量导入</el-button>

      </el-upload>

    </div>




    <div class="about">
      <el-table
        :data="tableData"
        style="width: 100%"
        @selection-change="handleSelectionChange"
        :row-key="getRowkeys"
      >
        <el-table-column
          type="selection"
          width="55"
          :reserve-selection="true"
        >

        </el-table-column>

        <el-table-column
          prop="name"
          label="分类名称"
          width="180"
        >
        </el-table-column>
        <el-table-column
          prop="description"
          label="分类描述"
        >
        </el-table-column>

        <el-table-column
          label="操作"
          v-if="user.role === 'ROLE_ADMIN'"
        >
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
        title="请填写地址"
        :visible.sync="dialogTableVisible"
        width="30%"
      >

        <el-form :data="form">
          <el-form-item
            label="分类名称"
            label-width="15%"
          >
            <el-input
              v-model="form.name"
              aria-autocomplete="off"
              style="width: 90%;"
            ></el-input>
          </el-form-item>

          <el-form-item
            label="分类描述"
            label-width="15%"
          >
            <el-input
              v-model="form.description"
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
      input: '',
      tableData: [],
      params: {
        name: '',
        pageNum: 1,
        pageSize: 5
      },
      total: 0,
      dialogTableVisible: false,
      form: {},
      multipleSelection: [],
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.findBySearch();

  },
  methods: {


    findBySearch() {
      request.get("/type/search", {
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
      request.post("/type", this.form).then(res => {
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
      request.delete("/type/" + id).then(res => {
        if (res.code === '0') {

          this.$message({
            message: "操作成功",
            type: 'success'
          });
          this.findBySearch();
        } else {
          this.$message({
            message: res.msg,
            type: 'error'
          });
        }

      })
    },
    delBatch() {
      if (this.multipleSelection.length === 0) {
        this.$message.warning("请勾选删除的项目")
        return
      }
      request.put("/type/delBatch", this.multipleSelection).then(res => {

        if (res.code === '0') {
          this.$message.success("批量删除成功")
          this.findBySearch();
        } else {
          this.$message.error(res.msg, "批量删除失败")

        }
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },


    getRowkeys(row) {
      return row.id
    },
    exp() {
      let user = localStorage.getItem("user");
      // 如果后端里没有选择放行，那讲token 在前端里，直接放在URL里的parmeter
      location.href = 'http://localhost:8080/api/type/export?token=' + JSON.parse(user).token
    },
    successUpload(res) {
      if (res.code === '0') {
        this.$message.success("批量导入成功")
        this.findBySearch();
      } else {
        this.$message.error(res.msg, "批量导入失败")

      }
    }


  },


}
</script>
