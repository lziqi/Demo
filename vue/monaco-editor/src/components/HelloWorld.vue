<template>
  <h1>{{ msg }}</h1>

  <p>
    Recommended IDE setup:
    <a href="https://code.visualstudio.com/" target="_blank">VSCode</a>
    +
    <a
      href="https://marketplace.visualstudio.com/items?itemName=octref.vetur"
      target="_blank"
    >
      Vetur
    </a>
    or
    <a href="https://github.com/johnsoncodehk/volar" target="_blank">Volar</a>
    (if using
    <code>&lt;script setup&gt;</code>)
  </p>

  <p>See <code>README.md</code> for more information.</p>

  <p>
    <a href="https://vitejs.dev/guide/features.html" target="_blank">
      Vite Docs
    </a>
    |
    <a href="https://v3.vuejs.org/" target="_blank">Vue 3 Docs</a>
  </p>

  <button @click="count++">count is: {{ count }},id is {{ id }}</button>
  <iframe
    src="http://localhost:7700/index.html"
    height="800px"
    width="100%"
    id="frame_full"
    frameborder="0"
    scrolling="auto"
    onload="this.style.height=document.body.clientHeight-50"
  ></iframe>
  <p>
    Edit
    <code>components/HelloWorld.vue</code> to test hot module replacement.
  </p>

  <!-- monaco editor -->
  <el-button @click="save">save</el-button>
  <div id="container" class="monaco-editor"></div>
</template>

<script lang="ts">
import { ref, defineComponent, onMounted, onUnmounted } from "vue";
import * as monaco from "monaco-editor";
import { useStore } from "vuex";

import { showAge } from "@/api/test";
import { getFile, putFile } from "@/api/file";
import { ElMessage } from "element-plus";

export default defineComponent({
  name: "HelloWorld",
  props: {
    msg: {
      type: String,
      required: true,
    },
  },
  setup: () => {
    const store = useStore();

    const count = ref(0);
    let id = ref(0);

    let editor;

    onMounted(() => {
      editor = monaco.editor.create(document.getElementById("container"), {
        language: "python",
        value: "",
        fontSize: 18,
        theme: "vs",
      });

      showAge("123");
      getFile("test.py")
        .then((result) => {
          editor.setValue(result.data.data);
          store.commit("setId", result.data.id);
          id.value = result.data.id;
          console.log(store.state.id);
        })
        .catch((err) => {
          console.log(err);
        });
    });

    let save = () => {
      putFile({
        id: id.value,
        data: editor.getValue(),
        name: "test.py",
        type: "code file",
      })
        .then((result) => {
          // console.log(result.data);
          ElMessage.success("保存成功");
        })
        .catch((err) => {
          ElMessage.error("保存失败，请重试");
        });
    };

    return {
      count,
      id,
      save, //保存函数
    };
  },
});
</script>

<style scoped>
.monaco-editor {
  text-align: left;
  width: 680px;
  height: 800px;
  margin: 0 auto;
  border: 1px solid #ccc;
}
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
