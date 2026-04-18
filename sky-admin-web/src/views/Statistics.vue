<template>
  <div class="statistics-container">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>今日营业额</template>
          <h2 style="color: #f56c6c;">¥{{ todayTurnover }}</h2>
        </el-card>
      </el-col>
    </el-row>

    <el-card style="margin-top: 20px;">
      <el-date-picker
        v-model="rangeDate"
        type="daterange"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        @change="fetchStatistics"
        style="margin-bottom: 20px;"
      />
      <div ref="chartRef" style="width: 100%; height: 400px;"></div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import * as echarts from 'echarts'
import request from '../utils/request'

const chartRef = ref(null)
const todayTurnover = ref('0.00')
const rangeDate = ref(null)
let chartInstance = null

const formatDate = (date) => {
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  return `${year}-${month}-${day}`
}

const normalizeToStringArray = (value) => {
  if (Array.isArray(value)) return value.map(item => `${item}`)
  if (typeof value === 'string') {
    return value
      .split(',')
      .map(item => item.trim())
      .filter(Boolean)
  }
  return []
}

const normalizeToNumberArray = (value) => {
  const source = Array.isArray(value)
    ? value
    : typeof value === 'string'
      ? value.split(',').map(item => item.trim())
      : []

  return source.map(item => {
    const numericValue = Number(item)
    return Number.isFinite(numericValue) ? numericValue : 0
  })
}

const fetchStatistics = async () => {
  try {
    if (!rangeDate.value) {
      const end = new Date()
      const begin = new Date()
      begin.setTime(begin.getTime() - 3600 * 1000 * 24 * 7)
      rangeDate.value = [begin, end]
    }

    const [begin, end] = rangeDate.value
    const params = {
      begin: formatDate(begin),
      end: formatDate(end)
    }

    const [orderRes, turnoverRes] = await Promise.all([
      request.get('/report/ordersStatistics', { params }),
      request.get('/report/turnoverStatistics', { params })
    ])

    if (orderRes.code === 1) {
      initChart(
        normalizeToStringArray(orderRes.data?.dateList),
        normalizeToNumberArray(orderRes.data?.orderCountList)
      )
    }

    if (turnoverRes.code === 1) {
      const turnoverList = normalizeToNumberArray(turnoverRes.data?.turnoverList)
      todayTurnover.value = turnoverList.length ? turnoverList[turnoverList.length - 1].toFixed(2) : '0.00'
    }
  } catch (error) {
    console.error('统计数据加载失败', error)
  }
}

const initChart = (dateList, orderCountList) => {
  if (!chartRef.value) return

  if (!chartInstance) {
    chartInstance = echarts.init(chartRef.value)
  }

  chartInstance.setOption({
    title: { text: '近七日订单趋势' },
    xAxis: { type: 'category', data: dateList },
    yAxis: { type: 'value' },
    series: [{ data: orderCountList, type: 'line' }]
  })
}

onMounted(fetchStatistics)

onUnmounted(() => {
  if (chartInstance) {
    chartInstance.dispose()
    chartInstance = null
  }
})
</script>
