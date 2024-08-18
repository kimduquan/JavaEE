package erp.schema.res;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.schema.ir.ui.View;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "res_country")
@Description("Country")
public class Country {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Country Name")
	private String name;
	
	/**
	 * 
	 */
	@Column(nullable = false, length = 2)
	@NotNull
	@Description("Country Code")
	private String code;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("%(street)s\\n%(street2)s\\n%(city)s %(state_code)s %(zip)s\\n%(country_name)s")
	@Description("Layout in Reports")
	private String address_format = "%(street)s\\n%(street2)s\\n%(city)s %(state_code)s %(zip)s\\n%(country_name)s";
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = View.class)
	@Description("Input View")
	private String address_view_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Currency.class)
	@Description("Currency")
	private String currency_id;
	
	/**
	 * 
	 */
	@Column
	@Description("Flag")
	private String image_url;
	
	/**
	 * 
	 */
	@Column
	@Description("Country Calling Code")
	private Integer phone_code;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = CountryGroup.class)
	@ElementCollection(targetClass = CountryGroup.class)
	@CollectionTable(name = "res_country_group")
	@Description("Country Groups")
	private List<String> country_group_ids;
	
	/**
	 * 
	 */
	@Column
	@OneToMany(targetEntity = CountryState.class)
	@ElementCollection(targetClass = CountryState.class)
	@CollectionTable(name = "res_country_state")
	@Description("States")
	private List<String> state_ids;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@DefaultValue("before")
	@Description("Customer Name Position")
	private String name_position = "before";
	
	/**
	 * 
	 */
	@Column
	@Description("Vat Label")
	private String vat_label;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("false")
	private Boolean state_required = false;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	private Boolean zip_required = true;
}
