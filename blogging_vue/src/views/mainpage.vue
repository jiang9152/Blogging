<script setup>
import {
  Management,
  Promotion,
  UserFilled,
  User,
  EditPen,
  SwitchButton,
  CaretBottom,
} from "@element-plus/icons-vue";

import { userInfoSvc } from "@/api/user.js";
import { useUserInfoStore } from "@/stores/userinfo.js";
import { useTokenStore } from "@/stores/token.js";

const tokenStore = useTokenStore();
const infoStore = useUserInfoStore();

const getUserInfo = async () => {
  let result = await userInfoSvc();

  infoStore.setInfo(result.data);
};

getUserInfo();

import { useRouter } from "vue-router";
import { ElMessage, ElMessageBox } from "element-plus";
const router = useRouter();
const handleCommand = (command) => {
  if (command == "logout") {
    ElMessageBox.confirm("是否確認要登出?", "提示", {
      confirmButtonText: "確認",
      cancelButtonText: "取消",
      type: "warning",
    })
      .then(async () => {
        tokenStore.removeToken();
        infoStore.removeInfo();

        router.push("/login");
        ElMessage({
          type: "success",
          message: "登出成功",
        });
      })
      .catch(() => {
        ElMessage({
          type: "info",
          message: "取消登出",
        });
      });
  } else {
    router.push("/user/" + command);
  }
};
</script>

<template>
  <el-container class="layout-container">
    <!---左側-->
    <el-aside width="200px">
      <div class="el-aside__logo"></div>
      <el-menu
        active-text-color="#ffd04b"
        background-color="#232323"
        text-color="#fff"
        router
      >
        <el-menu-item index="/article/category">
          <el-icon><Management /></el-icon>
          <span>文章分類</span>
        </el-menu-item>
        <el-menu-item index="/article/manage">
          <el-icon><Promotion /></el-icon>
          <span>文章管理</span>
        </el-menu-item>
        <el-menu-item index="/user/info">
          <el-icon><UserFilled /></el-icon>
          <span>基本資料</span>
        </el-menu-item>
        <!-- <el-sub-menu>
          <template #title>
            <el-icon><UserFilled /></el-icon>
            <span>個人中心</span>
          </template>
          <el-menu-item index="/user/info">
            <el-icon>
              <User />
            </el-icon>
            <span>基本資料</span>
          </el-menu-item>
          <el-menu-item index="/user/resetpassword">
            <el-icon>
              <EditPen />
            </el-icon>
            <span>重設密碼</span>
          </el-menu-item>
        </el-sub-menu>-->
      </el-menu>
    </el-aside>
    <!---右側-->
    <el-container>
      <!--上-->
      <el-header>
        <div>
          使用者:<strong>{{ infoStore.info.nickname }}</strong>
        </div>
        <el-dropdown placement="bottom-end" @command="handleCommand">
          <span class="el-dropdown__box">
            <el-avatar :icon="UserFilled" />
            <el-icon>
              <CaretBottom />
            </el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="info" :icon="User"
                >基本資料</el-dropdown-item
              >
              <!-- <el-dropdown-item command="resetpassword" :icon="EditPen"
                >重設密碼</el-dropdown-item
              >-->
              <el-dropdown-item command="logout" :icon="SwitchButton"
                >登出</el-dropdown-item
              >
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>
      <!--中-->
      <el-main>
        <router-view></router-view>
      </el-main>
      <!--下-->
      <el-footer>!!! BLOGGING !!!</el-footer>
    </el-container>
  </el-container>
</template>

<style scoped lang="scss">
.layout-container {
  height: 100vh;

  .el-aside {
    background-color: #232323;

    // &__logo {
    //   height: 120px;
    //   background: url("@/assets/logo.png") no-repeat center / 120px auto;
    // }

    .el-menu {
      border-right: none;
    }
  }

  .el-header {
    background-color: #fff;
    display: flex;
    align-items: center;
    justify-content: space-between;

    .el-dropdown__box {
      display: flex;
      align-items: center;

      .el-icon {
        color: #999;
        margin-left: 10px;
      }

      &:active,
      &:focus {
        outline: none;
      }
    }
  }

  .el-footer {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    color: #666;
  }
}
</style>
