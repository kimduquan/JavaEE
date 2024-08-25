package erp.base.schema.ir.ui;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.groups.Groups;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getChild_id() {
		return child_id;
	}

	public void setChild_id(String child_id) {
		this.child_id = child_id;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getParent_path() {
		return parent_path;
	}

	public void setParent_path(String parent_path) {
		this.parent_path = parent_path;
	}

	public List<String> getGroups_id() {
		return groups_id;
	}

	public void setGroups_id(List<String> groups_id) {
		this.groups_id = groups_id;
	}

	public String getComplete_name() {
		return complete_name;
	}

	public void setComplete_name(String complete_name) {
		this.complete_name = complete_name;
	}

	public String getWeb_icon() {
		return web_icon;
	}

	public void setWeb_icon(String web_icon) {
		this.web_icon = web_icon;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public byte[] getWeb_icon_data() {
		return web_icon_data;
	}

	public void setWeb_icon_data(byte[] web_icon_data) {
		this.web_icon_data = web_icon_data;
	}
}
