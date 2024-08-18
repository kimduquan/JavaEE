package erp.base.schema.ir.model;

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
public class Constraint {

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWrite_date() {
		return write_date;
	}

	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
}
