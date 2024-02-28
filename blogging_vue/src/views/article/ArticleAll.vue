<script setup>
import { Edit, Delete } from "@element-plus/icons-vue";

import { ref } from "vue";

const categorys = ref([
  {
    id: 3,
    categoryName: "food",
    categoryAlias: "mm",
    createTime: "",
    updateTime: "",
  },
]);

const categoryId = ref("");

const state = ref("");

const articles = ref([
  {
    id: 0,
    title: "",
    content: "",
    state: "",
    categoryId: 0,
    createTime: "",
    updateTime: "",
  },
]);

const pageNum = ref(1);
const total = ref(1);
const pageSize = ref(5);

const onSizeChange = (size) => {
  pageNum.value = size;
  articleList();
};
const onCurrentChange = (num) => {
  pageNum.value = num;
  articleList();
};

import { articleCategorySvc, articleAllSvc } from "@/api/article.js";

const articleCategoryList = async () => {
  let result = await articleCategorySvc();
  categorys.value = result.data;
};

const articleList = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
  };
  let result = await articleAllSvc(params);
  articles.value = result.data.items;
  total.value = result.data.total;

  for (let i = 0; i < articles.value.length; i++) {
    let article = articles.value[i];
    for (let j = 0; j < categorys.value.length; j++) {
      if (article.categoryId == categorys.value[j].id) {
        article.categoryName = categorys.value[j].categoryName;
      }
    }
  }
};

articleCategoryList();
articleList();

//抽屜
import { QuillEditor } from "@vueup/vue-quill";
import "@vueup/vue-quill/dist/vue-quill.snow.css";

const visibleDrawer = ref(false);
const articleModel = ref({
  title: "",
  categoryId: "",
  content: "",
  state: "",
});

import { ElMessage } from "element-plus";
const addArticle = async (articleState) => {
  articleModel.value.state = articleState;
  let result = await articleAddSvc(articleModel.value);
  ElMessage.success(result.msg ? result.msg : "新增成功");
  visibleDrawer.value = false;
  articleList();
};
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>所有文章</span>
      </div></template
    >

    <el-table :data="articles" style="width: 100%">
      <el-table-column prop="title" label="文章標題" width="400" />
      <el-table-column prop="categoryName" label="分類" />
      <el-table-column prop="createTime" label="發佈時間" />
      <el-table-column prop="state" label="狀態" />

      <template #empty>
        <el-empty description="No data" />
      </template>
      <el-pagination
        v-model:current-page="pageNum"
        v-model:page-size="pageSize"
        :page-size="[3, 5, 10, 15]"
        layout="jumper, total, sizes, prev, pager, next "
        background
        :total="total"
        @size-change="onSizeChange"
        @current-change="onCurrentChange"
        style="margin-top: 20px; justify-content: flex-end"
      />
    </el-table>
  </el-card>
</template>
<style scoped lang="scss">
.page-container {
  min-height: 100%;
  box-sizing: border-box;

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}

.editor {
  width: 100%;
  :deep(.ql-editor) {
    min-height: 200px;
  }
}
</style>
