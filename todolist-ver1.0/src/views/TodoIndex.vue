<template>
  <v-main class="d-flex flex-column">
    <!-- Header -->
    <div class="header">
      <v-row>
        <v-col cols=4>
          <div
            v-if="timerId || pauseHandle"
            style="width: 200px; 
            display: flex;
            justify-self: flex-end;"
          >
            <h1
              v-if="hours !== 0"
              style="font-size: 60px;"
            >{{ formatTimeUnit(hours) }}</h1>
            <h1
              v-if="hours !== 0"
              style="font-size: 60px;"
            >:</h1>
            <h1 style="font-size: 60px;">{{ formatTimeUnit(minutes) }}</h1>
            <h1 style="font-size: 60px;">:</h1>
            <h1 style="font-size: 60px;">{{ formatTimeUnit(seconds) }}</h1>
          </div>

          <div
            v-if="!timerId && !pauseHandle"
            style="width: 200px;"
          >
            <span>Hour</span>
            <input
              type="text"
              style="background-color: white; 
              color: black; 
              margin-top: 10px;"
              v-model="inputHours"
            >
            <span>Minute</span>
            <input
              type="text"
              style="background-color: white; color: black; margin-top: 10px;"
              v-model="inputMinutes"
            >
          </div>

        </v-col>
        <v-col cols=4>
          <H1 style="text-align: left; height: 100px; font-size: 70px;">Mission</H1>
        </v-col>
        <v-col cols=4>
          <div style="width: 300px; display: flex; flex-direction: column; align-items: flex-end;">
            <v-btn
              v-if="!timerId"
              @click="startTimer"
              style="color: black;"
            >Start</v-btn>
            <v-btn
              v-else
              @click="pauseTimer"
              style="color: black; margin-top: 10px;"
            >Pause</v-btn>
            <v-btn
              @click="cancelTimer"
              style="color: black; margin-top: 10px;"
            >Cancel</v-btn>
          </div>

        </v-col>
      </v-row>

    </div>
    <!-- Main Content -->
    <div class="body">
      TaskList area
    </div>
  </v-main>
</template>

<script>
import { ref, watch, onUnmounted } from 'vue';
export default {
  name: "TodoIndex",
  setup() {

    try {
      const inputHours = ref(0);
      const inputMinutes = ref(0);
      const remaniningSeconds = ref(0);
      const timerId = ref(null);
      const pauseHandle = ref(false);

      const hours = ref(0);
      const minutes = ref(0);
      const seconds = ref(0);
      // Math.floor是用来取得整数的
      watch(remaniningSeconds, (newSeconds) => {
        hours.value = Math.floor(newSeconds / 3600);
        minutes.value = Math.floor((newSeconds % 3600) / 60);
        seconds.value = newSeconds % 60;

      })
      function startTimer() {
        // 确保在设置新的定时器之前清除旧的定时器
        if (timerId.value) {
          clearInterval(timerId.value);
          timerId.value = null;
        }

        // 如果不是从暂停状态恢复，设置新的倒计时
        if (!pauseHandle.value && (inputMinutes.value > 0 || inputHours.value > 0)) {
          remaniningSeconds.value = inputMinutes.value * 60 + inputHours.value * 3600;
          timerId.value = setInterval(() => {
            if (remaniningSeconds.value > 0) {
              remaniningSeconds.value--;
            } else {
              clearInterval(timerId.value);
              timerId.value = null;
              pauseHandle.value = false;
            }
          }, 1000);
        }

        // 如果是从暂停状态恢复
        else if (pauseHandle.value && remaniningSeconds.value > 0) {
          timerId.value = setInterval(() => {
            if (remaniningSeconds.value > 0) {
              remaniningSeconds.value--;
            } else {
              clearInterval(timerId.value);
              timerId.value = null;
              pauseHandle.value = false;
            }
          }, 1000);
        }
      }


      function pauseTimer() {
        clearInterval(timerId.value);
        timerId.value = null;
        pauseHandle.value = true;
        console.log(remaniningSeconds, pauseHandle);


      }

      function cancelTimer() {
        clearInterval(timerId.value);
        timerId.value = null;
        pauseHandle.value = false;
        remaniningSeconds.value = 0;
        inputMinutes.value = 0;
        inputHours.value = 0;
      }
      onUnmounted(() => {
        clearInterval(timerId.value);
      })
      function formatTimeUnit(unit) {
        return unit < 10 ? `0${unit}` : unit.toString();
      }

      return {
        inputHours,   // 使小时输入在模板中可用
        inputMinutes, // 使分钟输入在模板中可用
        hours,        // 使计算的小时在模板中可用
        minutes,      // 使计算的分钟在模板中可用
        seconds,      // 使计算的秒在模板中可用
        timerId,      // 可能用于显示定时器状态
        remaniningSeconds, // 可用于调试或显示剩余秒数
        startTimer,   // 开始定时器的方法
        pauseTimer,    // 暂停定时器的方法
        cancelTimer,   // 取消定时器的方法
        pauseHandle,
        formatTimeUnit
      };
    } catch (error) {
      console.error('error in setup ', error);
    }
  },
  // components: {}
}
</script>


<style scoped>
.header {
  color: #fff;
  background-color: #343537;
  /* 浅灰色背景 */
  padding: 20px;
  /* text-align: center; */
  width: 100%;
  height: 160px;
  /* 宽度撑满容器 */
}

.body {


  background-color: #fff;

  margin-top: 0px;


}

.v-messages {
  display: none;
}
</style>
