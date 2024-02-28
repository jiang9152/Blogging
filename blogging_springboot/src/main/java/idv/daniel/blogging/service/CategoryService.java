package idv.daniel.blogging.service;

import java.util.List;

import idv.daniel.blogging.pojo.Category;

public interface CategoryService {

	// 新增分類
	void add(Category category);

	// 列表查詢
	List<Category> listAll(Integer userId);

	// 查詢用戶的分類
	Category findById(Integer id);

	// 更新分類
	void update(Category category);

	//刪除分類
	void delete(Integer id);

}
