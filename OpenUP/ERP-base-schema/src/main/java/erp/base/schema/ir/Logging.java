package erp.base.schema.ir;

import java.util.Date;

import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
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
@NodeEntity("Logging")
public class Logging {
	
	/**
	 * 
	 */
	public enum Type {
		/**
		 * 
		 */
		client,
		/**
		 * 
		 */
		server
	}
	
	/**
	 * 
	 */
	@jakarta.persistence.Id
	@Id
	private int id;

	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Created by")
	@Property
	private Integer create_uid;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Created on")
	@Property
	private Date create_date;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Last Updated by")
	@Property
	private Integer write_uid;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Last Updated on")
	@Property
	private Date write_date;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Property
	private String name;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@Property
	private Type type;
	
	/**
	 * 
	 */
	@Column
	@Description("Database Name")
	@Property
	private String dbname;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String level;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Property
	private String message;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Property
	private String path;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Function")
	@Property
	private String func;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Property
	private String line;

	public Integer getCreate_uid() {
		return create_uid;
	}

	public void setCreate_uid(Integer create_uid) {
		this.create_uid = create_uid;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Integer getWrite_uid() {
		return write_uid;
	}

	public void setWrite_uid(Integer write_uid) {
		this.write_uid = write_uid;
	}

	public Date getWrite_date() {
		return write_date;
	}

	public void setWrite_date(Date write_date) {
		this.write_date = write_date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
