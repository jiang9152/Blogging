package idv.daniel.blogging.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import idv.daniel.blogging.pojo.Article;
import idv.daniel.blogging.pojo.Category;
import idv.daniel.blogging.pojo.PageBean;
import idv.daniel.blogging.pojo.Result;
import idv.daniel.blogging.service.ArticleService;
import idv.daniel.blogging.util.JwtUtil;

@RestController
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService articleSvc;

	// 新增文章

	@PostMapping
	public Result add(@RequestHeader(name = "Authorization") String token,
			@RequestBody @Validated(Article.Add.class) Article article) {

		Map<String, Object> map = JwtUtil.parseToken(token);
		Integer userId = (Integer) map.get("id");

		article.setCreateUser(userId);
		articleSvc.add(article);

		return Result.success();

	}

	// 搜尋個人所有文章

	@GetMapping("/listMyArticles")
	public Result<PageBean<Article>> listMyArticle(@RequestHeader(name = "Authorization") String token, Integer pageNum,
			Integer pageSize, @RequestParam(required = false) Integer categoryId,
			@RequestParam(required = false) String state) {

		Map<String, Object> map = JwtUtil.parseToken(token);
		Integer userId = (Integer) map.get("id");

		PageBean<Article> page = articleSvc.list(userId, pageNum, pageSize, categoryId, state);

		return Result.success(page);

	}

	// 更新文章
	@PutMapping
	public Result update(@RequestBody @Validated(Article.Update.class) Article article) {

		articleSvc.update(article);
		return Result.success();

	}

	// 刪除文章
	@DeleteMapping
	public Result delete(Integer id) {

		articleSvc.delete(id);
		return Result.success();

	}

}
