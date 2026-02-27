<template>
  <el-card>
    <div style="margin-bottom: 20px; display: flex; gap: 10px;">
      <el-input v-model="queryParams.name" placeholder="套餐名称" style="width: 200px" clearable />
      <el-button type="primary" icon="Search" @click="fetchSetmeal">查询</el-button>
      <el-button type="success" icon="Plus">新增套餐</el-button>
    </div>

    <el-table v-loading="loading" :data="setmealList" border stripe>
      <el-table-column prop="name" label="套餐名称" />
      <el-table-column label="套餐图片" width="120" align="center">
        <template #default="scope">
          <el-image 
            v-if="scope.row.image" 
            :src="scope.row.image" 
            style="width: 80px; height: 80px;" 
            fit="cover" 
          />
        </template>
      </el-table-column>
      <el-table-column prop="categoryId" label="分类ID" width="100" align="center" />
      <el-table-column prop="price" label="价格" width="100" align="center">
        <template #default="scope">
          ¥{{ scope.row.price }}
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="100" align="center">
        <template #default="scope">
          <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
            {{ scope.row.status === 1 ? '起售' : '停售' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180" align="center">
        <template #default="scope">
          <el-button type="primary" link size="small">修改</el-button>
          <el-button type="danger" link size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <el-pagination 
      style="margin-top: 20px; justify-content: flex-end;" 
      v-model:current-page="queryParams.page" 
      :page-size="queryParams.pageSize" 
      layout="total, prev, pager, next" 
      :total="total" 
      @current-change="fetchSetmeal" 
    />
  </el-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import request from '../utils/request'

const loading = ref(false)
const setmealList = ref([])
const total = ref(0)

const queryParams = reactive({
  name: '',
  categoryId: null,
  status: null,
  page: 1,
  pageSize: 10
})

const fetchSetmeal = async () => {
  loading.value = true
  try {
    const res = await request.get('/setmeal/page', { params: queryParams })
    if (res.code === 1) {
      setmealList.value = res.data.records
      total.value = res.data.total
    }
  } finally {
    loading.value = false
  }
}

onMounted(fetchSetmeal)
</script>
