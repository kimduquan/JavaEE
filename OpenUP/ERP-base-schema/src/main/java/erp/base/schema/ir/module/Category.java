package erp.base.schema.ir.module;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity(name = "ModuleCategory")
@Table(name = "ir_module_category")
@Description("Application")
public class Category {
	
	@Id
	private int id;

	@Column(nullable = false)
	@NotNull
	@Description("Name")
	private String name;
	
	@Transient
	private Integer parent_id;
	
	@ManyToOne(targetEntity = Category.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@Description("Parent Application")
	private Category parent;
	
	@Transient
	private List<Integer> child_ids;

	@OneToMany(targetEntity = Category.class, fetch = FetchType.LAZY, mappedBy = "parent_id")
	@Description("Child Applications")
	private List<Category> childs;
	
	@Transient
	private List<Integer> module_ids;
	
	@OneToMany(targetEntity = Module.class, fetch = FetchType.LAZY, mappedBy = "category_id")
	@Description("Modules")
	private List<Module> modules;
	
	@Column
	@Description("Description")
	private String description;
	
	@Column
	@Description("Sequence")
	private Integer sequence;
	
	@Column
	@DefaultValue("true")
	@Description("Visible")
	private Boolean visible = true;
	
	@Column
	@Description("Exclusive")
	private Boolean exclusive;
	
	@Description("External ID")
	private String xml_id;
}
