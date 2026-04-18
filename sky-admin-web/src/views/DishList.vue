<template>
  <el-card>
    <!-- 搜索栏 -->
    <div style="margin-bottom: 20px; display: flex; gap: 10px;">
      <el-input v-model="queryParams.name" placeholder="菜品名称" style="width: 200px" clearable />
      <el-select v-model="queryParams.categoryId" placeholder="分类" style="width: 160px" clearable>
        <el-option v-for="cat in categoryOptions" :key="cat.id" :label="cat.name" :value="cat.id" />
      </el-select>
      <el-select v-model="queryParams.status" placeholder="状态" style="width: 120px" clearable>
        <el-option label="起售" :value="1" />
        <el-option label="停售" :value="0" />
      </el-select>
      <el-button type="primary" icon="Search" @click="handleQuery">查询</el-button>
      <el-button type="success" icon="Plus" @click="openDialog('add')">新增菜品</el-button>
    </div>

    <!-- 菜品列表 -->
    <el-table v-loading="loading" :data="dishList" border stripe>
      <el-table-column type="index" label="序号" width="60" align="center" />
      <el-table-column label="菜品图片" width="100" align="center">
        <template #default="scope">
          <el-image v-if="scope.row.image" :src="scope.row.image" style="width:70px;height:70px;" fit="cover" />
          <span v-else style="color:#909399;font-size:12px;">无图片</span>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="菜品名称" />
      <el-table-column prop="categoryName" label="分类" width="120" align="center" />
      <el-table-column prop="price" label="价格" width="100" align="center">
        <template #default="scope">¥{{ scope.row.price }}</template>
      </el-table-column>
      <el-table-column label="状态" width="100" align="center">
        <template #default="scope">
          <el-switch
            v-model="scope.row.status"
            :active-value="1"
            :inactive-value="0"
            @change="handleStatus(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="操作" width="160" align="center">
        <template #default="scope">
          <el-button type="primary" link size="small" @click="openDialog('edit', scope.row)">修改</el-button>
          <el-button type="danger" link size="small" @click="handleDelete(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      style="margin-top:20px;justify-content:flex-end;"
      v-model:current-page="queryParams.page"
      :page-size="queryParams.pageSize"
      layout="total, prev, pager, next"
      :total="total"
      @current-change="fetchDish"
    />

    <!-- 新增/编辑弹窗 -->
    <el-dialog v-model="dialogVisible" :title="formType === 'add' ? '新增菜品' : '修改菜品'" width="560px">
      <el-form :model="dishForm" label-width="100px">
        <el-form-item label="菜品图片">
          <el-upload
            action="/api/common/upload"
            :headers="uploadHeaders"
            :show-file-list="false"
            :on-success="handleUploadSuccess"
          >
            <img v-if="dishForm.image" :src="dishForm.image" style="width:100px;height:100px;object-fit:cover;" />
            <el-icon v-else style="font-size:28px;width:100px;height:100px;border:1px dashed #d9d9d9;display:flex;align-items:center;justify-content:center;"><Plus /></el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="菜品名称">
          <el-input v-model="dishForm.name" />
        </el-form-item>
        <el-form-item label="所属分类">
          <el-select v-model="dishForm.categoryId" placeholder="请选择分类" style="width:100%">
            <el-option v-for="cat in categoryOptions" :key="cat.id" :label="cat.name" :value="cat.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="售价">
          <el-input-number v-model="dishForm.price" :precision="2" :step="1" :min="0" style="width:100%" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="dishForm.description" type="textarea" :rows="2" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitDish">保存</el-button>
      </template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import request from '../utils/request'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'

const loading = ref(false)
const dishList = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formType = ref('add')
const categoryOptions = ref([])

const queryParams = reactive({ name: '', categoryId: null, status: null, page: 1, pageSize: 10 })

const dishForm = reactive({ id: null, name: '', categoryId: null, price: 0, image: '', description: '' })

const uploadHeaders = { token: localStorage.getItem('token') || '' }

const fetchDish = async () => {
  loading.value = true
  try {
    const res = await request.get('/dish/page', { params: queryParams })
    dishList.value = res.data?.records ?? []
    total.value = res.data?.total ?? 0
  } catch {
    dishList.value = []
    total.value = 0
  } finally {
    loading.value = false
  }
}

const fetchCategories = async () => {
  try {
    const res = await request.get('/category/list', { params: { type: 1 } })
    categoryOptions.value = res.data ?? []
  } catch {
    categoryOptions.value = []
  }
}

const handleQuery = () => {
  queryParams.page = 1
  fetchDish()
}

const openDialog = async (type, row) => {
  formType.value = type
  if (type === 'edit' && row) {
    try {
      const res = await request.get(`/dish/${row.id}`)
      const d = res.data
      dishForm.id = d.id
      dishForm.name = d.name
      dishForm.categoryId = d.categoryId
      dishForm.price = d.price
      dishForm.image = d.image
      dishForm.description = d.description
    } catch {
      dishForm.id = row.id
      dishForm.name = row.name
      dishForm.categoryId = row.categoryId
      dishForm.price = row.price
      dishForm.image = row.image
      dishForm.description = row.description
    }
  } else {
    dishForm.id = null
    dishForm.name = ''
    dishForm.categoryId = null
    dishForm.price = 0
    dishForm.image = ''
    dishForm.description = ''
  }
  dialogVisible.value = true
}

const handleUploadSuccess = (res) => {
  if (res.code === 1) {
    dishForm.image = res.data
    ElMessage.success('上传成功')
  }
}

const submitDish = async () => {
  if (!dishForm.name) return ElMessage.warning('请输入菜品名称')
  if (!dishForm.categoryId) return ElMessage.warning('请选择所属分类')
  const payload = {
    id: dishForm.id,
    name: dishForm.name,
    categoryId: dishForm.categoryId,
    price: dishForm.price,
    image: dishForm.image,
    description: dishForm.description,
    flavors: []
  }
  try {
    if (formType.value === 'add') {
      await request.post('/dish', payload)
      ElMessage.success('新增成功')
    } else {
      await request.put('/dish', payload)
      ElMessage.success('修改成功')
    }
    dialogVisible.value = false
    fetchDish()
  } catch { /* request.js 已统一提示 */ }
}

const handleDelete = (id) => {
  ElMessageBox.confirm('确定删除该菜品吗？', '提示', { type: 'warning' }).then(async () => {
    await request.delete('/dish', { params: { ids: id } })
    ElMessage.success('删除成功')
    fetchDish()
  })
}

const handleStatus = async (row) => {
  try {
    await request.post(`/dish/status/${row.status}`, null, { params: { id: row.id } })
    ElMessage.success('状态修改成功')
  } catch {
    row.status = row.status === 1 ? 0 : 1
  }
}

onMounted(() => {
  fetchDish()
  fetchCategories()
})
</script>
