package idv.daniel.blogging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import idv.daniel.blogging.interceptor.LoginInterceptors;

@Configuration
public class Config implements WebMvcConfigurer {

	@Autowired
	private LoginInterceptors loginInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 登入和註冊不攔截
		registry.addInterceptor(loginInterceptor).excludePathPatterns("/user/login", "/user/register");
	}

}