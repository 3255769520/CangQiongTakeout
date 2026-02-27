<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2>苍穹外卖管理端</h2>
      <el-form :model="loginForm" ref="loginRef">
        <el-form-item prop="username">
          <el-input v-model="loginForm.username" placeholder="账号：admin" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="loginForm.password" type="password" placeholder="密码：123456" />
        </el-form-item>
        <el-button type="primary" style="width: 100%" @click="handleLogin">登录</el-button>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import request from '../utils/request'
import { ElMessage } from 'element-plus'

const router = useRouter()
const loginForm = reactive({
  username: '',
  password: ''
})

const handleLogin = async () => {
  try {
    const res = await request.post('/employee/login', loginForm)
    if (res.code === 1) {
      localStorage.setItem('token', res.data.token)
      ElMessage.success('登录成功')
      router.push('/orders')
    }
  } catch (error) {
    console.error(error)
  }
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f5f7fa;
}
.login-card {
  width: 400px;
  text-align: center;
}
</style>
