package erp.base.schema.ir.model;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

import erp.base.schema.res.groups.Groups;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_model_access")
@Description("Model Access")
public class Access {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	private String name;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	private Boolean active = true;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@ManyToOne(targetEntity = Model.class)
	@NotNull
	@Description("Model")
	private String model_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Groups.class)
	@Description("Group")
	private String group_id;
	
	/**
	 * 
	 */
	@Column
	@Description("Read Access")
	private Boolean perm_read;
	
	/**
	 * 
	 */
	@Column
	@Description("Write Access")
	private Boolean perm_write;
	
	/**
	 * 
	 */
	@Column
	@Description("Create Access")
	private Boolean perm_create;
	
	/**
	 * 
	 */
	@Column
	@Description("Delete Access")
	private Boolean perm_unlink;
}
