<template>
  <div>
    <el-input
      v-model="params.name"
      style="width: 200px"
      placeholder="请输入图书名称"
    ></el-input>
    <el-input
      v-model="params.author"
      style="width: 200px"
      placeholder="请输入图书作者"
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
      v-if="user.role !== 'ROLE_STUDENT'"
    >新增</el-button>


    <div class="about">
      <el-table
        :data="tableData"
        style="width: 100%"
      >
        <el-table-column
          prop="img"
          label="图书封面"
        >
          <!-- 此处通过从数据库中取得的时间戳来获取保存在本地图片 -->
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
          label="图书名称"
          width="180"
        >
        </el-table-column>
        <el-table-column
          prop="name"
          label="图书介绍"
          width="180"
        >
          <template slot-scope="scope">
            <el-button
              type="success"
              @click="viewEditor(scope.row.content)"
            >点击查看</el-button>
          </template>
        </el-table-column>
        <el-table-column
          prop="author"
          label="图书作者"
        >
        </el-table-column>

        <el-table-column
          prop="price"
          label="图书价格"
        >
        </el-table-column>

        <el-table-column
          prop="press"
          label="图书出版社"
        > </el-table-column>
        <el-table-column
          prop="typeName"
          label="图书分类"
        > </el-table-column>



        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              type="primary"
              @click="edit(scope.row)"
            >编辑</el-button>
            <el-button
              type="primary"
              @click="down(scope.row.img)"
            >下载</el-button>
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
        title="请填写图书信息"
        :visible.sync="dialogTableVisible"
        width="40%"
      >
        <el-form :data="form">

          <el-form-item
            label="图书名称"
            label-width="20%"
          >
            <el-input
              v-model="form.name"
              aria-autocomplete="off"
            ></el-input>
          </el-form-item>

          <el-form-item
            label="图书封面"
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
            label="图书作者"
            label-width="20%"
          >
            <el-input
              v-model="form.author"
              aria-autocomplete="off"
            ></el-input>
          </el-form-item>

          <el-form-item
            label="图书价格"
            label-width="20%"
          >
            <el-input
              v-model="form.price"
              aria-autocomplete="off"
            ></el-input>
          </el-form-item>

          <el-form-item
            label="图书出版社"
            label-width="20%"
          >
            <el-input
              v-model="form.press"
              aria-autocomplete="off"
              style="width: 90%;"
            ></el-input>
          </el-form-item>

          <el-form-item
            label="图书分类"
            label-width="20%"
          >
            <el-select
              v-model="form.typeId"
              placeholder="请选择"
            >
              <el-option
                v-for="item in typeObjs"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            label="图书介绍"
            label-width="20%"
          >
            <div id="editor"></div>
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
    <el-dialog
      title="图书介绍"
      :visible.sync="editorVisible"
      width="50%"
    >
      <div
        v-html="this.form.content"
        class="w-e-text"
      ></div>
    </el-dialog>
  </div>
</template>


<script>
import request from '@/utils/request';
import E from 'wangeditor';

let editor
function initWangEditor(content) {
  setTimeout(() => {
    if (!editor) {
      editor = new E('#editor')
      editor.config.placeholder = '请输内容 Mr.Sui'
      editor.config.uploadFileName = 'file'
      editor.config.uploadImgServer = 'http://localhost:8080/api/files/wang/upload'
      editor.create()
    }
    editor.txt.html(content)
  }, 0
  )
}



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
      editorVisible: false,
      form: {},
      typeObjs: [],
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.findTypes();
    this.findBySearch();

  },
  methods: {
    findTypes() {
      request.get("/type").then(res => {
        if (res.code == '0') {
          this.typeObjs = res.data;
          console.log("获取成功", res);
        } else {
          console.log("获取分类失败", res);
          this.$message.error(res.msg)
        }
      })
    },

    findBySearch() {
      request.get("/book/search", {
        params: this.params
      }).then(res => {
        if (res.code === '0') {
          this.tableData = res.data.list;
          this.total = res.data.total;

        } else {
          console.log("查询失败");

          this.$message.error(res.msg)
        }
      })
    },

    add() {
      this.form = {};
      initWangEditor('')
      this.dialogTableVisible = true
    },
    edit(obj) {
      this.form = obj;
      initWangEditor(obj.content ? this.form.content : "")
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
      this.form.content = editor.txt.html();
      request.post("/book", this.form).then(res => {
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
      request.delete("/book/" + id).then(res => {
        if (res.code === '0' || res == '') {
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
      this.form.img = res.data;
    },
    down(flag) {
      location.href = 'http://localhost:8080/api/files/' + flag;
    },
    viewEditor(data) {
      this.form.content = data;
      console.log("debug vewEditor -> form.content", this.form.content);
      this.editorVisible = true;
    }
  },


}
</script>
