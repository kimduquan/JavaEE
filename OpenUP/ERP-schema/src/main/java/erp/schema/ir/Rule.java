package erp.schema.ir;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.schema.Groups;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_rule")
@Description("Record Rule")
public class Rule {

	/**
	 * 
	 */
	@Column
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
	@ManyToMany(targetEntity = Groups.class)
	@ElementCollection(targetClass = Groups.class)
	@CollectionTable(name = "res_groups")
	private List<String> groups;
	
	/**
	 * 
	 */
	@Column
	@Description("Domain")
	private String domain_force;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Description("Read")
	private Boolean perm_read = true;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Description("Write")
	private Boolean perm_write = true;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Description("Create")
	private Boolean perm_create = true;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Description("Delete")
	private Boolean perm_unlink = true;
}
