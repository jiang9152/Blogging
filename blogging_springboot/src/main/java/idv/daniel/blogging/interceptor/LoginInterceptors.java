package idv.daniel.blogging.interceptor;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import idv.daniel.blogging.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptors implements HandlerInterceptor {

	@Autowired
	private StringRedisTemplate template;

	// 登入驗證
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getHeader("Authorization");
		try {

			ValueOperations<String, String> operations = template.opsForValue();
			String redisToken = operations.get(token);

			// token已失效
			if (redisToken == null) {
				throw new RuntimeException();
			}

			Map<String, Object> claims = JwtUtil.parseToken(token);
			return true;
		} catch (Exception e) {
			response.setStatus(401);
			return false;
		}
	}

}
