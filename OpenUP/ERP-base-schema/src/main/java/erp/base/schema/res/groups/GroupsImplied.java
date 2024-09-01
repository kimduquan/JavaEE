package erp.base.schema.res.groups;

import java.util.List;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

/**
 * 
 */
@Entity
@NodeEntity
public class GroupsImplied extends Groups {

	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = Groups.class)
	@ElementCollection(targetClass = Groups.class)
	@CollectionTable(name = "res_groups")
	@Description("Inherits")
	@Property
	@Relationship(type = "INHERITS")
	private List<String> implied_ids;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = Groups.class)
	@ElementCollection(targetClass = Groups.class)
	@CollectionTable(name = "res_groups")
	@Description("Transitively inherits")
	@Property
	@Relationship(type = "TRANSITIVELY_INHERITS")
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
