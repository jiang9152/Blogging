<script setup>
import { Edit, Delete, Search } from "@element-plus/icons-vue";

import { ref } from "vue";

const categorys = ref([
  {
    id: 0,
    categoryName: "",
    categoryAlias: "",
    createTime: "",
    updateTime: "",
  },
]);

const categoryId = ref("");

const state = ref("");

const articles = ref([
  {
    id: 1,
    title: "",
    content: "",
    state: "",
    categoryId: 1,
    createTime: "",
    updateTime: "",
  },
]);

const pageNum = ref(1);
const total = ref(20);
const pageSize = ref(3);

const onSizeChange = (size) => {
  pageSize.value = size;
  articleList();
};
const onCurrentChange = (num) => {
  pageNum.value = num;
  articleList();
};

import {
  articleCategorySvc,
  articleListSvc,
  articleAddSvc,
  articleUpdSvc,
  articleDelSvc,
} from "@/api/article.js";

const articleCategoryList = async () => {
  let result = await articleCategorySvc();
  categorys.value = result.data;
};

const articleList = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    categoryId: categoryId.value ? categoryId.value : null,
    state: state.value ? state.value : null,
  };
  let result = await articleListSvc(params);
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

//新增抽屜
import { QuillEditor } from "@vueup/vue-quill";
import "@vueup/vue-quill/dist/vue-quill.snow.css";

const visibleDrawer = ref(false);
const visibleUpDrawer = ref(false);

const articleModel = ref({
  id: "",
  title: "",
  categoryId: "",
  content: "",
  state: "",
});
const articleUpModel = ref({
  id: "",
  title: "",
  categoryId: "",
  content: "",
  state: "",
});

import { ElMessage, ElMessageBox } from "element-plus";
const addArticle = async (articleState) => {
  articleModel.value.state = articleState;
  let result = await articleAddSvc(articleModel.value);
  ElMessage.success(result.msg ? result.msg : "新增成功");
  visibleDrawer.value = false;
  articleList();
};

//彈窗
const open = (rowData) => {
  ElMessageBox.alert(rowData.content, rowData.title);
};

//編輯
const onUpdate = async (rowData) => {
  articleUpModel.value.id = rowData.id;
  articleUpModel.value.title = rowData.title;
  articleUpModel.value.categoryId = rowData.categoryId;
  articleUpModel.value.content = rowData.content;
  visibleUpDrawer.value = true;
};

const updateArticle = async (articleUpState) => {
  articleUpModel.value.state = articleUpState;
  let result = await articleUpdSvc(articleUpModel.value);
  ElMessage.success(result.msg ? result.msg : "更新成功");
  visibleUpDrawer.value = false;
  articleList();
};
//刪除
const deleteArticle = (rowData) => {
  ElMessageBox.confirm("確認刪除此文章?", "提示", {
    confirmButtonText: "確認",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      let result = await articleDelSvc(rowData.id);
      ElMessage({
        type: "success",
        message: "刪除成功",
      });
      articleList();
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "取消刪除",
      });
    });
};
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>文章管理</span>
        <div class="extra">
          <el-button type="primary" @click="visibleDrawer = true"
            >新增文章</el-button
          >
        </div>
      </div></template
    >
    <el-form inline>
      <el-form-item label="文章分類">
        <el-select
          v-model="categoryId"
          placeholder="請選擇"
          style="width: 200px"
        >
          <el-option
            v-for="c in categorys"
            :label="c.categoryName"
            :value="c.id"
            :key="c.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="狀態">
        <el-select placeholder="請選擇" v-model="state" style="width: 200px">
          <el-option label="發佈" value="發佈"></el-option>
          <el-option label="草稿" value="草稿"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="articleList">搜尋</el-button>
        <el-button
          @click="
            categoryId = '';
            state = '';
          "
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-table :data="articles" style="width: 100%">
      <el-table-column prop="title" label="文章標題" width="400" />
      <el-table-column prop="categoryName" label="分類" />
      <el-table-column prop="createTime" label="發佈時間" />
      <el-table-column prop="state" label="狀態" />
      <el-table-column label="編輯" width="150">
        <template #default="{ row }">
          <el-button
            :icon="Edit"
            circle
            plain
            type="primary"
            @click="onUpdate(row)"
          ></el-button>
          <el-button
            :icon="Delete"
            circle
            plain
            type="danger"
            @click="deleteArticle(row)"
          ></el-button>
          <el-button
            :icon="Search"
            circle
            plain
            type="success"
            @click="open(row)"
          ></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="No data" />
      </template>
    </el-table>

    <el-pagination
      v-model:current-page="pageNum"
      v-model:page-size="pageSize"
      :page-sizes="[3, 5, 10, 15]"
      layout="jumper, total, sizes, prev, pager, next "
      background
      :total="total"
      @size-change="onSizeChange"
      @current-change="onCurrentChange"
      style="margin-top: 10px; justify-content: flex-end"
    />

    <!--新增抽屜-->
    <el-drawer
      ref="drawerRef"
      v-model="visibleDrawer"
      title="新增文章"
      direction="rtl"
      size="50%"
    >
      <el-form :model="articleModel" label-width="100px">
        <el-form-item label="文章標題">
          <el-input
            v-model="articleModel.title"
            placeholder="請輸入標題"
          ></el-input>
        </el-form-item>
        <el-form-item label="文章分類">
          <el-select v-model="articleModel.categoryId" placeholder="請選擇">
            <el-option
              v-for="c in categorys"
              :key="c.id"
              :label="c.categoryName"
              :value="c.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="文章內容">
          <div class="editor">
            <quill-editor
              theme="snow"
              v-model:content="articleModel.content"
              contentType="text"
            ></quill-editor>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addArticle('發佈')">發佈</el-button>
          <el-button type="info" @click="addArticle('草稿')">草稿</el-button>
        </el-form-item>
      </el-form>
    </el-drawer>

    <!--修改抽屜-->
    <el-drawer
      ref="drawerRef"
      v-model="visibleUpDrawer"
      title="更新文章"
      direction="rtl"
      size="50%"
    >
      <el-form :model="articleUpModel" label-width="100px">
        <el-form-item label="文章標題">
          <el-input
            v-model="articleUpModel.title"
            placeholder="請輸入標題"
          ></el-input>
        </el-form-item>
        <el-form-item label="文章分類">
          <el-select v-model="articleUpModel.categoryId" placeholder="請選擇">
            <el-option
              v-for="c in categorys"
              :key="c.id"
              :label="c.categoryName"
              :value="c.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="文章內容">
          <div class="editor">
            <quill-editor
              theme="snow"
              v-model:content="articleUpModel.content"
              contentType="text"
            ></quill-editor>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateArticle('發佈')"
            >發佈</el-button
          >
          <el-button type="info" @click="updateArticle('草稿')">草稿</el-button>
        </el-form-item>
      </el-form>
    </el-drawer>
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
