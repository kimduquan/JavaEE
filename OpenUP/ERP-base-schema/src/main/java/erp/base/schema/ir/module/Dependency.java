package erp.base.schema.ir.module;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "ir_module_module_dependency")
@Description("Module dependency")
public class Dependency {

	/**
	 * 
	 */
	@Column
	private String name;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Module.class)
	@Description("Module")
	private String module_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Module.class)
	@Description("Dependency")
	private String depend_id;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@Description("Status")
	private String state;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	private Boolean auto_install_required = true;
}
