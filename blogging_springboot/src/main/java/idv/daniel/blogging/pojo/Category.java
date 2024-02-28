package idv.daniel.blogging.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Category")
public class Category implements Serializable {

	public interface Add {
	}

	public interface Update {
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull(groups = Update.class)
	private Integer id;

	@Column(name = "categoryName")
	@NotEmpty(groups = { Update.class, Add.class })
	private String categoryName;

	@Column(name = "categoryAlias")
	@NotEmpty(groups = { Update.class, Add.class })
	private String categoryAlias;

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
