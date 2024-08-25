package erp.base.schema.res.country;

import java.util.List;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "res_country_group")
@Description("Country Group")
public class Group {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	private String name;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = Country.class)
	@ElementCollection(targetClass = Country.class)
	@CollectionTable(name = "res_country")
	@Description("Countries")
	private List<String> country_ids;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getCountry_ids() {
		return country_ids;
	}

	public void setCountry_ids(List<String> country_ids) {
		this.country_ids = country_ids;
	}
}
