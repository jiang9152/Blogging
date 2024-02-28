package idv.daniel.blogging.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import idv.daniel.blogging.pojo.Category;

@Component
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Transactional
	@Query(value = "FROM Category where createUser=:userId ")
	List<Category> findByUserId(@Param("userId") Integer userId);

	@Transactional
	@Modifying
	@Query(value = "UPDATE Category c SET c.categoryAlias=:categoryAlias, c.categoryName=:categoryName, c.updateTime=:time  where c.id=:id ")
	void updateCategory(@Param("id") Integer id, @Param("categoryName") String categoryName,
			@Param("categoryAlias") String categoryAlias, @Param("time") Timestamp timestamp);

}
