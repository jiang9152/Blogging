<script setup>
import { Edit, Delete } from "@element-plus/icons-vue";
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

import {
  articleCategorySvc,
  articleCategoryAddSvc,
  articleCategoryUpdSvc,
  articleCategoryDelSvc,
} from "@/api/article.js";

const articleCategoryList = async () => {
  let result = await articleCategorySvc();
  categorys.value = result.data;
};
articleCategoryList();

//彈窗
const dialogVisible = ref(false);
const categoryModel = ref({ categoryName: "", categoryAlias: "" });
const rules = {
  categoryName: [
    { required: true, message: "請輸入分類名稱", trigger: "blur" },
  ],
  categoryAlias: [
    { required: true, message: "請輸入分類別名", trigger: "blur" },
  ],
};

import { ElMessage, ElMessageBox } from "element-plus";
const addCategory = async () => {
  let result = await articleCategoryAddSvc(categoryModel.value);
  ElMessage.success(result.msg ? result.msg : "新增成功");
  articleCategoryList();
  dialogVisible.value = false;
};

const title = ref("");

const showDialog = (row) => {
  dialogVisible.value = true;
  title.value = "新增分類";
  categoryModel.value.categoryName = row.categoryName;
  categoryModel.value.categoryAlias = row.categoryAlias;
  categoryModel.value.id = row.id;
};

const updateCategory = async () => {
  let result = await articleCategoryUpdSvc(categoryModel.value);
  ElMessage.success(result.msg ? result.msg : "更新成功");
  articleCategoryList;
  dialogVisible.value = false;
};

const clearData = () => {
  categoryModel.value.categoryName = "";
  categoryModel.value.categoryAlias = "";
};

const deleteCategory = (row) => {
  ElMessageBox.confirm("確認刪除此分類?", "提示", {
    confirmButtonText: "確認",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      let result = await articleCategoryDelSvc(row.id);
      ElMessage({
        type: "success",
        message: "刪除成功",
      });
      articleCategoryList();
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
        <span>文章分類</span>
        <div class="extra">
          <el-button
            type="primary"
            @click="
              dialogVisible = true;
              title = '新增分類';
              clearData();
            "
            >新增分類</el-button
          >
        </div>
      </div>
    </template>
    <el-table :data="categorys" style="width: 100%">
      <el-table-column label="序號" width="100" type="index"></el-table-column>
      <el-table-column label="分類名稱" prop="categoryName"></el-table-column>
      <el-table-column label="分類別名" prop="categoryAlias"></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button
            :icon="Edit"
            circle
            plain
            type="primary"
            @click="showDialog(row)"
          ></el-button>
          <el-button
            :icon="Delete"
            circle
            plain
            type="danger"
            @click="deleteCategory(row)"
          ></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="No Data"></el-empty>
      </template>
    </el-table>

    <!--彈窗-->
    <el-dialog v-model="dialogVisible" :title="title" width="30%">
      <el-form
        :model="categoryModel"
        :rules="rules"
        label-width="100px"
        style="padding-right: 30px"
      >
        <el-form-item label="分類名稱" prop="categoryName"
          ><el-input
            v-model="categoryModel.categoryName"
            minlength="1"
            maxlength="10"
          ></el-input
        ></el-form-item>
        <el-form-item label="分類別名" prop="categoryAlias"
          ><el-input
            v-model="categoryModel.categoryAlias"
            minlength="1"
            maxlength="10"
          ></el-input
        ></el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button
            type="primary"
            @click="title == '新增分類' ? addCategory() : updateCategory()"
            >確認</el-button
          >
        </span>
      </template>
    </el-dialog>
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
</style>
