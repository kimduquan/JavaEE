package erp.base.schema.ir;

import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.model.Fields;
import erp.base.schema.res.Company;
import erp.base.schema.res.users.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_default")
@Description("Default Values")
public class Default {

	@Column(nullable = false)
	@ManyToOne(targetEntity = Fields.class)
	@NotNull
	@Description("Field")
	private String field_id;
	
	@Column
	@ManyToOne(targetEntity = Users.class)
	@Description("User")
	private String user_id;
	
	@Column
	@ManyToOne(targetEntity = Company.class)
	@Description("Company")
	private String company_id;
	
	@Column
	@Description("Condition")
	private String condition;
	
	@Column(nullable = false)
	@NotNull
	@Description("Default Value (JSON format)")
	private String json_value;

	public String getField_id() {
		return field_id;
	}

	public void setField_id(String field_id) {
		this.field_id = field_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
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
}
