import request from "@/utils/request.js";
// import { useTokenStore } from "@/stores/token.js";

export const articleCategorySvc = () => {
  return request.get("/category");
};

export const articleCategoryAddSvc = (categoryData) => {
  return request.post("/category", categoryData);
};

export const articleCategoryUpdSvc = (categoryData) => {
  return request.put("/category", categoryData);
};

export const articleCategoryDelSvc = (id) => {
  return request.delete("/category?id=" + id);
};

export const articleListSvc = (params) => {
  return request.get("/article/listMyArticles", { params: params });
};

export const articleAddSvc = (articleData) => {
  return request.post("/article", articleData);
};

export const articleUpdSvc = (articleData) => {
  return request.put("/article", articleData);
};

export const articleDelSvc = (id) => {
  return request.delete("/article?id=" + id);
};
