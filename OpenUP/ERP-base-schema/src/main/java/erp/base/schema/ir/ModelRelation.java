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
@Table(name = "ir_model_relation")
@Description("Relation Model")
public class ModelRelation {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Relation Name")
	private String name;
	
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
	@Column
	private String write_date;
	
	/**
	 * 
	 */
	@Column
	private String create_date;
}
