<template>
  <div>
    <v-container>
      <!-- 搜索栏 -->
      <v-card>
        <v-card-title class="d-flex justify-space-between align-center">
          <div class="d-flex align-center">
            <v-icon style="color: skyblue;">mdi-magnify</v-icon>
            <h6>&emsp; 检索条件</h6>
          </div>
          <v-btn
            tabindex="0"
            @click="searchStudent"
          >检索</v-btn>
          <v-btn
            tabindex="0"
            @click="callAddStudent"
          >添加学生信息</v-btn>
        </v-card-title>
        <v-card-text>
          <div>

            <v-text-field
              style="width: 1000px; margin: 0 auto;"
              clearable
              v-model="keywords"
              outlined
              @keyup.enter="searchStudent"
            ></v-text-field>

          </div>
        </v-card-text>

      </v-card>
      <!-- 删除学生对话框 -->
      <v-dialog
        v-model="showDelDialog"
        max-width="500px"
      >

        <v-card>
          <v-card-title>确认删除</v-card-title>
          <v-card-text>确定要删除该学生信息吗？</v-card-text>
          <v-card-actions>
            <v-btn
              color="red darken-1"
              text
              @click="deleteStudent"
            >确认</v-btn>
            <v-btn
              text
              @click="showDelDialog = false"
            >取消</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <!-- 修改学生信息对话框 -->
      <v-dialog
        v-model="showEditDialog"
        width="760px"
        minx-with="500px"
      >
        <v-card>
          <div style="padding: 20px;">
            <v-form>
              <v-card-text>

                <v-row>
                  <v-col cols="6">
                    <v-text-field
                      v-model="studentInfoBox.studentId"
                      label="学号"
                      readonly
                    ></v-text-field>
                  </v-col>

                  <v-col cols="6">
                    <v-text-field
                      clearable
                      label="姓名"
                      v-model="studentInfoBox.name"
                    ></v-text-field>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col cols="6">
                    <v-select
                      label="性别"
                      v-model="studentInfoBox.gender"
                      :items="['men', 'women']"
                    ></v-select>
                  </v-col>
                  <v-col cols="6">

                    <v-text-field
                      clearable
                      label="年龄"
                      v-model="studentInfoBox.age"
                    ></v-text-field>
                  </v-col>
                </v-row>

              </v-card-text>
            </v-form>
            <v-card-actions>
              <v-btn
                color="red darken-1"
                text
                @click="editStudent"
              >确认</v-btn>
              <v-btn
                text
                @click="showEditDialog = false"
              >取消</v-btn>
            </v-card-actions>
          </div>
        </v-card>
      </v-dialog>

      <!-- 增加学生对话框  -->
      <v-dialog
        v-model="showAddDialog"
        width="760px"
        minx-with="500px"
      >
        <v-card>
          <div style="padding: 20px;">
            <v-form>
              <v-card-text>

                <v-row>
                  <v-col cols="6">
                    <v-text-field
                      v-model="studentInfoBox.studentId"
                      label="学号"
                    ></v-text-field>
                  </v-col>

                  <v-col cols="6">
                    <v-text-field
                      clearable
                      label="姓名"
                      v-model="studentInfoBox.name"
                    ></v-text-field>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col cols="6">
                    <v-select
                      label="性别"
                      v-model="studentInfoBox.gender"
                      :items="['men', 'women']"
                    ></v-select>
                  </v-col>
                  <v-col cols="6">

                    <v-text-field
                      clearable
                      label="年龄"
                      v-model="studentInfoBox.age"
                    ></v-text-field>
                  </v-col>
                </v-row>

              </v-card-text>
            </v-form>
            <v-card-actions>
              <v-btn
                color="red darken-1"
                text
                @click="addStudent"
              >添加</v-btn>
              <v-btn
                text
                @click="showAddDialog = false"
              >取消</v-btn>
            </v-card-actions>
          </div>
        </v-card>
      </v-dialog>

      <v-data-table
        :headers="headers"
        :items="students"
        item-key="id"
        :items-per-page="-1"
        hide-default-footer
      >
        <template v-slot:[`item.No`]="{ index }">
          {{ index + 1 }}
        </template>
        <template v-slot:[`item.studentId`]="{ item }">
          {{ item.studentId }}
        </template>
        <template v-slot:[`item.name`]="{ item }">
          {{ item.name }}
        </template>
        <template v-slot:[`item.gender`]="{ item }">
          {{ item.gender }}
        </template>
        <template v-slot:[`item.age`]="{ item }">
          {{ item.age }}
        </template>
        <template v-slot:item="{ item, index }">
          <!-- 获取到的数据显示深色和浅色 -->
          <tr :style="{ 'background-color': index % 2 === 0 ? '#f0f0f0' : 'white' }">
            <td>{{ index + 1 }}</td>
            <td>{{ item.studentId }}</td>
            <td>{{ item.name }}</td>
            <td>{{ item.gender }}</td>
            <td>{{ item.age }}</td>
            <td>
              <v-icon @click="callEditStudent(item)">mdi-pencil</v-icon>
              <!-- <v-icon @click="deleteStudent(item)">mdi-delete</v-icon> -->
              <v-icon @click="callDeleteStudent(item)">mdi-delete</v-icon>

            </td>
          </tr>
        </template>
      </v-data-table>
    </v-container>
  </div>
</template>


<script>
import axios from 'axios'
export default {
  name: 'StudentInfo',
  data() {
    return {
      drawer: null,
      // 学生表header
      headers: [
        { text: 'No', value: 'No' },
        // 以下需要mysql数据库提供
        { text: '学号', value: 'studentId' },
        { text: '姓名', value: 'name' },
        { text: '性别', value: 'gender' },
        { text: '年龄', value: 'age' },
        //* *******************
        { text: '操作', value: 'actions', sortable: false }
      ],
      students: [],
      studentToDelete: null,
      studentInfoBox: {
        studentId: null,
        name: null,
        gender: null,
        age: ''
      },
      showDelDialog: false,
      showEditDialog: false,
      showAddDialog: false,
      keywords: '',


    }
  },
  mounted() {
    // 挂载时候触发fetchStudents 函数
    this.fetchStudents()
    console.log('!!!!' + this.students)
  },
  methods: {
    //  取得学生信息逻辑 ************************
    fetchStudents() {
      axios.get('/Students')
        .then(response => {
          console.log('!!!! zhao dao le ' + response.data)
          // 真实API students 形式是一群对象的形式，使用空数组接收形式： this.students = response.data;
          // MOCKAPI students 形式是父对象包含子对象，使用空数组接收形式： this.students = response.data.data;
          this.students = response.data


        })
        .catch(error => {
          console.error('!!!!mei zhao dao Error fetching students:', error)
        })
    },

    // 删除学生信息逻辑************************
    callDeleteStudent(student) {

      this.studentToDelete = student
      this.showDelDialog = true

    },
    deleteStudent() {

      if (this.studentToDelete) {
        // 获取到的学生ID信息逻辑********
        console.log("catch studnet ID !!" + this.studentToDelete.studentId);

        axios.delete(`http://localhost:8085/api/Students/${this.studentToDelete.studentId}`)
          .then(response => {

            this.fetchStudents() // 重新获取数据以更新页面
          })
          .catch(error => {
            console.error('Error deleting student:', error)

          })
          .finally(() => {
            this.showDelDialog = false

          })
        // TODO：【优先级5】 替换为正常弹出提示信息 
        alert('学生信息删除成功');
      }


    },
    // 修改学生信息逻辑********************
    callEditStudent(student) {

      this.studentInfoBox.studentId = student.studentId
      this.studentInfoBox.name = student.name
      this.studentInfoBox.gender = student.gender
      this.studentInfoBox.age = student.age
      this.showEditDialog = true

    },
    editStudent() {

      if (this.studentInfoBox) {
        // 将对象信息序列化成json字符串传到后端
        let jsonData = JSON.stringify(this.studentInfoBox);
        // 调用后端接口保存编辑后的学生信息
        axios.put(`http://localhost:8085/api/editStudent/${this.studentInfoBox.studentId}`, jsonData, {
          headers: {
            'Content-Type': 'application/json'
          }
        })
          .then(response => {
            console.log('学生信息已更新');

            // 更新成功后刷新页面或重新获取学生列表
            this.fetchStudents();
          })
          .catch(error => {
            console.error('更新学生信息时出错:', error);
          })
          .finally(() => {
            this.showEditDialog = false
          })

      }
    },
    // 搜索学生信息逻辑********************
    searchStudent() {

      if (this.keywords && this.keywords.trim() !== '') {
        // 获取到的学生ID信息
        console.log("catch keywords !!" + this.keywords);

        axios.get(`http://localhost:8085/api/Students/search?keywords=${this.keywords}`)
          .then(response => {
            this.students = response.data
          })
          .catch(error => {
            console.error('!!!!edit wrong ', error)
          })
      } else {
        axios.get('http://localhost:8085/api/Students')
          .then(response => {
            this.students = response.data
            console.log("搜索条件为空，获取所有数据");

          })
          .catch(error => {
            console.error('!!!!mei zhao dao Error fetching students:', error)
          })
      }


    },
    //   增加学生信息逻辑**********************
    callAddStudent() {

      for (const key in this.studentInfoBox) {
        this.studentInfoBox[key] = null;
      }
      this.showAddDialog = true


    },
    addStudent() {
      if (this.studentInfoBox) {
        // 将对象信息序列化成json字符串传到后端
        let jsonData = JSON.stringify(this.studentInfoBox);
        // 调用后端接口保存编辑后的学生信息
        axios.put(`http://localhost:8085/api/addStudent/${this.studentInfoBox.studentId}`, jsonData, {
          headers: {
            'Content-Type': 'application/json'
          }
        })
          .then(response => {
            console.log('学生信息已添加');

            // 更新成功后刷新页面或重新获取学生列表
            this.fetchStudents();
          })
          .catch(error => {
            console.error('添加学生信息时出错:', error);
          })
          .finally(() => {
            this.showAddDialog = false
          })

      }
    }

  }

}

</script>
