<template>
  <el-card>
    <el-button type="success" icon="Plus" @click="dialogVisible = true">新增菜品</el-button>
    
    <el-dialog v-model="dialogVisible" title="新增菜品" width="600px">
      <el-form :model="dishForm" label-width="100px">
        <el-form-item label="菜品图片">
          <el-upload
            class="avatar-uploader"
            action="/api/common/upload"
            :headers="{ token: localStorage.getItem('token') }"
            :show-file-list="false"
            :on-success="handleUploadSuccess"
          >
            <img v-if="dishForm.image" :src="dishForm.image" class="avatar" style="width: 100px; height: 100px;" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="菜品名称"><el-input v-model="dishForm.name" /></el-form-item>
        <el-form-item label="售价"><el-input-number v-model="dishForm.price" :precision="2" :step="0.1" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button type="primary" @click="saveDish">保存</el-button>
      </template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, reactive } from 'vue'
import request from '../utils/request'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'

const dialogVisible = ref(false)
const dishForm = reactive({ name: '', image: '', price: 0, categoryId: null })

const handleUploadSuccess = (res) => {
  if (res.code === 1) {
    dishForm.image = res.data
    ElMessage.success('上传成功')
  }
}

const saveDish = async () => {
  await request.post('/dish', dishForm)
  ElMessage.success('新增菜品成功')
  dialogVisible.value = false
}
</script>
