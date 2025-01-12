package erp.base.schema.ir.module;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.module.module.Dependency;
import erp.base.schema.ir.module.module.Exclusion;
import erp.base.schema.res.Country;
import erp.schema.util.EnumAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

@Entity
@Table(name = "ir_module_module")
@Description("Module")
public class Module {
	
	public enum License {
		@Description("GPL Version 2")
		GPL_2,
		@Description("GPL-2 or later version")
        GPL_2__or__any__later__version,
        @Description("GPL Version 3")
        GPL_3,
        @Description("GPL-3 or later version")
        GPL_3__or__any__later__version,
        @Description("Affero GPL-3")
        AGPL_3,
        @Description("LGPL Version 3")
        LGPL_3,
        @Description("Other OSI Approved License")
        Other__OSI__approved__licence,
        @Description("Odoo Enterprise Edition License v1.0")
        OEEL_1,
        @Description("Odoo Proprietary License v1.0")
        OPL_1,
        @Description("Other Proprietary")
        Other__proprietary
	}
	
	public class LicenseEnumAttributeConverter extends EnumAttributeConverter<License> {
		public LicenseEnumAttributeConverter() {
			super(License.class, null, "__", " ");
		}
	}
	
	@Id
	private int id;

	@Column(nullable = false, updatable = false)
	@NotNull
	@Description("Technical Name")
	private String name;
	
	@Transient
	private Integer category_id;

	@ManyToOne(targetEntity = Category.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", updatable = false)
	@Description("Category")
	private Category category;
	
	@Column(updatable = false)
	@Description("Module Name")
	private String shortdesc;
	
	@Column(updatable = false)
	@Description("Summary")
	private String summary;
	
	@Column(updatable = false)
	@Description("Description")
	private String description;
	
	@Transient
	@Description("Description HTML")
	private String description_html;
	
	@Column(updatable = false)
	@Description("Author")
	private String author;
	
	@Column(updatable = false)
	@Description("Maintainer")
	private String maintainer;
	
	@Column(updatable = false)
	@Description("Contributors")
	private String contributors;
	
	@Column(updatable = false)
	@Description("Website")
	private String website;
	
	@Transient
	@Description("Latest Version")
	private String installed_version;
	
	@Column(updatable = false)
	@Description("Installed Version")
	private String latest_version;
	
	@Column(updatable = false)
	@Description("Published Version")
	private String published_version;
	
	@Column(updatable = false)
	@Description("URL")
	private String url;
	
	@Column
	@DefaultValue("100")
	@Description("Sequence")
	private Integer sequence = 100;
	
	@Transient
	private List<Integer> dependencies_id;

	@OneToMany(targetEntity = Dependency.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "module_id")
	@Description("Dependencies")
	private List<Dependency> dependencies;
	
	@Transient
	private List<Integer> country_ids;
	
	@ManyToMany(targetEntity = Country.class, fetch = FetchType.LAZY)
	@JoinTable(name = "module_country", joinColumns = {@JoinColumn(name = "module_id")}, inverseJoinColumns = {@JoinColumn(name = "country_id")})
	private List<Country> countries;
	
	@Transient
	private List<Integer> exclusion_ids;

	@OneToMany(targetEntity = Exclusion.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "module_id")
	@Description("Exclusions")
	private List<Exclusion> exclusions;
	
	@Column
	@Description("Automatic Installation")
	private Boolean auto_install;
	
	@Column(updatable = false)
	@Enumerated(EnumType.STRING)
	@DefaultValue("uninstallable")
	@Description("Status")
	private State state = State.uninstallable;
	
	@Column(updatable = false)
	@DefaultValue("false")
	@Description("Demo Data")
	private Boolean demo = false;
	
	@Column(updatable = false)
	@Enumerated(EnumType.STRING)
	@Convert(converter = LicenseEnumAttributeConverter.class)
	@DefaultValue("LGPL-3")
	private License license = License.LGPL_3;
	
	@Column
	@Description("Menus")
	private String menus_by_module;
	
	@Column
	@Description("Reports")
	private String reports_by_module;
	
	@Column
	@Description("Views")
	private String views_by_module;
	
	@Column(updatable = false)
	@Description("Application")
	private Boolean application;
	
	@Column
	@Description("Icon URL")
	private String icon;
	
	@Transient
	@Description("Icon")
	private byte[] icon_image;
	
	@Transient
	@Description("Flag")
	private String icon_flag;
	
	@Column
	@DefaultValue("false")
	@Description("Odoo Enterprise Module")
	private Boolean to_buy = false;
	
	@Transient
	private Boolean has_iap;

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

	public Integer getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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

	public List<Integer> getDependencies_id() {
		return dependencies_id;
	}

	public void setDependencies_id(List<Integer> dependencies_id) {
		this.dependencies_id = dependencies_id;
	}

	public List<Dependency> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}

	public List<Integer> getCountry_ids() {
		return country_ids;
	}

	public void setCountry_ids(List<Integer> country_ids) {
		this.country_ids = country_ids;
	}

	public List<Country> getCountries() {
		return countries;
	}

	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}

	public List<Integer> getExclusion_ids() {
		return exclusion_ids;
	}

	public void setExclusion_ids(List<Integer> exclusion_ids) {
		this.exclusion_ids = exclusion_ids;
	}

	public List<Exclusion> getExclusions() {
		return exclusions;
	}

	public void setExclusions(List<Exclusion> exclusions) {
		this.exclusions = exclusions;
	}

	public Boolean getAuto_install() {
		return auto_install;
	}

	public void setAuto_install(Boolean auto_install) {
		this.auto_install = auto_install;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
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
}
