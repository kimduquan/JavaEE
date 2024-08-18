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
@Table(name = "ir_model_inherit")
@Description("Model Inheritance Tree")
public class ModelInherit {

	/**
	 * 
	 */
	@Column(nullable = false)
	@ManyToOne(targetEntity = Model.class)
	@NotNull
	private String model_id;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@ManyToOne(targetEntity = Model.class)
	@NotNull
	private String parent_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = ModelFields.class)
	private String parent_field_id;
}