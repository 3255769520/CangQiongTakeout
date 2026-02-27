<template>
  <el-card>
    <div style="margin-bottom: 20px; display: flex; gap: 10px;">
      <el-input v-model="queryParams.name" placeholder="分类名称" style="width: 200px" clearable />
      <el-button type="primary" icon="Search" @click="fetchCategory">查询</el-button>
      <el-button type="success" icon="Plus" @click="openDialog('add')">新增分类</el-button>
    </div>

    <el-table v-loading="loading" :data="categoryList" border stripe>
      <el-table-column type="index" label="序号" width="60" align="center" />
      <el-table-column prop="name" label="分类名称" />
      <el-table-column prop="type" label="类型">
        <template #default="scope">
          {{ scope.row.type === 1 ? '菜品' : '套餐' }}
        </template>
      </el-table-column>
      <el-table-column prop="sort" label="排序" width="80" align="center" />
      <el-table-column label="状态" width="100" align="center">
        <template #default="scope">
          <el-switch v-model="scope.row.status" :active-value="1" :inactive-value="0" @change="handleStatus(scope.row)" />
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180" align="center">
        <template #default="scope">
          <el-button type="primary" link @click="openDialog('edit', scope.row)">修改</el-button>
          <el-button type="danger" link @click="handleDelete(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="formType === 'add' ? '新增分类' : '修改分类'" width="400px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="类型">
          <el-select v-model="form.type"><el-option label="菜品" :value="1" /><el-option label="套餐" :value="2" /></el-select>
        </el-form-item>
        <el-form-item label="排序"><el-input-number v-model="form.sort" :min="0" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </template>
    </el-dialog>
    
    <el-pagination 
      style="margin-top: 20px; justify-content: flex-end;" 
      v-model:current-page="queryParams.page" 
      :page-size="queryParams.pageSize" 
      layout="total, prev, pager, next" 
      :total="total" 
      @current-change="fetchCategory" 
    />
  </el-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import request from '../utils/request'
import { ElMessage, ElMessageBox } from 'element-plus'

const loading = ref(false)
const categoryList = ref([])
const dialogVisible = ref(false)
const formType = ref('add')

const queryParams = reactive({
  name: '',
  page: 1,
  pageSize: 10
})

const form = reactive({
  id: null,
  name: '',
  type: 1,
  sort: 0
})

const fetchCategory = async () => {
  loading.value = true
  const res = await request.get('/category/page', { params: queryParams })
  categoryList.value = res.data.records
  loading.value = false
}

const openDialog = (type, row) => {
  formType.value = type
  dialogVisible.value = true
  if (type === 'edit') Object.assign(form, row)
  else Object.assign(form, { id: null, name: '', type: 1, sort: 0 })
}

const submitForm = async () => {
  const method = formType.value === 'add' ? 'post' : 'put'
  await request[method]('/category', form)
  ElMessage.success('操作成功')
  dialogVisible.value = false
  fetchCategory()
}

const handleDelete = (id) => {
  ElMessageBox.confirm('确定删除吗？', '警告', { type: 'warning' }).then(async () => {
    await request.delete('/category', { params: { id } })
    ElMessage.success('删除成功')
    fetchCategory()
  })
}

const handleStatus = async (row) => {
  try {
    await request.post(`/category/status/${row.status}`, null, { params: { id: row.id } })
    ElMessage.success('修改成功')
  } catch (e) {
    row.status = row.status === 1 ? 0 : 1
  }
}

onMounted(fetchCategory)
</script>
