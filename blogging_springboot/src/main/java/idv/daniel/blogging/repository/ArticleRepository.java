package idv.daniel.blogging.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import idv.daniel.blogging.pojo.Article;
import idv.daniel.blogging.pojo.Category;

@Component
public interface ArticleRepository extends JpaRepository<Article, Integer> {

	@Transactional
	@Query(value = "FROM Article where state='發佈' ")
	List<Article> listAll();

	@Transactional
	@Modifying
	@Query(value = "UPDATE Article c SET c.title=:title, c.content=:content,c.categoryId=:categoryId,c.state=:state, c.updateTime=:time  where c.id=:id ")
	void updateArticle(@Param("id") Integer id, @Param("categoryId") Integer categoryId, @Param("title") String title,
			@Param("content") String content, @Param("state") String state, @Param("time") Timestamp timestamp);

}
