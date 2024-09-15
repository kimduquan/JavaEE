package erp.base.schema.ir.ui;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import erp.base.schema.res.groups.Groups;
import erp.schema.util.EnumAttributeConverter;
import erp.schema.util.NameAttributeConverter;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_ui_menu")
@Description("Menu")
@NodeEntity("Menu")
public class Menu {
	
	/**
	 * 
	 */
	public enum Action {
		/**
		 * 
		 */
		ir__actions__report,
        /**
         * 
         */
        ir__actions__act_window,
        /**
         * 
         */
        ir__actions__act_url,
        /**
         * 
         */
        ir__actions__server,
        /**
         * 
         */
        ir__actions__client
	}
	
	/**
	 * 
	 */
	public class ActionAttributeConverter extends EnumAttributeConverter<Action> {
		/**
		 * 
		 */
		public ActionAttributeConverter() {
			super(Action.class, null, ".", "__");
		}
	}
	
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
	@Description("Menu")
	@Property
	@Convert(NameAttributeConverter.class)
	private String name;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Property
	private Boolean active = true;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("10")
	@Property
	private Integer sequence = 10;
	
	/**
	 * 
	 */
	@ElementCollection
	@CollectionTable(name = "ir_ui_menu", joinColumns = {
			@JoinColumn(name = "parent_id", referencedColumnName = "id")
	})
	@Description("Child IDs")
	@Transient
	private List<Integer> child_id;

	/**
	 * 
	 */
	@OneToMany(targetEntity = Menu.class, mappedBy = "parent_id")
	@Relationship(type = "CHILD_IDS")
	private List<Menu> childs;
	
	/**
	 * 
	 */
	@Column(insertable = false, updatable = false)
	@Transient
	private Integer parent_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = Menu.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@Relationship(type = "PARENT_MENU")
	private Menu parent;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String parent_path;
	
	/**
	 * 
	 */
	@ElementCollection
	@CollectionTable(name = "ir_ui_menu_group_rel", joinColumns = {@JoinColumn(name = "menu_id")})
	@Column(name = "gid")
	@Description("Groups")
	@Transient
	private List<Integer> groups_id;
	
	/**
	 * 
	 */
	@ManyToMany(targetEntity = Groups.class)
	@JoinTable(name = "ir_ui_menu_group_rel", joinColumns = {@JoinColumn(name = "menu_id")}, inverseJoinColumns = {@JoinColumn(name = "gid")})
	@Relationship(type = "GROUPS")
	private List<Groups> groups;
	
	/**
	 * 
	 */
	@jakarta.persistence.Transient
	@Description("Full Path")
	@Transient
	private String complete_name;
	
	/**
	 * 
	 */
	@Column
	@Description("Web Icon File")
	@Property
	private String web_icon;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@jakarta.persistence.Convert(converter = ActionAttributeConverter.class)
	@Property
	private Action action;
	
	/**
	 * 
	 */
	@jakarta.persistence.Transient
	@Description("Web Icon Image")
	@Transient
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

	public List<Integer> getChild_id() {
		return child_id;
	}

	public void setChild_id(List<Integer> child_id) {
		this.child_id = child_id;
	}

	public Integer getParent_id() {
		return parent_id;
	}

	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}

	public String getParent_path() {
		return parent_path;
	}

	public void setParent_path(String parent_path) {
		this.parent_path = parent_path;
	}

	public List<Integer> getGroups_id() {
		return groups_id;
	}

	public void setGroups_id(List<Integer> groups_id) {
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

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public byte[] getWeb_icon_data() {
		return web_icon_data;
	}

	public void setWeb_icon_data(byte[] web_icon_data) {
		this.web_icon_data = web_icon_data;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Menu> getChilds() {
		return childs;
	}

	public void setChilds(List<Menu> childs) {
		this.childs = childs;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public List<Groups> getGroups() {
		return groups;
	}

	public void setGroups(List<Groups> groups) {
		this.groups = groups;
	}
}
