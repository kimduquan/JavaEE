package erp.base.schema.res.partner;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
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
@Table(name = "res_partner_category")
@Description("Partner Tags")
public class Category {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Tag Name")
	private String name;
	
	/**
	 * 
	 */
	@Column
	@Description("Color")
	private Integer color;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Category.class)
	@Description("Parent Category")
	private String parent_id;
	
	/**
	 * 
	 */
	@Column
	@OneToMany(targetEntity = Category.class)
	@ElementCollection(targetClass = Category.class)
	@CollectionTable(name = "res_partner_category")
	@Description("Child Tags")
	private List<String> child_ids;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Description("The active field allows you to hide the category without removing it.")
	private Boolean active = true;
	
	/**
	 * 
	 */
	@Column
	private String parent_path;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = Partner.class)
	@ElementCollection(targetClass = Partner.class)
	@CollectionTable(name = "res_partner")
	@Description("Partners")
	private List<String> partner_ids;
}
