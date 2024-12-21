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
}
