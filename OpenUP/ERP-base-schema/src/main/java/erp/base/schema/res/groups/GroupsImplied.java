package erp.base.schema.res.groups;

import java.util.List;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

/**
 * 
 */
@Entity
public class GroupsImplied extends Groups {

	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = Groups.class)
	@ElementCollection(targetClass = Groups.class)
	@CollectionTable(name = "res_groups")
	@Description("Inherits")
	private List<String> implied_ids;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = Groups.class)
	@ElementCollection(targetClass = Groups.class)
	@CollectionTable(name = "res_groups")
	@Description("Transitively inherits")
	private List<String> trans_implied_ids;

	public List<String> getImplied_ids() {
		return implied_ids;
	}

	public void setImplied_ids(List<String> implied_ids) {
		this.implied_ids = implied_ids;
	}

	public List<String> getTrans_implied_ids() {
		return trans_implied_ids;
	}

	public void setTrans_implied_ids(List<String> trans_implied_ids) {
		this.trans_implied_ids = trans_implied_ids;
	}
}
