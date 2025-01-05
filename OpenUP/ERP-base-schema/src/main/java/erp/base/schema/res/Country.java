package erp.base.schema.res;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.ui.View;
import erp.base.schema.res.country.Group;
import erp.base.schema.res.country.State;
import jakarta.persistence.Column;
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
@Table(name = "res_country")
@Description("Country")
public class Country {
	
	public enum NamePosition {
		@Description("Before Address")
		before,
		@Description("After Address")
        after
	}
	
	@Id
	private int id;

	@Column(nullable = false)
	@NotNull
	@Description("Country Name")
	private String name;
	
	@Column(nullable = false, length = 2)
	@NotNull
	@Description("Country Code")
	private String code;
	
	@Column
	@DefaultValue("%(street)s\\n%(street2)s\\n%(city)s %(state_code)s %(zip)s\\n%(country_name)s")
	@Description("Layout in Reports")
	private String address_format = "%(street)s\\n%(street2)s\\n%(city)s %(state_code)s %(zip)s\\n%(country_name)s";
	
	@Transient
	private Integer address_view_id;

	@ManyToOne(targetEntity = View.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "address_view_id")
	@Description("Input View")
	private View address_view;
	
	@Transient
	private Integer currency_id;
	
	@ManyToOne(targetEntity = Currency.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "currency_id")
	@Description("Currency")
	private Currency currency;
	
	@Transient
	@Description("Flag")
	private String image_url;
	
	@Column
	@Description("Country Calling Code")
	private Integer phone_code;
	
	@Transient
	private List<Integer> country_group_ids;
	
	@ManyToMany(targetEntity = Group.class, fetch = FetchType.LAZY)
	@JoinTable(name = "res_country_res_country_group_rel", joinColumns = {@JoinColumn(name = "res_country_id")}, inverseJoinColumns = {@JoinColumn(name = "res_country_group_id")})
	@Description("Country Groups")
	private List<Group> country_groups;
	
	@Transient
	private List<Integer> state_ids;

	@OneToMany(targetEntity = State.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id")
	@Description("States")
	private List<State> states;
	
	@Column
	@Enumerated(EnumType.STRING)
	@DefaultValue("before")
	@Description("Customer Name Position")
	private NamePosition name_position = NamePosition.before;
	
	@Column
	@Description("Vat Label")
	private String vat_label;
	
	@Column
	@DefaultValue("false")
	private Boolean state_required = false;
	
	@Column
	@DefaultValue("true")
	private Boolean zip_required = true;
}
