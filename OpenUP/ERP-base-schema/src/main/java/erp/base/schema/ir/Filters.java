package erp.base.schema.ir;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

import erp.base.schema.ir.actions.Actions;
import erp.base.schema.res.users.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_filters")
@Description("Filters")
public class Filters {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Filter Name")
	private String name;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Users.class)
	@Description("User")
	private String user_id;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("[]")
	private String domain = "[]";
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("{}")
	private String context = "{}";
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("[]")
	private String sort = "[]";
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@Description("Model")
	private String model_id;
	
	/**
	 * 
	 */
	@Column
	@Description("Default Filter")
	private Boolean is_default;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Actions.class)
	@Description("Action")
	private String action_id;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	private Boolean active = true;
}
