package erp.schema.res;

import java.util.List;
import org.eclipse.microprofile.graphql.Description;
import erp.schema.ir.ModelAccess;
import erp.schema.ir.ModuleCategory;
import erp.schema.ir.Rule;
import erp.schema.ir.ui.Menu;
import erp.schema.ir.ui.View;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "res_groups")
@Description("Access Groups")
public class Groups {

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
	@ManyToMany(targetEntity = Users.class)
	@ElementCollection(targetClass = Users.class)
	@CollectionTable(name = "res_users")
	private List<String> users;
	
	/**
	 * 
	 */
	@Column
	@OneToMany(targetEntity = ModelAccess.class)
	@ElementCollection(targetClass = ModelAccess.class)
	@CollectionTable(name = "ir_model_access")
	@Description("Access Controls")
	private List<String> model_access;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = Rule.class)
	@ElementCollection(targetClass = Rule.class)
	@CollectionTable(name = "ir_rule")
	@Description("Rules")
	private List<String> rule_groups;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = Menu.class)
	@ElementCollection(targetClass = Menu.class)
	@CollectionTable(name = "ir_ui_menu")
	@Description("Access Menu")
	private List<String> menu_access;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = View.class)
	@ElementCollection(targetClass = View.class)
	@CollectionTable(name = "ir_ui_view")
	@Description("Views")
	private List<String> view_access;
	
	/**
	 * 
	 */
	@Column
	private String comment;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = ModuleCategory.class)
	@Description("Application")
	private String category_id;
	
	/**
	 * 
	 */
	@Column
	@Description("Color Index")
	private Integer color;
	
	/**
	 * 
	 */
	@Column
	@Description("Group Name")
	private String full_name;
	
	/**
	 * 
	 */
	@Column
	@Description("Share Group")
	private Boolean share;
}
