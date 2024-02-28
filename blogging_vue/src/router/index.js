import { createRouter, createWebHistory } from "vue-router";

import LoginVue from "@/views/login.vue";
import MainVue from "@/views/mainpage.vue";
import ArticleCategoryVue from "@/views/article/ArticleCategory.vue";
import ArticleManageyVue from "@/views/article/ArticleManage.vue";
import UserInfoVue from "@/views/user/UserInfo.vue";

const routes = [
  { path: "/login", component: LoginVue },
  {
    path: "/",
    component: MainVue,
    children: [
      { path: "/article/category", component: ArticleCategoryVue },
      { path: "/article/manage", component: ArticleManageyVue },
      { path: "/user/info", component: UserInfoVue },
    ],
  },
];

const router = createRouter({
  routes: routes,
  history: createWebHistory(),
});

export default router;
