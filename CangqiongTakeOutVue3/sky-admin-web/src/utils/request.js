import axios from 'axios'
import { ElMessage } from 'element-plus'

const service = axios.create({
    baseURL: '/api',
    timeout: 5000
})

service.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers['token'] = token
  }
  return config
}, error => {
  return Promise.reject(error)
})

service.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code !== 1) {
      ElMessage.error(res.msg || '系统错误')
      return Promise.reject(new Error(res.msg))
    }
    return res
  },
  error => {
    if (error.response && error.response.status === 401) {
      ElMessage.error('身份验证失败，请重新登录')
      localStorage.removeItem('token')
    } else {
      ElMessage.error('连接失败，请确认后端已启动')
    }
    return Promise.reject(error)
  }
)

export default service
