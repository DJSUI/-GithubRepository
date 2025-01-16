<template>
  <div>
    <div style="width: 50%; ">
      <div style="margin-bottom: 15px; font-weight: bold; font-size: 18px;">系统公告</div>
      <el-collapse
        v-model="activeName"
        accordion
      >
        <el-collapse-item
          v-for=" item in data"
          :title="item.name"
          :name="item.id"
        >
          <div>{{ item.content }}</div>
        </el-collapse-item>
      </el-collapse>
    </div>
    <div style="width: 50%;">
      <div
        id="bie"
        style="width: 100%; height: 500px;"
      ></div>
    </div>
  </div>
</template>



<script>
import * as echarts from 'echarts';
import request from '@/utils/request';
export default {
  name: 'HomeView',
  data() {
    return {
      activeName: '1',
      data: []
    }
  },
  mounted() {
    this.findNotice();
    this.initEcharts();
  },
  methods: {
    findNotice() {
      request.get('/notice').then(res => {
        if (res.code === '0') {
          this.data = res.data;
          this.activeName = res.data[0].id;

        } else {
          this.$message.error(res.msg);
        }
      })
    },
    initEcharts() {
      request.get('/book/echarts/bie').then(res => {
        if (res.code === '0') {
          this.initBie(res.data);
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    initBie(data) {
      var chartDom = document.getElementById('bie');
      var myChart = echarts.init(chartDom);
      var option;

      option = {
        title: {
          text: '图书统计',
          subtext: '统计维度：图书分类',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: '50%',
            data: data,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };

      option && myChart.setOption(option);
    }
  }
}
</script>