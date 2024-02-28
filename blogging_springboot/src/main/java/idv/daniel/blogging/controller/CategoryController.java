package idv.daniel.blogging.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

import idv.daniel.blogging.pojo.Category;
import idv.daniel.blogging.pojo.Result;
import idv.daniel.blogging.service.CategoryService;
import idv.daniel.blogging.util.JwtUtil;

@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private CategoryService categorySvc;

	// 新增分類
	@PostMapping
	public Result add(@RequestBody @Validated(Category.Add.class) Category category,
			@RequestHeader(name = "Authorization") String token) {

		Map<String, Object> map = JwtUtil.parseToken(token);
		Integer userId = (Integer) map.get("id");

		category.setCreateUser(userId);

		categorySvc.add(category);

		return Result.success();

	}

	// 取得個人所有分類
	@GetMapping
	public Result<List<Category>> listAll(@RequestHeader(name = "Authorization") String token) {

		Map<String, Object> map = JwtUtil.parseToken(token);
		Integer userId = (Integer) map.get("id");

		List<Category> allCategory = categorySvc.listAll(userId);
		return Result.success(allCategory);

	}

	// 取得一筆分類資訊
	@GetMapping("/detail")
	public Result<Category> detail(Integer id) {

		Category c = categorySvc.findById(id);
		return Result.success(c);

	}

	// 修改分類
	@PutMapping
	public Result update(@RequestBody @Validated(Category.Update.class) Category category) {

		categorySvc.update(category);
		return Result.success();

	}
	// 刪除分類
	@DeleteMapping
	public Result delete(Integer id) {

		categorySvc.delete(id);
		return Result.success();

	}
}
