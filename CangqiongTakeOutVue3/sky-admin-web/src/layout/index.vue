<template>
  <el-container class="layout-container">
    <el-aside width="200px" class="aside">
      <div class="logo">
        <img src="https://element-plus.org/images/element-plus-logo.svg" alt="logo" />
        <span>苍穹外卖</span>
      </div>
      <el-menu 
        :default-active="$route.path" 
        router 
        class="el-menu-vertical" 
        background-color="#304156" 
        text-color="#bfcbd9" 
        active-text-color="#409EFF" 
      > 
        <el-menu-item index="/workspace">
          <el-icon><DataBoard /></el-icon>
          <span>工作台</span>
        </el-menu-item>
        <el-menu-item index="/orders">
          <el-icon><List /></el-icon>
          <span>订单管理</span>
        </el-menu-item>
        <el-menu-item index="/category">
          <el-icon><Menu /></el-icon>
          <span>分类管理</span>
        </el-menu-item>
        <el-menu-item index="/dish">
          <el-icon><Food /></el-icon>
          <span>菜品管理</span>
        </el-menu-item>
        <el-menu-item index="/setmeal">
          <el-icon><Box /></el-icon>
          <span>套餐管理</span>
        </el-menu-item>
        <el-menu-item index="/statistics">
          <el-icon><TrendCharts /></el-icon>
          <span>数据统计</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header class="header">
        <div class="header-left">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/orders' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{ $route.meta.title || '当前页' }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="header-right">
          <el-avatar :size="35" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" style="margin-right: 10px;" />
          <el-dropdown>
            <span class="el-dropdown-link">
              管理员 <el-icon class="el-icon--right"><arrow-down /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="handleLogout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <el-main class="main">
        <el-tabs v-model="activeTab" type="card" closable @tab-remove="removeTab" @tab-click="handleTabClick">
          <el-tab-pane
            v-for="tab in tabs"
            :key="tab.path"
            :label="tab.title"
            :name="tab.path"
          />
        </el-tabs>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router'
import { onMounted, onUnmounted, ref, watch } from 'vue'
import { ElMessage, ElNotification } from 'element-plus'
import { List, Menu, Food, ArrowDown, TrendCharts, DataBoard, Box } from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()

let socket = null
let reconnectTimer = null

const activeTab = ref(route.path)
const tabs = ref([
  { path: '/workspace', title: '工作台' }
])

const addTab = (path, title) => {
  if (!tabs.value.find(tab => tab.path === path)) {
    tabs.value.push({ path, title })
  }
  activeTab.value = path
}

const removeTab = (targetName) => {
  if (targetName === '/workspace') return
  const index = tabs.value.findIndex(tab => tab.path === targetName)
  tabs.value.splice(index, 1)
  if (activeTab.value === targetName) {
    activeTab.value = tabs.value[tabs.value.length - 1].path
    router.push(activeTab.value)
  }
}

const handleTabClick = (tab) => {
  router.push(tab.props.name)
}

watch(() => route.path, (newPath) => {
  activeTab.value = newPath
  const title = route.meta.title || '当前页'
  addTab(newPath, title)
})

const initWebSocket = () => {
  socket = new WebSocket('ws://localhost:8080/ws/admin')

  socket.onopen = () => {
    console.log('WebSocket 连接已建立')
  }

  socket.onmessage = (event) => {
    const data = JSON.parse(event.data)
    if (data.type === 1 || data.type === 2) {
      ElNotification({
        title: data.type === 1 ? '新订单通知' : '客户催单',
        message: data.content,
        type: data.type === 1 ? 'warning' : 'danger',
        duration: 0
      })
      const voice = new SpeechSynthesisUtterance(data.type === 1 ? "您有新的外卖订单" : "客户正在催单，请处理")
      window.speechSynthesis.speak(voice)
    }
  }

  socket.onclose = () => {
    console.warn('WebSocket 已断开，5秒后尝试重连...')
    reconnectTimer = setTimeout(initWebSocket, 5000)
  }

  socket.onerror = (err) => {
    console.error('WebSocket 报错，请检查后端是否开启了 WebSocket 支持', err)
  }
}

const handleLogout = () => {
  localStorage.removeItem('token')
  router.push('/login')
  ElMessage.success('已安全退出')
}

onMounted(() => {
  initWebSocket()
})

onUnmounted(() => {
  socket?.close()
})
</script>

<style scoped>
.layout-container { height: 100vh; }
.aside { background-color: #304156; transition: width 0.3s; }
.logo { height: 60px; display: flex; align-items: center; justify-content: center; color: #fff; font-weight: bold; background: #2b2f3a; gap: 10px; }
.logo img { width: 30px; }
.header { background: #fff; display: flex; align-items: center; justify-content: space-between; border-bottom: 1px solid #e6e6e6; }
.main { background-color: #f0f2f5; padding: 20px; }
.el-menu { border-right: none; }
</style>
