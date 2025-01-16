<template>
  <div>
    <el-input
      v-model="params.name"
      style="width: 200px"
      placeholder="请输入姓名"
    ></el-input>
    <el-input
      v-model="params.phone"
      style="width: 200px"
      placeholder="请输入电话"
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
          label="姓名"
          width="180"
        >
        </el-table-column>
        <el-table-column
          prop="sex"
          label="性别"
        >
        </el-table-column>

        <el-table-column
          prop="age"
          label="年龄"
        >
        </el-table-column>

        <el-table-column
          prop="phone"
          label="电话"
        >
        </el-table-column>
        <el-table-column
          prop="role"
          label="角色"
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
        title="请填写地址"
        :visible.sync="dialogTableVisible"
        width="30%"
      >
        <el-form :data="form">
          <el-form-item
            label="姓名"
            label-width="15%"
          >
            <el-input
              v-model="form.name"
              aria-autocomplete="off"
              style="width: 90%;"
            ></el-input>
          </el-form-item>

          <el-form-item
            label="性别"
            label-width="15%"
          >

            <el-radio
              v-model="form.sex"
              label="男"
            >男</el-radio>
            <el-radio
              v-model="form.sex"
              label="女"
            >女</el-radio>




          </el-form-item>

          <el-form-item
            label="年龄"
            label-width="15%"
          >
            <el-input
              v-model="form.age"
              aria-autocomplete="off"
              style="width: 90%;"
            ></el-input>
          </el-form-item>

          <el-form-item
            label="电话"
            label-width="15%"
          >
            <el-input
              v-model="form.phone"
              aria-autocomplete="off"
              style="width: 90%;"
            ></el-input>
          </el-form-item>

          <el-form-item
            label="角色"
            label-width="20%"
          >
            <el-select
              v-model="form.role"
              placeholder="请选择"
            >
              <el-option
                label="老师"
                value="ROLE_TEACHER"
              ></el-option>
              <el-option
                label="学生"
                value="ROLE_STUDENT"
              ></el-option>
            </el-select>
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
      request.get("/user/search", {
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
      request.post("/user", this.form).then(res => {
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
      request.delete("/user/" + id).then(res => {
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
