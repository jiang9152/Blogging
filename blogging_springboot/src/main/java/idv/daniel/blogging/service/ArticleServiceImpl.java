package idv.daniel.blogging.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import idv.daniel.blogging.pojo.Article;
import idv.daniel.blogging.pojo.PageBean;
import idv.daniel.blogging.repository.ArticleRepository;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleRepository repository;

	@Override
	public void add(Article article) {
		article.setCreateTime(new Timestamp(System.currentTimeMillis()));
		article.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		repository.save(article);

	}

	@Override
	public PageBean<Article> list(Integer userId, Integer pageNum, Integer pageSize, Integer categoryId, String state) {

		// 返回物件
		PageBean<Article> page = new PageBean();

		// 查詢物件
		Article article = new Article();

		// 設置條件
		article.setCreateUser(userId);

		if (categoryId != null) {
			article.setCategoryId(categoryId);
		}

		if (state != null) {
			article.setState(state);
		}

		Example<Article> example = Example.of(article);

		List<Article> articles = repository.findAll(example);

		// 分頁
		Integer articlesNum = articles.size();
		List<Article> pageArticles = new ArrayList<>();

		if (articlesNum < (pageNum * pageSize)) {

			for (int i = pageSize * (pageNum - 1); i < articlesNum; i++) {
				pageArticles.add(articles.get(i));
			}

		} else {

			for (int i = pageSize * (pageNum - 1); i < pageNum * pageSize; i++) {
				pageArticles.add(articles.get(i));
			}

		}

		page.setTotal((long) articlesNum);
		page.setItems(pageArticles);

		return page;
	}

	@Override
	public void update(Article article) {

		repository.updateArticle(article.getId(), article.getCategoryId(), article.getTitle(), article.getContent(),
				article.getState(), new Timestamp(System.currentTimeMillis()));

	}

	@Override
	public void delete(Integer id) {
		repository.deleteById(id);
	}

}
