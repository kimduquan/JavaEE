package erp.base.schema.ir.module;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Module {

	/**
	 * 
	 */
	@Column(nullable = false, updatable = false)
	@NotNull
	@Description("Technical Name")
	private String name;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@ManyToOne(targetEntity = Category.class)
	@Description("Category")
	private String category_id;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Module Name")
	private String shortdesc;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Summary")
	private String summary;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Description")
	private String description;
	
	/**
	 * 
	 */
	@Column
	@Description("Description HTML")
	private String description_html;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Author")
	private String author;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Maintainer")
	private String maintainer;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Contributors")
	private String contributors;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Website")
	private String website;
	
	/**
	 * 
	 */
	@Column
	@Description("Latest Version")
	private String installed_version;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Installed Version")
	private String latest_version;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Published Version")
	private String published_version;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("URL")
	private String url;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("100")
	@Description("Sequence")
	private Integer sequence = 100;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@OneToMany(targetEntity = Dependency.class)
	@ElementCollection(targetClass = Dependency.class)
	@CollectionTable(name = "ir_module_module_dependency")
	@Description("Dependencies")
	private List<String> dependencies_id;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@OneToMany(targetEntity = Exclusion.class)
	@ElementCollection(targetClass = Exclusion.class)
	@CollectionTable(name = "ir_module_module_exclusion")
	@Description("Exclusions")
	private List<String> exclusion_ids;
	
	/**
	 * 
	 */
	@Column
	@Description("Automatic Installation")
	private Boolean auto_install;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Enumerated(EnumType.STRING)
	@DefaultValue("uninstallable")
	@Description("Status")
	private String state = "uninstallable";
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@DefaultValue("false")
	@Description("Demo Data")
	private Boolean demo = false;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Enumerated(EnumType.STRING)
	@DefaultValue("LGPL-3")
	private String license = "LGPL-3";
	
	/**
	 * 
	 */
	@Column
	@Description("Menus")
	private String menus_by_module;
	
	/**
	 * 
	 */
	@Column
	@Description("Reports")
	private String reports_by_module;
	
	/**
	 * 
	 */
	@Column
	@Description("Views")
	private String views_by_module;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Application")
	private Boolean application;
	
	/**
	 * 
	 */
	@Column
	@Description("Icon URL")
	private String icon;
	
	/**
	 * 
	 */
	@Column
	@Description("Icon")
	private byte[] icon_image;
	
	/**
	 * 
	 */
	@Column
	@Description("Flag")
	private String icon_flag;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("false")
	@Description("Odoo Enterprise Module")
	private Boolean to_buy = false;
	
	/**
	 * 
	 */
	@Column
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Boolean getDemo() {
		return demo;
	}

	public void setDemo(Boolean demo) {
		this.demo = demo;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
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
}
