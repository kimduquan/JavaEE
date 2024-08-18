package erp.base.schema.ir;

import org.eclipse.microprofile.graphql.Description;

import erp.base.schema.res.Company;
import erp.base.schema.res.Users;
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
	@ManyToOne(targetEntity = ModelFields.class)
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
}
