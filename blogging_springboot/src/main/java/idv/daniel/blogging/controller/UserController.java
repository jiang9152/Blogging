package idv.daniel.blogging.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import idv.daniel.blogging.pojo.Result;
import idv.daniel.blogging.pojo.User;
import idv.daniel.blogging.service.UserService;
import idv.daniel.blogging.util.JwtUtil;
import idv.daniel.blogging.util.Md5util;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

	@Autowired
	private UserService userSvc;

	@Autowired
	private StringRedisTemplate template;

	// 註冊功能

	@PostMapping("/register")
	public Result register(@Pattern(regexp = "^\\S{5,30}$") String username,
			@Pattern(regexp = "^\\S{5,30}$") String password) {

		// 確認是否註冊過
		User user = userSvc.findByUserName(username);

		if (user == null) {
			userSvc.register(username, password);

			return Result.success();

		} else {

			return Result.error("該名稱已註冊");
		}

	}

	// 登入功能

	@PostMapping("/login")
	public Result login(@Pattern(regexp = "^\\S{5,20}$") String username,
			@Pattern(regexp = "^\\S{5,20}$") String password) {

		// 查詢是否註冊過
		User loginUser = userSvc.findByUserName(username);

		if (loginUser == null) {
			// 未註冊
			return Result.error("該名稱未註冊");

		} else {

			if (Md5util.getMd5String(password).equals(loginUser.getPassword())) {
				// 登入成功
				Map<String, Object> claims = new HashMap<>();
				claims.put("id", loginUser.getId());
				claims.put("username", loginUser.getUsername());
				String token = JwtUtil.getToken(claims);

				// 存入redis
				ValueOperations<String, String> operations = template.opsForValue();
				operations.set(token, token, 1, TimeUnit.HOURS);

				return Result.success(token);
			}

			return Result.error("密碼錯誤，登入失敗");
		}

	}

	// 取得用戶資訊

	@GetMapping("/userInfo")
	public Result<User> userInfo(@RequestHeader(name = "Authorization") String token) {

		Map<String, Object> map = JwtUtil.parseToken(token);
		String username = (String) map.get("username");
		User user = userSvc.findByUserName(username);
		return Result.success(user);
	}

	// 更新用戶資訊

	@PutMapping("/update")
	public Result<User> update(@RequestBody @Validated User user) {

		userSvc.updateUser(user);
		return Result.success();
	}

	// 登出
	@GetMapping("/logout")
	public Result logout(@RequestHeader(name = "Authorization") String token) {

		// 刪除redis中舊的token
		ValueOperations<String, String> operations = template.opsForValue();
		operations.getOperations().delete(token);

		return Result.success();
	}

}
