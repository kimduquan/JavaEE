package erp.base.schema.ir.module;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_module_category")
@Description("Application")
public class Category {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Name")
	private String name;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Category.class)
	@Description("Parent Application")
	private String parent_id;
	
	/**
	 * 
	 */
	@Column
	@OneToMany(targetEntity = Category.class)
	@ElementCollection(targetClass = Category.class)
	@CollectionTable(name = "ir_module_category")
	@Description("Child Applications")
	private List<String> child_ids;
	
	/**
	 * 
	 */
	@Column
	@OneToMany(targetEntity = Module.class)
	@ElementCollection(targetClass = Module.class)
	@CollectionTable(name = "ir_module_module")
	@Description("Modules")
	private List<String> module_ids;
	
	/**
	 * 
	 */
	@Column
	@Description("Description")
	private String description;
	
	/**
	 * 
	 */
	@Column
	@Description("Sequence")
	private Integer sequence;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Description("Visible")
	private Boolean visible = true;
	
	/**
	 * 
	 */
	@Column
	@Description("Exclusive")
	private Boolean exclusive;
	
	/**
	 * 
	 */
	@Column
	@Description("External ID")
	private String xml_id;
}
