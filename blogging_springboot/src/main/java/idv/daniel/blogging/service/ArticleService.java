package idv.daniel.blogging.service;

import idv.daniel.blogging.pojo.Article;
import idv.daniel.blogging.pojo.PageBean;

public interface ArticleService {

	// 新增文章
	void add(Article article);

	// 查詢個人文章
	PageBean<Article> list(Integer userId, Integer pageNum, Integer pageSize, Integer categoryId, String state);

	// 更新文章
	void update(Article article);

	// 刪除文章
	void delete(Integer id);

}
