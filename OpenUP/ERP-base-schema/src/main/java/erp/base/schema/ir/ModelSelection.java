package erp.base.schema.ir;

import org.eclipse.microprofile.graphql.DefaultValue;
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
@Table(name = "ir_model_fields_selection")
@Description("Fields Selection")
public class ModelSelection {

	@Column(nullable = false)
	@ManyToOne(targetEntity = ModelFields.class)
	@NotNull
	private String field_id;
	
	@Column(nullable = false)
	@NotNull
	private String value;
	
	@Column(nullable = false)
	@NotNull
	private String name;
	
	@Column
	@DefaultValue("1000")
	private Integer sequence = 1000;
}
