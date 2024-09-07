package erp.base.schema.res.groups;

import java.util.List;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
	@ElementCollection(targetClass = Groups.class)
	@CollectionTable(name = "res_groups_implied_rel", joinColumns = {
			@JoinColumn(name = "hid", referencedColumnName = "gid")
	})
	@Description("Inherits")
	@Transient
	private List<String> implied_ids;
	
	/**
	 * 
	 */
	@ManyToMany(targetEntity = Groups.class)
	@JoinTable(name = "res_groups_implied_rel", joinColumns = {
			@JoinColumn(name = "hid", referencedColumnName = "gid")
	})
	@Relationship(type = "INHERITS")
	private List<Groups> implieds;
	
	/**
	 * 
	 */
	@ElementCollection(targetClass = Groups.class)
	@CollectionTable(name = "res_groups")
	@Description("Transitively inherits")
	@Transient
	private List<String> trans_implied_ids;
	
	/**
	 * 
	 */
	@ManyToMany(targetEntity = Groups.class)
	@Relationship(type = "TRANSITIVELY_INHERITS")
	private List<Groups> trans_implieds;

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

	public List<Groups> getImplieds() {
		return implieds;
	}

	public void setImplieds(List<Groups> implieds) {
		this.implieds = implieds;
	}

	public List<Groups> getTrans_implieds() {
		return trans_implieds;
	}

	public void setTrans_implieds(List<Groups> trans_implieds) {
		this.trans_implieds = trans_implieds;
	}
}
