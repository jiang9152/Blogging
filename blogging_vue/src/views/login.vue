<script setup>
import { User, Lock } from "@element-plus/icons-vue";
import { ref } from "vue";
import { ElMessage } from "element-plus";

//false為註冊，true為登入
const isRegister = ref(false);
const registerData = ref({
  username: "",
  password: "",
  rePassword: "",
});

//校驗
const validatePass2 = (rule, value, callback) => {
  if (value === "") {
    callback(new Error("請再次確認密碼"));
  } else if (value !== registerData.value.password) {
    callback(new Error("請確認兩次密碼相同"));
  } else {
    callback();
  }
};

const rules = {
  username: [
    { required: true, message: "請輸入用戶姓名", trigger: "blur" },
    { min: 5, max: 30, message: "請輸入5-30個字", trigger: "blur" },
  ],
  password: [
    { required: true, message: "請輸入用戶密碼", trigger: "blur" },
    { min: 5, max: 30, message: "請輸入5-30個字", trigger: "blur" },
  ],
  rePassword: [{ validator: validatePass2, trigger: "blur" }],
};

//與後台連接
import { userRegisterSvc, userLoginSvc } from "@/api/user.js";

const register = async () => {
  let result = await userRegisterSvc(registerData.value);

  ElMessage.success(result.msg ? result.msg : "註冊成功");
};

import { useRouter } from "vue-router";
import { useTokenStore } from "@/stores/token.js";
const router = useRouter();
const tokenStore = useTokenStore();

const login = async () => {
  let result = await userLoginSvc(registerData.value);

  ElMessage.success(result.msg ? result.msg : "登入成功");
  tokenStore.setToken(result.data);
  router.push("/article/manage");
};

const clearValue = () => {
  registerData.value = {
    username: "",
    password: "",
    rePassword: "",
  };
};
</script>

<template>
  <el-row class="login-page">
    <el-col :span="12" class="bg"></el-col>
    <el-col :span="6" :offset="3" class="form">
      <!--註冊-->
      <el-form
        ref="form"
        size="large"
        autocpmplete="off"
        v-if="isRegister"
        :model="registerData"
        :rules="rules"
      >
        <el-form-item>
          <h1>用戶註冊</h1>
        </el-form-item>

        <el-form-item prop="username">
          <el-input
            :prefix-icon="User"
            placeholder="用戶姓名"
            v-model="registerData.username"
          />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            :prefix-icon="Lock"
            type="password"
            placeholder="用戶密碼"
            v-model="registerData.password"
          />
        </el-form-item>
        <el-form-item prop="rePassword">
          <el-input
            :prefix-icon="Lock"
            type="password"
            placeholder="再次輸入密碼"
            v-model="registerData.rePassword"
          />
        </el-form-item>
        <el-form-item
          ><el-button
            class="button"
            type="primary"
            auto-insert-space
            @click="register"
            >註冊</el-button
          ></el-form-item
        >

        <el-form-item class="flex">
          <el-link
            type="info"
            :underline="false"
            @click="
              isRegister = false;
              clearValue();
            "
            >返回登入</el-link
          >
        </el-form-item>
      </el-form>
      <!--登入-->
      <el-form
        ref="form"
        size="large"
        autocpmplete="off"
        v-else
        :model="registerData"
        :rules="rules"
      >
        <el-form-item>
          <h1>用戶登入</h1>
        </el-form-item>

        <el-form-item prop="username">
          <el-input
            :prefix-icon="User"
            placeholder="用戶姓名"
            v-model="registerData.username"
          />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            name="password"
            :prefix-icon="Lock"
            type="password"
            placeholder="用戶密碼"
            v-model="registerData.password"
          />
        </el-form-item>
        <!--<el-form-item class="flex">
          <div class="flex">
            <el-link type="primary" :underline="false">忘記密碼</el-link>
          </div>
        </el-form-item>-->

        <el-form-item
          ><el-button
            class="button"
            type="primary"
            auto-insert-space
            @click="login"
            >登入</el-button
          ></el-form-item
        >
        <el-form-item class="flex">
          <el-link
            type="info"
            :underline="false"
            @click="
              isRegister = true;
              clearValue();
            "
            >前往註冊</el-link
          >
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
.login-page {
  height: 100vh;
  background-color: #fff;

  .bg {
    background: url("@/assets/login_bg.jpg") no-repeat center / cover;
    border-radius: 0 20px 20px 0;
  }

  .form {
    display: flex;
    flex-direction: column;
    justify-content: center;
    user-select: none;

    .button {
      width: 100%;
    }
    .flex {
      width: 100%;
      display: flex;
      justify-content: space-between;
    }
  }
}
</style>
