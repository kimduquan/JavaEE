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
}
