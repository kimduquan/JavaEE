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
@Table(name = "ir_act_url")
@Description("Action URL")
public class ActUrl extends Actions {

	/**
	 * 
	 */
	@Column
	@DefaultValue("ir.actions.act_url")
	private String type = "ir.actions.act_url";
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Action URL")
	private String url;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@Description("Action Target")
	@DefaultValue("new")
	private String target = "new";
}