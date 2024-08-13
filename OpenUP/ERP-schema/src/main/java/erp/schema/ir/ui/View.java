package erp.schema.ir.ui;

import java.util.List;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

import erp.schema.Groups;
import erp.schema.ir.Model;
import erp.schema.ir.ModelData;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_ui_view")
@Description("View")
public class View {
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("View Name")
	private String name;
	
	/**
	 * 
	 */
	@Column
	private String model;
	
	/**
	 * 
	 */
	@Column
	private String key;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("16")
	@Description("Sequence")
	private Integer priority = 16;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@Description("View Type")
	private String type;
	
	/**
	 * 
	 */
	@Column
	@Description("View Architecture")
	private String arch;
	
	/**
	 * 
	 */
	@Column
	@Description("Base View Architecture")
	private String arch_base;
	
	/**
	 * 
	 */
	@Column
	@Description("Arch Blob")
	private String arch_db;
	
	/**
	 * 
	 */
	@Column
	@Description("Arch Filename")
	private String arch_fs;
	
	/**
	 * 
	 */
	@Column
	@Description("Modified Architecture")
	private Boolean arch_updated;
	
	/**
	 * 
	 */
	@Column
	@Description("Previous View Architecture")
	private String arch_prev;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = View.class)
	@Description("Inherited View")
	private String inherit_id;
	
	/**
	 * 
	 */
	@Column
	@OneToMany(targetEntity = View.class)
	@ElementCollection(targetClass = View.class)
	@CollectionTable(name = "ir_ui_view")
	@Description("Views which inherit from this one")
	private List<String> inherit_children_ids;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = ModelData.class)
	@Description("Model Data")
	private String model_data_id;
	
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
	@ManyToMany(targetEntity = Groups.class)
	@ElementCollection(targetClass = Groups.class)
	@CollectionTable(name = "res_groups")
	@Description("Groups")
	private List<String> groups_id;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("primary")
	@Description("View inheritance mode")
	private String mode = "primary";
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	private Boolean active = true;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Model.class)
	@Description("Model of the view")
	private String model_id;
}
