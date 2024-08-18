package erp.base.schema.ir;

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
@Table(name = "ir_module_module_exclusion")
@Description("Module exclusion")
public class ModuleExclusion {

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
	@Description("Exclusion Module")
	private String exclusion_id;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@Description("Status")
	private String state;
}
