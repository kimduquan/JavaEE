package erp.base.schema.res.groups;

import java.util.List;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import erp.base.schema.ir.Rule;
import erp.base.schema.ir.model.Access;
import erp.base.schema.ir.module.Category;
import erp.base.schema.ir.ui.Menu;
import erp.base.schema.ir.ui.View;
import erp.base.schema.res.users.Users;
import erp.schema.util.NameAttributeConverter;
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
	@Column
	@ManyToMany(targetEntity = Users.class)
	@ElementCollection(targetClass = Users.class)
	@CollectionTable(name = "res_users")
	@Property
	@Relationship(type = "USERS")
	private List<String> users;
	
	/**
	 * 
	 */
	@Column
	@OneToMany(targetEntity = Access.class)
	@ElementCollection(targetClass = Access.class)
	@CollectionTable(name = "ir_model_access")
	@Description("Access Controls")
	@Property
	@Relationship(type = "MODEL_ACCESS")
	private List<String> model_access;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = Rule.class)
	@ElementCollection(targetClass = Rule.class)
	@CollectionTable(name = "ir_rule")
	@Description("Rules")
	@Property
	@Relationship(type = "RULES")
	private List<String> rule_groups;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = Menu.class)
	@ElementCollection(targetClass = Menu.class)
	@CollectionTable(name = "ir_ui_menu")
	@Description("Access Menu")
	@Property
	@Relationship(type = "MODEL_ACCESS")
	private List<String> menu_access;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = View.class)
	@ElementCollection(targetClass = View.class)
	@CollectionTable(name = "ir_ui_view")
	@Description("Views")
	@Property
	@Relationship(type = "VIEW_ACCESS")
	private List<String> view_access;
	
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
	@ManyToOne(targetEntity = Category.class)
	@Description("Application")
	@Property
	@Relationship(type = "APPLICATION")
	private String category_id;
	
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

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

	public List<String> getModel_access() {
		return model_access;
	}

	public void setModel_access(List<String> model_access) {
		this.model_access = model_access;
	}

	public List<String> getRule_groups() {
		return rule_groups;
	}

	public void setRule_groups(List<String> rule_groups) {
		this.rule_groups = rule_groups;
	}

	public List<String> getMenu_access() {
		return menu_access;
	}

	public void setMenu_access(List<String> menu_access) {
		this.menu_access = menu_access;
	}

	public List<String> getView_access() {
		return view_access;
	}

	public void setView_access(List<String> view_access) {
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
}
