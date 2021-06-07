import axios from 'axios';
import { ElMessage } from 'element-plus';

const instance = axios.create({
    baseURL: "http://localhost:8000",
    // timeout: 5000
    headers: {
        'Content-Type': "application/json;charset=utf-8"
    },
    withCredentials: false,
})

instance.interceptors.request.use(
    config => {
        // do something before request is sent
        return config
    },
    error => {
        // do something with request error
        console.log('Error: ' + error.message)  // for debug
        return Promise.reject(error)
    }
)

// response interceptor
instance.interceptors.response.use(
    response => {
        // const res = response.data
        return response
    },
    error => {
        // 响应失败
        console.log(error)  // for debug
        if (!error.response || error.response.data.meta == undefined) {
            ElMessage.error('后端网络异常,请检查后端程序是否运行或检查网络连接!')
            return Promise.reject(error)
        }
        // return Promise.reject(error)
        return error.response.data
    }
)

export default instance;

