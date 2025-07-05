package erp.base.schema.res.country;

import java.util.List;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.Country;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "res_country_group")
@Description("Country Group")
public class Group {
	
	@Id
	private int id;

	@Column(nullable = false)
	@NotNull
	private String name;
	
	@Transient
	private List<Integer> country_ids;

	@ManyToMany(targetEntity = Country.class)
	@JoinTable(name = "res_country_res_country_group_rel", joinColumns = {@JoinColumn(name = "res_country_group_id")}, inverseJoinColumns = {@JoinColumn(name = "res_country_id")})
	@Description("Countries")
	private List<Country> countries;

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
}
