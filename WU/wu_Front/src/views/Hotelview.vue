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
          prop="img"
          label="酒店封面"
        >

          <template v-slot="scope">
            <el-image
              style="width: 70px; height: 70px; border-radius: 25%;"
              :src="'http://localhost:8080/api/files/' + scope.row.img"
              :preview-src-list="['http://localhost:8080/api/files/' + scope.row.img]"
            >

            </el-image>
          </template>
        </el-table-column>

        <el-table-column
          prop="name"
          label="酒店名称"
          width="180"
        >
        </el-table-column>

        <el-table-column
          prop="price"
          label="酒店价格"
        >
        </el-table-column>
        <el-table-column
          prop="num"
          label="剩余房间数"
        >
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              type="primary"
              @click="edit(scope.row)"
              v-if="user.role === 'ROLE_ADMIN'"
            >编辑</el-button>
            <el-button
              type="primary"
              @click="reserve(scope.row)"
              v-if="user.role !== 'ROLE_ADMIN'"
            >预定</el-button>
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
    <div>
      <el-dialog
        title="请填写酒店信息"
        :visible.sync="dialogTableVisible"
        width="40%"
      >
        <el-form :data="form">

          <el-form-item
            label="酒店名称"
            label-width="20%"
          >
            <el-input
              v-model="form.name"
              aria-autocomplete="off"
            ></el-input>
          </el-form-item>

          <el-form-item
            label="酒店封面"
            label-width="20%"
          >
            <el-upload
              action="http://localhost:8080/api/files/upload"
              :on-success="successUpload"
            >
              <el-button type="primary">Click to upload</el-button>

            </el-upload>
          </el-form-item>
          <el-form-item
            label="酒店价格"
            label-width="20%"
          >
            <el-input
              v-model="form.price"
              aria-autocomplete="off"
            ></el-input>
          </el-form-item>

          <el-form-item
            label="酒店剩余房间数"
            label-width="20%"
          >
            <el-input
              v-model="form.num"
              aria-autocomplete="off"
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
import { h } from 'vue';

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
      request.get("/hotel/search", {
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
      request.post("/hotel", this.form).then(res => {
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
      request.delete("/hotel/" + id).then(res => {
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
    successUpload(res) {
      console.log("res", res);
      this.form.img = res.data;
    },
    down(flag) {
      location.href = 'http://localhost:8080/api/files/' + flag;
    },
    reserve(row) {
      let param = { hotelId: row.id, userId: this.user.id }
      request.post("/reserve", param).then(res => {
        if (res.code === '0') {
          this.$message.success("预定成功");
          this.findBySearch();
        } else {
          this.$message({
            message: res.message,
            type: 'error'
          });
        }
      })
    }

  }


}
</script>
