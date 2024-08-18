package erp.base.schema.ir.ui;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

import erp.base.schema.res.Groups;
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
@Table(name = "ir_ui_menu")
@Description("Menu")
public class Menu {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Menu")
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
	@Column
	@DefaultValue("10")
	private Integer sequence = 10;
	
	/**
	 * 
	 */
	@Column
	@OneToMany(targetEntity = Menu.class)
	@ElementCollection(targetClass = Menu.class)
	@CollectionTable(name = "ir_ui_menu")
	@Description("Child IDs")
	private String child_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Menu.class)
	private String parent_id;
	
	/**
	 * 
	 */
	@Column
	private String parent_path;
	
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
	@Column
	@Description("Full Path")
	private String complete_name;
	
	/**
	 * 
	 */
	@Column
	@Description("Web Icon File")
	private String web_icon;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	private String action;
	
	/**
	 * 
	 */
	@Column
	@Description("Web Icon Image")
	private byte[] web_icon_data;
}
