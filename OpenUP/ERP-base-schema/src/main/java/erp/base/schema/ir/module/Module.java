package erp.base.schema.ir.module;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import erp.schema.util.EnumAttributeConverter;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_module_module")
@Description("Module")
@NodeEntity("Module")
public class Module {
	
	/**
	 * 
	 */
	public enum Status {
		/**
		 * 
		 */
		uninstallable,
		/**
		 * 
		 */
		uninstalled,
		/**
		 * 
		 */
		installed,
		/**
		 * 
		 */
		to_upgrade,
		/**
		 * 
		 */
		to_remove,
		/**
		 * 
		 */
		to_install
	}
	
	/**
	 * 
	 */
	public enum License {
		/**
		 * 
		 */
		GPL_2,
        /**
         * 
         */
        GPL_2__or__any__later__version,
        /**
         * 
         */
        GPL_3,
        /**
         * 
         */
        GPL_3__or__any__later__version,
        /**
         * 
         */
        AGPL_3,
        /**
         * 
         */
        LGPL_3,
        /**
         * 
         */
        Other__OSI__approved__licence,
        /**
         * 
         */
        OEEL_1,
        /**
         * 
         */
        OPL_1,
        /**
         * 
         */
        Other__proprietary
	}
	
	/**
	 * 
	 */
	public class LicenseEnumAttributeConverter extends EnumAttributeConverter<License> {
		/**
		 * 
		 */
		public LicenseEnumAttributeConverter() {
			super(License.class, null, "__", " ");
		}
	}
	
	/**
	 * 
	 */
	@jakarta.persistence.Id
	@Id
	private int id;

	/**
	 * 
	 */
	@Column(nullable = false, updatable = false)
	@NotNull
	@Description("Technical Name")
	@Property
	private String name;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Category")
	@Transient
	private String category_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = Category.class)
	@JoinColumn(name = "category_id", updatable = false)
	@Relationship(type = "CATEGORY")
	private Category category;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Module Name")
	@Property
	private String shortdesc;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Summary")
	@Property
	private String summary;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Description")
	@Property
	private String description;
	
	/**
	 * 
	 */
	@Column
	@Description("Description HTML")
	@Property
	private String description_html;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Author")
	@Property
	private String author;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Maintainer")
	@Property
	private String maintainer;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Contributors")
	@Property
	private String contributors;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Website")
	@Property
	private String website;
	
	/**
	 * 
	 */
	@Column
	@Description("Latest Version")
	@Property
	private String installed_version;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Installed Version")
	@Property
	private String latest_version;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Published Version")
	@Property
	private String published_version;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("URL")
	@Property
	private String url;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("100")
	@Description("Sequence")
	@Property
	private Integer sequence = 100;
	
	/**
	 * 
	 */
	@ElementCollection(targetClass = Dependency.class)
	@CollectionTable(name = "ir_module_module_dependency", joinColumns = {
			@JoinColumn(name = "module_id")
	})
	@Description("Dependencies")
	@Transient
	private List<String> dependencies_id;

	/**
	 * 
	 */
	@OneToMany(targetEntity = Dependency.class, mappedBy = "module_id")
	@Relationship(type = "DEPENDENCIES")
	private List<Dependency> dependencies;
	
	/**
	 * 
	 */
	@ElementCollection(targetClass = Exclusion.class)
	@CollectionTable(name = "ir_module_module_exclusion", joinColumns = {
			@JoinColumn(name = "module_id")
	})
	@Description("Exclusions")
	@Transient
	private List<String> exclusion_ids;

	/**
	 * 
	 */
	@OneToMany(targetEntity = Exclusion.class, mappedBy = "module_id")
	@Relationship(type = "EXCLUSIONS")
	private List<Exclusion> exclusions;
	
	/**
	 * 
	 */
	@Column
	@Description("Automatic Installation")
	@Property
	private Boolean auto_install;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Enumerated(EnumType.STRING)
	@DefaultValue("uninstallable")
	@Description("Status")
	@Property
	private Status state = Status.uninstallable;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@DefaultValue("false")
	@Description("Demo Data")
	@Property
	private Boolean demo = false;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Enumerated(EnumType.STRING)
	@Convert(converter = LicenseEnumAttributeConverter.class)
	@DefaultValue("LGPL-3")
	@Property
	private License license = License.LGPL_3;
	
	/**
	 * 
	 */
	@Column
	@Description("Menus")
	@Property
	private String menus_by_module;
	
	/**
	 * 
	 */
	@Column
	@Description("Reports")
	@Property
	private String reports_by_module;
	
	/**
	 * 
	 */
	@Column
	@Description("Views")
	@Property
	private String views_by_module;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Application")
	@Property
	private Boolean application;
	
	/**
	 * 
	 */
	@Column
	@Description("Icon URL")
	@Property
	private String icon;
	
	/**
	 * 
	 */
	@Column
	@Description("Icon")
	@Property
	private byte[] icon_image;
	
	/**
	 * 
	 */
	@Column
	@Description("Flag")
	@Property
	private String icon_flag;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("false")
	@Description("Odoo Enterprise Module")
	@Property
	private Boolean to_buy = false;
	
	/**
	 * 
	 */
	@Column
	@Property
	private Boolean has_iap;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public String getShortdesc() {
		return shortdesc;
	}

	public void setShortdesc(String shortdesc) {
		this.shortdesc = shortdesc;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription_html() {
		return description_html;
	}

	public void setDescription_html(String description_html) {
		this.description_html = description_html;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getMaintainer() {
		return maintainer;
	}

	public void setMaintainer(String maintainer) {
		this.maintainer = maintainer;
	}

	public String getContributors() {
		return contributors;
	}

	public void setContributors(String contributors) {
		this.contributors = contributors;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getInstalled_version() {
		return installed_version;
	}

	public void setInstalled_version(String installed_version) {
		this.installed_version = installed_version;
	}

	public String getLatest_version() {
		return latest_version;
	}

	public void setLatest_version(String latest_version) {
		this.latest_version = latest_version;
	}

	public String getPublished_version() {
		return published_version;
	}

	public void setPublished_version(String published_version) {
		this.published_version = published_version;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public List<String> getDependencies_id() {
		return dependencies_id;
	}

	public void setDependencies_id(List<String> dependencies_id) {
		this.dependencies_id = dependencies_id;
	}

	public List<String> getExclusion_ids() {
		return exclusion_ids;
	}

	public void setExclusion_ids(List<String> exclusion_ids) {
		this.exclusion_ids = exclusion_ids;
	}

	public Boolean getAuto_install() {
		return auto_install;
	}

	public void setAuto_install(Boolean auto_install) {
		this.auto_install = auto_install;
	}

	public Status getState() {
		return state;
	}

	public void setState(Status state) {
		this.state = state;
	}

	public Boolean getDemo() {
		return demo;
	}

	public void setDemo(Boolean demo) {
		this.demo = demo;
	}

	public License getLicense() {
		return license;
	}

	public void setLicense(License license) {
		this.license = license;
	}

	public String getMenus_by_module() {
		return menus_by_module;
	}

	public void setMenus_by_module(String menus_by_module) {
		this.menus_by_module = menus_by_module;
	}

	public String getReports_by_module() {
		return reports_by_module;
	}

	public void setReports_by_module(String reports_by_module) {
		this.reports_by_module = reports_by_module;
	}

	public String getViews_by_module() {
		return views_by_module;
	}

	public void setViews_by_module(String views_by_module) {
		this.views_by_module = views_by_module;
	}

	public Boolean getApplication() {
		return application;
	}

	public void setApplication(Boolean application) {
		this.application = application;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public byte[] getIcon_image() {
		return icon_image;
	}

	public void setIcon_image(byte[] icon_image) {
		this.icon_image = icon_image;
	}

	public String getIcon_flag() {
		return icon_flag;
	}

	public void setIcon_flag(String icon_flag) {
		this.icon_flag = icon_flag;
	}

	public Boolean getTo_buy() {
		return to_buy;
	}

	public void setTo_buy(Boolean to_buy) {
		this.to_buy = to_buy;
	}

	public Boolean getHas_iap() {
		return has_iap;
	}

	public void setHas_iap(Boolean has_iap) {
		this.has_iap = has_iap;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Dependency> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}

	public List<Exclusion> getExclusions() {
		return exclusions;
	}

	public void setExclusions(List<Exclusion> exclusions) {
		this.exclusions = exclusions;
	}
}
