package erp.base.schema.ir.module;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_module_module")
@Description("Module")
public class Module {

	/**
	 * 
	 */
	@Column(nullable = false, updatable = false)
	@NotNull
	@Description("Technical Name")
	private String name;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@ManyToOne(targetEntity = Category.class)
	@Description("Category")
	private String category_id;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Module Name")
	private String shortdesc;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Summary")
	private String summary;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Description")
	private String description;
	
	/**
	 * 
	 */
	@Column
	@Description("Description HTML")
	private String description_html;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Author")
	private String author;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Maintainer")
	private String maintainer;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Contributors")
	private String contributors;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Website")
	private String website;
	
	/**
	 * 
	 */
	@Column
	@Description("Latest Version")
	private String installed_version;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Installed Version")
	private String latest_version;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Published Version")
	private String published_version;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("URL")
	private String url;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("100")
	@Description("Sequence")
	private Integer sequence = 100;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@OneToMany(targetEntity = Dependency.class)
	@ElementCollection(targetClass = Dependency.class)
	@CollectionTable(name = "ir_module_module_dependency")
	@Description("Dependencies")
	private List<String> dependencies_id;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@OneToMany(targetEntity = Exclusion.class)
	@ElementCollection(targetClass = Exclusion.class)
	@CollectionTable(name = "ir_module_module_exclusion")
	@Description("Exclusions")
	private List<String> exclusion_ids;
	
	/**
	 * 
	 */
	@Column
	@Description("Automatic Installation")
	private Boolean auto_install;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Enumerated(EnumType.STRING)
	@DefaultValue("uninstallable")
	@Description("Status")
	private String state = "uninstallable";
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@DefaultValue("false")
	@Description("Demo Data")
	private Boolean demo = false;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Enumerated(EnumType.STRING)
	@DefaultValue("LGPL-3")
	private String license = "LGPL-3";
	
	/**
	 * 
	 */
	@Column
	@Description("Menus")
	private String menus_by_module;
	
	/**
	 * 
	 */
	@Column
	@Description("Reports")
	private String reports_by_module;
	
	/**
	 * 
	 */
	@Column
	@Description("Views")
	private String views_by_module;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Application")
	private Boolean application;
	
	/**
	 * 
	 */
	@Column
	@Description("Icon URL")
	private String icon;
	
	/**
	 * 
	 */
	@Column
	@Description("Icon")
	private byte[] icon_image;
	
	/**
	 * 
	 */
	@Column
	@Description("Flag")
	private String icon_flag;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("false")
	@Description("Odoo Enterprise Module")
	private Boolean to_buy = false;
	
	/**
	 * 
	 */
	@Column
	private Boolean has_iap;
}
