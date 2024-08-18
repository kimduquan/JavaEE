package erp.base.schema.ir;

import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_model_constraint")
@Description("Model Constraint")
public class ModelConstraint {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Constraint")
	private String name;
	
	/**
	 * 
	 */
	@Column
	@Description("PostgreSQL constraint definition")
	private String definition;
	
	/**
	 * 
	 */
	@Column
	@Description("Error message returned when the constraint is violated.")
	private String message;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@ManyToOne(targetEntity = Model.class)
	@NotNull
	private String model;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@ManyToOne(targetEntity = Module.class)
	@NotNull
	private String module;
	
	/**
	 * 
	 */
	@Column(nullable = false, length = 1)
	@NotNull
	@Description("Constraint Type")
	private String type;
	
	/**
	 * 
	 */
	@Column
	private String write_date;
	
	/**
	 * 
	 */
	@Column
	private String create_date;
}
