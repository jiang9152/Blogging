import request from "@/utils/request.js";

export const userRegisterSvc = (registerData) => {
  const params = new URLSearchParams();
  for (let key in registerData) {
    params.append(key, registerData[key]);
  }

  return request.post("/user/register", params);
};

export const userLoginSvc = (loginData) => {
  const params = new URLSearchParams();
  for (let key in loginData) {
    params.append(key, loginData[key]);
  }

  return request.post("/user/login", params);
};

export const userInfoSvc = () => {
  return request.get("/user/userInfo");
};

export const userInfoUpdSvc = (userInfoData) => {
  return request.put("/user/update", userInfoData);
};
