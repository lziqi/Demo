<script setup lang="ts">
import { ref } from "vue";

import Cookies from "js-cookie";

defineProps<{ msg: string }>();

// 创建一个为期为7天的cookie
Cookies.set("name", "lziqi", { expires: 7, path: "" });

// 获取cookie
console.log(Cookies.get("name"));
//返回的是string，需要使用则需要转换,json string 转 object
// console.log(JSON.parse(Cookies.get("name")));

// 删除cookie
Cookies.remove('name', { path: '' });//设置的时候有path就需要path，没有就不需要

const count = ref(0);
</script>

<template>
  <h1>{{ msg }}</h1>

  <p>
    Recommended IDE setup:
    <a href="https://code.visualstudio.com/" target="_blank">VSCode</a>
    +
    <a href="https://github.com/johnsoncodehk/volar" target="_blank">Volar</a>
  </p>

  <p>See <code>README.md</code> for more information.</p>

  <p>
    <a href="https://vitejs.dev/guide/features.html" target="_blank">
      Vite Docs
    </a>
    |
    <a href="https://v3.vuejs.org/" target="_blank">Vue 3 Docs</a>
  </p>

  <button type="button" @click="count++">count is: {{ count }}</button>
  <p>
    Edit
    <code>components/HelloWorld.vue</code> to test hot module replacement.
  </p>
</template>

<style scoped>
a {
  color: #42b983;
}

label {
  margin: 0 0.5em;
  font-weight: bold;
}

code {
  background-color: #eee;
  padding: 2px 4px;
  border-radius: 4px;
  color: #304455;
}
</style>
