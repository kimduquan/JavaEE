package erp.base.schema.ir.ui;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

import erp.base.schema.res.Groups;
import erp.schema.util.EnumAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ir_ui_menu")
@Description("Menu")
public class Menu {
	
	public enum Action {
		@Description("ir.actions.report")
		ir__actions__report,
		@Description("ir.actions.act_window")
        ir__actions__act_window,
        @Description("ir.actions.act_url")
        ir__actions__act_url,
        @Description("ir.actions.server")
        ir__actions__server,
        @Description("ir.actions.client")
        ir__actions__client
	}
	
	public class ActionAttributeConverter extends EnumAttributeConverter<Action> {
		public ActionAttributeConverter() {
			super(Action.class, null, ".", "__");
		}
	}
	
	@Id
	private int id;

	@Column(nullable = false)
	@NotNull
	@Description("Menu")
	private String name;
	
	@Column
	@DefaultValue("true")
	private Boolean active = true;
	
	@Column
	@DefaultValue("10")
	private Integer sequence = 10;
	
	@Transient
	private List<Integer> child_id;

	@OneToMany(targetEntity = Menu.class, fetch = FetchType.LAZY, mappedBy = "parent_id")
	@Description("Child IDs")
	private List<Menu> childs;
	
	@Transient
	private Integer parent_id;
	
	@ManyToOne(targetEntity = Menu.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@Description("Parent Menu")
	private Menu parent;
	
	@Column
	private String parent_path;
	
	@Transient
	private List<Integer> groups_id;
	
	@ManyToMany(targetEntity = Groups.class, fetch = FetchType.LAZY)
	@JoinTable(name = "ir_ui_menu_group_rel", joinColumns = {@JoinColumn(name = "menu_id")}, inverseJoinColumns = {@JoinColumn(name = "gid")})
	@Description("Groups")
	private List<Groups> groups;
	
	@Description("Full Path")
	private String complete_name;
	
	@Column
	@Description("Web Icon File")
	private String web_icon;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Action action;
	
	@Description("Web Icon Image")
	private byte[] web_icon_data;
}
