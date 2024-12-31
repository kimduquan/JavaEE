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
}
