package erp.base.schema.ir;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.Groups;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ir_rule")
@Description("Record Rule")
public class Rule {
	
	@Id
	private int id;

	@Column
	private String name;
	
	@Column
	@DefaultValue("true")
	private Boolean active = true;
	
	@Transient
	private Integer model_id;
	
	@ManyToOne(targetEntity = Model.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "model_id", nullable = false)
	@NotNull
	@Description("Model")
	private Model model;
	
	@ManyToMany(targetEntity = Groups.class)
	@JoinTable(name = "rule_group_rel", joinColumns = {@JoinColumn(name = "rule_group_id")}, inverseJoinColumns = {@JoinColumn(name = "group_id")})
	private List<Groups> groups;
	
	@Column
	@Description("Domain")
	private String domain_force;
	
	@Column
	@DefaultValue("true")
	@Description("Read")
	private Boolean perm_read = true;
	
	@Column
	@DefaultValue("true")
	@Description("Write")
	private Boolean perm_write = true;
	
	@Column
	@DefaultValue("true")
	@Description("Create")
	private Boolean perm_create = true;
	
	@Column
	@DefaultValue("true")
	@Description("Delete")
	private Boolean perm_unlink = true;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public Integer getModel_id() {
		return model_id;
	}

	public void setModel_id(Integer model_id) {
		this.model_id = model_id;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public List<Groups> getGroups() {
		return groups;
	}

	public void setGroups(List<Groups> groups) {
		this.groups = groups;
	}

	public String getDomain_force() {
		return domain_force;
	}

	public void setDomain_force(String domain_force) {
		this.domain_force = domain_force;
	}

	public Boolean getPerm_read() {
		return perm_read;
	}

	public void setPerm_read(Boolean perm_read) {
		this.perm_read = perm_read;
	}

	public Boolean getPerm_write() {
		return perm_write;
	}

	public void setPerm_write(Boolean perm_write) {
		this.perm_write = perm_write;
	}

	public Boolean getPerm_create() {
		return perm_create;
	}

	public void setPerm_create(Boolean perm_create) {
		this.perm_create = perm_create;
	}

	public Boolean getPerm_unlink() {
		return perm_unlink;
	}

	public void setPerm_unlink(Boolean perm_unlink) {
		this.perm_unlink = perm_unlink;
	}
}
