package erp.base.schema.ir.actions;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_act_client")
@Description("Client Action")
public class ActClient extends Actions {

	/**
	 * 
	 */
	@Column
	@DefaultValue("ir.actions.client")
	private String type = "ir.actions.client";
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Client action tag")
	private String tag;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@DefaultValue("current")
	@Description("Target Window")
	private String target = "current";
	
	/**
	 * 
	 */
	@Column
	@Description("Destination Model")
	private String res_model;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("{}")
	@Description("Context Value")
	private String context = "{}";
	
	/**
	 * 
	 */
	@Column
	@Description("Supplementary arguments")
	private byte[] params;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Params storage")
	private byte[] params_store;
}
