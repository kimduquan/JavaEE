package erp.base.schema.ir.actions;

import org.eclipse.microprofile.graphql.DefaultValue;
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
@Table(name = "ir_actions_todo")
@Description("Configuration Wizards")
public class Todo {

	/**
	 * 
	 */
	@Column(nullable = false)
	@ManyToOne(targetEntity = Actions.class)
	@NotNull
	@Description("Action")
	private String action_id;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("10")
	private Integer sequence = 10;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@Description("Status")
	@DefaultValue("open")
	private String state = "open";
	
	/**
	 * 
	 */
	@Column
	private String name;
}
