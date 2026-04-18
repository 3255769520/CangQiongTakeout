<template>
  <el-row :gutter="20">
    <el-col :span="6">
      <el-card shadow="hover">
        <template #header>今日订单数</template>
        <div class="stat-value">{{ workspaceData.todayOrders }}</div>
      </el-card>
    </el-col>
    <el-col :span="6">
      <el-card shadow="hover">
        <template #header>今日营业额</template>
        <div class="stat-value revenue">¥{{ workspaceData.todayTurnover }}</div>
      </el-card>
    </el-col>
    <el-col :span="6">
      <el-card shadow="hover">
        <template #header>待处理订单</template>
        <div class="stat-value warning">{{ workspaceData.pendingOrders }}</div>
      </el-card>
    </el-col>
    <el-col :span="6">
      <el-card shadow="hover">
        <template #header>已完成订单</template>
        <div class="stat-value success">{{ workspaceData.completedOrders }}</div>
      </el-card>
    </el-col>
  </el-row>

  <el-card style="margin-top: 20px;">
    <template #header>最新订单</template>
    <el-table :data="recentOrders" border stripe>
      <el-table-column prop="number" label="订单号" width="180" />
      <el-table-column prop="consignee" label="收货人" width="100" />
      <el-table-column prop="amount" label="金额" width="100">
        <template #default="scope">¥{{ scope.row.amount }}</template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template #default="scope">
          <el-tag :type="getStatusType(scope.row.status)">
            {{ getStatusText(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="orderTime" label="下单时间" />
    </el-table>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '../utils/request'

const workspaceData = ref({
  todayTurnover: '0.00',
  todayOrders: 0,
  pendingOrders: 0,
  completedOrders: 0
})

const recentOrders = ref([])

const formatDate = (date) => {
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  return `${year}-${month}-${day}`
}

const parseNumberList = (value) => {
  const source = Array.isArray(value)
    ? value
    : typeof value === 'string'
      ? value.split(',').map(item => item.trim())
      : []

  return source
    .map(item => Number(item))
    .filter(item => Number.isFinite(item))
}

const getLastNumber = (value) => {
  const list = parseNumberList(value)
  return list.length > 0 ? list[list.length - 1] : 0
}

const fetchWorkspaceData = async () => {
  try {
    const today = formatDate(new Date())

    const [orderStatsRes, turnoverRes, pendingRes, recentRes] = await Promise.all([
      request.get('/report/ordersStatistics', { params: { begin: today, end: today } }),
      request.get('/report/turnoverStatistics', { params: { begin: today, end: today } }),
      request.get('/order/conditionSearch', { params: { status: 2, page: 1, pageSize: 1 } }),
      request.get('/order/conditionSearch', { params: { page: 1, pageSize: 10 } })
    ])

    if (orderStatsRes.code === 1) {
      workspaceData.value.todayOrders = orderStatsRes.data?.totalOrderCount ?? 0
      workspaceData.value.completedOrders = orderStatsRes.data?.validOrderCount ?? 0
    }

    if (turnoverRes.code === 1) {
      const todayTurnover = getLastNumber(turnoverRes.data?.turnoverList)
      workspaceData.value.todayTurnover = todayTurnover.toFixed(2)
    }

    if (pendingRes.code === 1) {
      workspaceData.value.pendingOrders = pendingRes.data?.total ?? 0
    }

    if (recentRes.code === 1) {
      recentOrders.value = recentRes.data?.records ?? []
    }
  } catch (error) {
    console.error('工作台数据加载失败', error)
  }
}

const getStatusType = (status) => {
  const typeMap = {
    1: 'info',
    2: 'warning',
    3: 'primary',
    4: 'primary',
    5: 'success',
    6: 'danger'
  }
  return typeMap[status] || 'info'
}

const getStatusText = (status) => {
  const textMap = {
    1: '待付款',
    2: '待接单',
    3: '已接单',
    4: '派送中',
    5: '已完成',
    6: '已取消'
  }
  return textMap[status] || '未知状态'
}

onMounted(fetchWorkspaceData)
</script>

<style scoped>
.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #303133;
}
.stat-value.revenue {
  color: #f56c6c;
}
.stat-value.warning {
  color: #e6a23c;
}
.stat-value.success {
  color: #67c23a;
}
</style>
