package erp.base.schema.ir;

import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_logging")
@Description("Logging")
public class Logging {

	@Column(updatable = false)
	@Description("Created by")
	private Integer create_uid;
	
	@Column(updatable = false)
	@Description("Created on")
	private String create_date;
	
	@Column(updatable = false)
	@Description("Last Updated by")
	private Integer write_uid;
	
	@Column(updatable = false)
	@Description("Last Updated on")
	private Integer write_date;
	
	@Column(nullable = false)
	@NotNull
	private String name;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	private String type;
	
	@Column
	@Description("Database Name")
	private String dbname;
	
	@Column
	private String level;
	
	@Column(nullable = false)
	@NotNull
	private String message;
	
	@Column(nullable = false)
	@NotNull
	private String path;
	
	@Column(nullable = false)
	@NotNull
	@Description("Function")
	private String func;
	
	@Column(nullable = false)
	@NotNull
	private String line;

	public Integer getCreate_uid() {
		return create_uid;
	}

	public void setCreate_uid(Integer create_uid) {
		this.create_uid = create_uid;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public Integer getWrite_uid() {
		return write_uid;
	}

	public void setWrite_uid(Integer write_uid) {
		this.write_uid = write_uid;
	}

	public Integer getWrite_date() {
		return write_date;
	}

	public void setWrite_date(Integer write_date) {
		this.write_date = write_date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFunc() {
		return func;
	}

	public void setFunc(String func) {
		this.func = func;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}
}
