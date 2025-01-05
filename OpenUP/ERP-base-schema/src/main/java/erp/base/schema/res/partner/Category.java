package erp.base.schema.res.partner;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.Partner;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity(name = "PartnerCategory")
@Table(name = "res_partner_category")
@Description("Partner Tags")
public class Category {
	
	@Id
	private int id;

	@Column(nullable = false)
	@NotNull
	@Description("Tag Name")
	private String name;
	
	@Column
	@Description("Color")
	private Integer color;
	
	@Transient
	private Integer parent_id;
	
	@ManyToOne(targetEntity = Category.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@Description("Category")
	private Category parent;
	
	@Transient
	private List<Integer> child_ids;

	@OneToMany(targetEntity = Category.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@Description("Child Tags")
	private List<Category> childs;
	
	@Column
	@DefaultValue("true")
	@Description("The active field allows you to hide the category without removing it.")
	private Boolean active = true;
	
	@Column
	private String parent_path;
	
	@Transient
	private List<Integer> partner_ids;

	@ManyToMany(targetEntity = Partner.class, fetch = FetchType.LAZY)
	@JoinTable(name = "res_partner_res_partner_category_rel", joinColumns = {@JoinColumn(name = "partner_id")}, inverseJoinColumns = {@JoinColumn(name = "category_id")})
	@Description("Partners")
	private List<Partner> partners;
}
