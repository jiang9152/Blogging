package idv.daniel.blogging.service;

import idv.daniel.blogging.pojo.User;

public interface UserService {

	// 根據用戶名稱查詢
	User findByUserName(String username);

	// 註冊新用戶
	void register(String username, String password);

	// 更新用戶資料
	void updateUser(User user);



}
