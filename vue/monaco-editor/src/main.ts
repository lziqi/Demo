import { createApp } from 'vue';
import App from './App.vue';


// vuex
import store from '@/store/index';

// 引入element plus
import ElementPlus from 'element-plus';
import 'element-plus/lib/theme-chalk/index.css';

createApp(App).use(store).use(ElementPlus).mount('#app')
