package erp.schema;

import org.eclipse.microprofile.graphql.Description;
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
@Table(name = "ir_actions")
@Description("Actions")
public class IrActions {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Action Name")
	private String name;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Action Type")
	private String type;
	
	/**
	 * 
	 */
	@Column
	@Description("External ID")
	private String xml_id;
	
	/**
	 * 
	 */
	@Column
	@Description("Action Description")
	private String help;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = IrModel.class)
	@Description("Setting a value makes this action available in the sidebar for the given model.")
	private String binding_model_id;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	private String binding_type = "action";
	
	/**
	 * 
	 */
	@Column
	private String binding_view_types = "list,form";
}
