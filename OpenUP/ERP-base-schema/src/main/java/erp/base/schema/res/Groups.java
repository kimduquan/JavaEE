package erp.base.schema.res;

import java.util.List;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.Rule;
import erp.base.schema.ir.model.Access;
import erp.base.schema.ir.module.Category;
import erp.base.schema.ir.ui.Menu;
import erp.base.schema.ir.ui.View;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "res_groups")
@Description("Access Groups")
public class Groups {
	
	@Id
	private int id;

	@Column(nullable = false)
	@NotNull
	private String name;
	
	@ManyToMany(targetEntity = Users.class, fetch = FetchType.LAZY)
	@JoinTable(name = "res_groups_users_rel", joinColumns = {@JoinColumn(name = "gid")}, inverseJoinColumns = {@JoinColumn(name = "uid")})
	private List<Users> users;
	
	@OneToMany(targetEntity = Access.class, fetch = FetchType.LAZY, mappedBy = "group_id")
	@Description("Access Controls")
	private List<Access> model_access;
	
	@ManyToMany(targetEntity = Rule.class, fetch = FetchType.LAZY)
	@JoinTable(name = "rule_group_rel", joinColumns = {@JoinColumn(name = "group_id")}, inverseJoinColumns = {@JoinColumn(name = "rule_group_id")})
	@Description("Rules")
	private List<Rule> rule_groups;
	
	@ManyToMany(targetEntity = Menu.class, fetch = FetchType.LAZY)
	@JoinTable(name = "ir_ui_menu_group_rel", joinColumns = {@JoinColumn(name = "gid")}, inverseJoinColumns = {@JoinColumn(name = "menu_id")})
	@Description("Access Menu")
	private List<Menu> menu_access;
	
	@ManyToMany(targetEntity = View.class, fetch = FetchType.LAZY)
	@JoinTable(name = "ir_ui_view_group_rel", joinColumns = {@JoinColumn(name = "group_id")}, inverseJoinColumns = {@JoinColumn(name = "view_id")})
	@Description("Views")
	private List<View> view_access;
	
	@Column
	private String comment;
	
	@Transient
	private Integer category_id;

	@ManyToOne(targetEntity = Category.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	@Description("Application")
	private Category category;
	
	@Column
	@Description("Color Index")
	private Integer color;
	
	@Transient
	@Description("Group Name")
	private String full_name;
	
	@Column
	@Description("Share Group")
	private Boolean share;
	
	@Column
	@Description("API Keys maximum duration days")
	private Float api_key_duration;
}
