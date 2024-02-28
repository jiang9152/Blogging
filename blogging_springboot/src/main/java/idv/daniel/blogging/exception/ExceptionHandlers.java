package idv.daniel.blogging.exception;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import idv.daniel.blogging.pojo.Result;

@RestControllerAdvice
public class ExceptionHandlers {

	@ExceptionHandler(Exception.class)
	public Result handleException(Exception e) {
		e.printStackTrace();
		return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "伺服器錯誤");
	}

}
