package erp.base.schema.res.groups;

import java.util.List;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import erp.base.schema.ir.Rule;
import erp.base.schema.ir.model.Access;
import erp.base.schema.ir.module.Category;
import erp.base.schema.ir.ui.Menu;
import erp.base.schema.ir.ui.View;
import erp.base.schema.res.users.Users;
import erp.schema.util.NameAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
@NodeEntity("Access Groups")
public class Groups {
	
	/**
	 * 
	 */
	@jakarta.persistence.Id
	@Id
	private int id;

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Property
	@Convert(NameAttributeConverter.class)
	private String name;
	
	/**
	 * 
	 */
	@ManyToMany(targetEntity = Users.class)
	@JoinTable(name = "res_groups_users_rel", joinColumns = {
			@JoinColumn(name = "gid", referencedColumnName = "uid")
	})
	@Relationship(type = "USERS")
	private List<Users> users;
	
	/**
	 * 
	 */
	@OneToMany(targetEntity = Access.class, mappedBy = "group_id")
	@Description("Access Controls")
	@Relationship(type = "ACCESS_CONTROLS")
	private List<Access> model_access;
	
	/**
	 * 
	 */
	@ManyToMany(targetEntity = Rule.class)
	@JoinTable(name = "rule_group_rel", joinColumns = {
			@JoinColumn(name = "group_id", referencedColumnName = "rule_group_id")
	})
	@Description("Rules")
	@Relationship(type = "RULES")
	private List<Rule> rule_groups;
	
	/**
	 * 
	 */
	@ManyToMany(targetEntity = Menu.class)
	@JoinTable(name = "ir_ui_menu_group_rel", joinColumns = {
			@JoinColumn(name = "menu_id", referencedColumnName = "gid")
	})
	@Description("Access Menu")
	@Relationship(type = "ACCESS_MENU")
	private List<Menu> menu_access;
	
	/**
	 * 
	 */
	@ManyToMany(targetEntity = View.class)
	@JoinTable(name = "ir_ui_view_group_rel", joinColumns = {
			@JoinColumn(name = "view_id", referencedColumnName = "group_id")
	})
	@Description("Views")
	@Relationship(type = "VIEWS")
	private List<View> view_access;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String comment;
	
	/**
	 * 
	 */
	@Column
	@Description("Application")
	@Transient
	private String category_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = Category.class)
	@JoinColumn(name = "category_id")
	@Relationship(type = "APPLICATION")
	private Category category;
	
	/**
	 * 
	 */
	@Column
	@Description("Color Index")
	@Property
	private Integer color;
	
	/**
	 * 
	 */
	@Column
	@Description("Group Name")
	@Property
	private String full_name;
	
	/**
	 * 
	 */
	@Column
	@Description("Share Group")
	@Property
	private Boolean share;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}

	public List<Access> getModel_access() {
		return model_access;
	}

	public void setModel_access(List<Access> model_access) {
		this.model_access = model_access;
	}

	public List<Rule> getRule_groups() {
		return rule_groups;
	}

	public void setRule_groups(List<Rule> rule_groups) {
		this.rule_groups = rule_groups;
	}

	public List<Menu> getMenu_access() {
		return menu_access;
	}

	public void setMenu_access(List<Menu> menu_access) {
		this.menu_access = menu_access;
	}

	public List<View> getView_access() {
		return view_access;
	}

	public void setView_access(List<View> view_access) {
		this.view_access = view_access;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public Integer getColor() {
		return color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public Boolean getShare() {
		return share;
	}

	public void setShare(Boolean share) {
		this.share = share;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
