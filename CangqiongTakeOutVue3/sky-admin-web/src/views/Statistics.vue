<template>
  <div class="statistics-container">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>今日营收</template>
          <h2 style="color: #f56c6c;">¥ {{ todayTurnover }}</h2>
        </el-card>
      </el-col>
    </el-row>
    
    <el-card style="margin-top: 20px;">
      <div ref="chartRef" style="width: 100%; height: 400px;"></div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'
import request from '../utils/request'

const chartRef = ref(null)
const todayTurnover = ref('0.00')

const fetchStatistics = async () => {
  const res = await request.get('/report/ordersStatistics')
  if (res.code === 1) {
    initChart(res.data.dateList, res.data.orderCountList)
  }
}

const initChart = (dateList, orderCountList) => {
  const myChart = echarts.init(chartRef.value)
  myChart.setOption({
    title: { text: '近七日订单趋势' },
    xAxis: { type: 'category', data: dateList },
    yAxis: { type: 'value' },
    series: [{ data: orderCountList, type: 'line' }]
  })
}

onMounted(fetchStatistics)
</script>
