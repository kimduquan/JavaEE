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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getColor() {
		return color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public List<String> getChild_ids() {
		return child_ids;
	}

	public void setChild_ids(List<String> child_ids) {
		this.child_ids = child_ids;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getParent_path() {
		return parent_path;
	}

	public void setParent_path(String parent_path) {
		this.parent_path = parent_path;
	}

	public List<String> getPartner_ids() {
		return partner_ids;
	}

	public void setPartner_ids(List<String> partner_ids) {
		this.partner_ids = partner_ids;
	}
}
