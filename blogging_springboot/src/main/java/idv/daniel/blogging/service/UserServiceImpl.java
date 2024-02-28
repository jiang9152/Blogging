package idv.daniel.blogging.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import idv.daniel.blogging.pojo.User;
import idv.daniel.blogging.repository.UserRepository;
import idv.daniel.blogging.util.Md5util;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Override
	public User findByUserName(String username) {

		return repository.findByUserName(username);
	}

	@Override
	public void register(String username, String password) {
		// 密碼加密
		String md5String = Md5util.getMd5String(password);
		// 加入資料庫
		repository.addUser(username, md5String);
	}

	@Override
	public void updateUser(User user) {

		Integer id = user.getId();
		String email = user.getEmail();
		String nickname = user.getNickname();

		repository.updateUser(id, email, nickname, new Timestamp(System.currentTimeMillis()));

	}

}
