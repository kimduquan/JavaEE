package erp.base.schema.res.partner;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import erp.schema.util.NameAttributeConverter;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity(name = "PartnerCategory")
@Table(name = "res_partner_category")
@Description("Partner Tags")
@NodeEntity("Partner Tags")
public class Category {
	
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
	@Description("Tag Name")
	@Property
	@Convert(NameAttributeConverter.class)
	private String name;
	
	/**
	 * 
	 */
	@Column
	@Description("Color")
	@Property
	private Integer color;
	
	/**
	 * 
	 */
	@Column
	@Description("Parent Category")
	@Transient
	private String parent_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = Category.class)
	@JoinColumn(name = "parent_id")
	@Relationship("PARENT_CATEGORY")
	private Category parent;
	
	/**
	 * 
	 */
	@ElementCollection(targetClass = Category.class)
	@CollectionTable(name = "res_partner_category", joinColumns = {
			@JoinColumn(name = "parent_id")
	})
	@Description("Child Tags")
	@Transient
	private List<String> child_ids;

	/**
	 * 
	 */
	@OneToMany(targetEntity = Category.class, mappedBy = "parent_id")
	@Relationship("CHILD_TAGS")
	private List<String> childs;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Description("The active field allows you to hide the category without removing it.")
	@Property
	private Boolean active = true;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String parent_path;
	
	/**
	 * 
	 */
	@ElementCollection(targetClass = Partner.class)
	@CollectionTable(name = "res_partner", joinColumns = {
			@JoinColumn(name = "partner_id", referencedColumnName = "category_id")
	})
	@Description("Partners")
	@Transient
	private List<String> partner_ids;

	/**
	 * 
	 */
	@ManyToMany(targetEntity = Partner.class)
	@JoinTable(name = "res_partner", joinColumns = {
			@JoinColumn(name = "category_id"),
			@JoinColumn(name = "partner_id")
	})
	@Relationship(type = "PARTNERS")
	private List<Partner> partners;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public List<String> getChilds() {
		return childs;
	}

	public void setChilds(List<String> childs) {
		this.childs = childs;
	}

	public List<Partner> getPartners() {
		return partners;
	}

	public void setPartners(List<Partner> partners) {
		this.partners = partners;
	}
}
