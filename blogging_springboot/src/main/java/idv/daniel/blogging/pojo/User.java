package idv.daniel.blogging.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	private Integer id;

	@Column(name = "username")
	private String username;

	@JsonIgnore
	@Column(name = "password")
	private String password;

	@Column(name = "nickname")
	@NotEmpty
	@Pattern(regexp = "^\\S{5,30}$")
	private String nickname;

	@Column(name = "email")
	@NotEmpty
	@Email
	private String email;

	@CreationTimestamp
	@Column(name = "createTime")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp createTime;

	@Column(name = "updateTime")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp updateTime;

}
