package idv.daniel.blogging.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import idv.daniel.blogging.pojo.Category.Add;
import idv.daniel.blogging.pojo.Category.Update;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Article")
public class Article implements Serializable {

	public interface Add {
	}

	public interface Update {
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull(groups = Update.class)
	private Integer id;

	@Column(name = "title")
	@NotEmpty(groups = { Update.class, Add.class })
	@Pattern(regexp = "^\\S{1,30}$")
	private String title;

	@Column(name = "content")
	@NotEmpty(groups = { Update.class, Add.class })
	private String content;

	@Column(name = "state")
	@NotEmpty(groups = { Update.class, Add.class })
	private String state;

	@Column(name = "categoryId")
	@NotNull(groups = { Update.class, Add.class })
	private Integer categoryId;

	@Column(name = "createUser")
	private Integer createUser;

	@CreationTimestamp
	@Column(name = "createTime")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp createTime;

	@Column(name = "updateTime")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp updateTime;

}
