package idv.daniel.blogging.repository;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import idv.daniel.blogging.pojo.User;

@Component
public interface UserRepository extends JpaRepository<User, Integer> {

	@Transactional
	@Query(value = "FROM User where username=:username ")
	User findByUserName(@Param("username") String username);

	@Transactional
	@Modifying
	@Query(value = "INSERT INTO Users(username, password) VALUES (:username, :password) ", nativeQuery = true)
	void addUser(@Param("username") String username, @Param("password") String md5String);

	@Transactional
	@Modifying
	@Query(value = "UPDATE User u SET u.email=:email, u.nickname=:nickname, u.updateTime=:time  where u.id=:id ")
	void updateUser(@Param("id") Integer id, @Param("email") String email, @Param("nickname") String nickname,
			@Param("time") Timestamp time);

}
