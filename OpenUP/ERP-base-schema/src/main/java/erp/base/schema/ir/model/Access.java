package erp.base.schema.ir.model;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.Model;
import erp.base.schema.res.Groups;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ir_model_access")
@Description("Model Access")
public class Access {
	
	@Id
	private int id;

	@Column(nullable = false)
	@NotNull
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
	
	@Transient
	private Integer group_id;
	
	@ManyToOne(targetEntity = Groups.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	@Description("Group")
	private Groups group;
	
	@Column
	@Description("Read Access")
	private Boolean perm_read;
	
	@Column
	@Description("Write Access")
	private Boolean perm_write;
	
	@Column
	@Description("Create Access")
	private Boolean perm_create;
	
	@Column
	@Description("Delete Access")
	private Boolean perm_unlink;

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

	public Integer getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}

	public Groups getGroup() {
		return group;
	}

	public void setGroup(Groups group) {
		this.group = group;
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
