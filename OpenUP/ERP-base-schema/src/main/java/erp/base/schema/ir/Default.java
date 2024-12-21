package erp.base.schema.ir;

import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.model.Fields;
import erp.base.schema.res.Company;
import erp.base.schema.res.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ir_default")
@Description("Default Values")
public class Default {
	
	@Id
	private int id;

	@Transient
	private Integer field_id;

	@ManyToOne(targetEntity = Fields.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "field_id", nullable = false)
	@NotNull
	@Description("Field")
	private Fields field;
	
	@Transient
	private Integer user_id;

	@ManyToOne(targetEntity = Users.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@Description("User")
	private Users user;
	
	@Transient
	private Integer company_id;

	@ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	@Description("Company")
	private Company company;
	
	@Column
	@Description("Condition")
	private String condition;
	
	@Column(nullable = false)
	@NotNull
	@Description("Default Value (JSON format)")
	private String json_value;
}
