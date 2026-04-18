<template>
  <el-card class="box-card">
    <div style="margin-bottom: 20px;">
      <el-button type="primary" :loading="loading" :icon="Refresh" @click="fetchOrders">
        同步最新订单
      </el-button>
    </div>

    <el-table v-loading="loading" :data="orderList" border stripe>
      <el-table-column prop="number" label="订单号" width="180" align="center" />
      <el-table-column prop="consignee" label="收货人" width="110" align="center" />

      <el-table-column prop="phone" label="联系电话" width="150" align="center">
        <template #default="scope">
          <span style="font-weight: bold; color: #409eff;">{{ scope.row.phone }}</span>
        </template>
      </el-table-column>

      <el-table-column prop="amount" label="实付金额" width="110" align="center">
        <template #default="scope">
          <span style="color: #f56c6c;">¥{{ scope.row.amount }}</span>
        </template>
      </el-table-column>

      <el-table-column label="订单详情" min-width="260">
        <template #default="scope">
          <div v-if="scope.row.orderDetailList && scope.row.orderDetailList.length > 0">
            <el-tag
              v-for="item in scope.row.orderDetailList"
              :key="item.id"
              size="small"
              effect="plain"
              style="margin: 2px"
            >
              {{ item.name }} x{{ item.number }}
            </el-tag>
          </div>
          <span v-else style="color: #909399; font-size: 12px;">暂无明细数据</span>
        </template>
      </el-table-column>

      <el-table-column prop="orderTime" label="下单时间" width="180" align="center" />

      <el-table-column label="状态" width="100" align="center">
        <template #default="scope">
          <el-tag :type="getStatusType(scope.row.status)">{{ getStatusText(scope.row.status) }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="120" align="center">
        <template #default="scope">
          <el-button
            v-if="scope.row.status === 2"
            type="primary"
            size="small"
            @click="handleConfirm(scope.row)"
          >
            接单
          </el-button>
          <el-tag v-else-if="scope.row.status === 3" type="success">待派送</el-tag>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '../utils/request'
import { ElMessage } from 'element-plus'
import { Refresh } from '@element-plus/icons-vue'

const orderList = ref([])
const loading = ref(false)

const fetchOrders = async () => {
  loading.value = true
  try {
    const res = await request.get('/order/conditionSearch', {
      params: {
        page: 1,
        pageSize: 10
      }
    })

    if (res.code === 1 && res.data) {
      orderList.value = res.data.records ?? []
      console.log('订单数据加载成功:', orderList.value)
    }
  } catch (error) {
    console.error('订单同步失败', error)
  } finally {
    loading.value = false
  }
}

const handleConfirm = async (row) => {
  try {
    await request.put('/order/confirm', { id: row.id })
    ElMessage.success(`订单 ${row.number} 已接单`)
    fetchOrders()
  } catch (error) {
  }
}

const getStatusType = (status) => {
  const map = {
    1: 'info',
    2: 'warning',
    3: 'primary',
    4: 'primary',
    5: 'success',
    6: 'danger'
  }
  return map[status] || 'info'
}

const getStatusText = (status) => {
  const map = {
    1: '待付款',
    2: '待接单',
    3: '已接单',
    4: '派送中',
    5: '已完成',
    6: '已取消'
  }
  return map[status] || '未知状态'
}

onMounted(fetchOrders)
</script>
