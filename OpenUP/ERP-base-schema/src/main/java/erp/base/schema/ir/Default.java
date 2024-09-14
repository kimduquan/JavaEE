package erp.base.schema.ir;

import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import erp.base.schema.ir.model.Fields;
import erp.base.schema.res.Company;
import erp.base.schema.res.users.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_default")
@Description("Default Values")
@NodeEntity("Default Values")
public class Default {
	
	/**
	 * 
	 */
	@jakarta.persistence.Id
	@Id
	private int id;

	/**
	 * 
	 */
	@Column(nullable = false, insertable = false, updatable = false)
	@NotNull
	@Description("Field")
	@Transient
	private Integer field_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = Fields.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "field_id", nullable = false)
	@NotNull
	@Relationship(type = "FIELD")
	private Fields field;
	
	/**
	 * 
	 */
	@Column(insertable = false, updatable = false)
	@Description("User")
	@Transient
	private Integer user_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = Users.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@Relationship(type = "USER")
	private Users user;
	
	/**
	 * 
	 */
	@Column(insertable = false, updatable = false)
	@Description("Company")
	@Transient
	private Integer company_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	@Relationship(type = "COMPANY")
	private Company company;
	
	/**
	 * 
	 */
	@Column
	@Description("Condition")
	@Property
	private String condition;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Default Value (JSON format)")
	@Property
	private String json_value;

	public Integer getField_id() {
		return field_id;
	}

	public void setField_id(Integer field_id) {
		this.field_id = field_id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getCompany_id() {
		return company_id;
	}

	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getJson_value() {
		return json_value;
	}

	public void setJson_value(String json_value) {
		this.json_value = json_value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Fields getField() {
		return field;
	}

	public void setField(Fields field) {
		this.field = field;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
