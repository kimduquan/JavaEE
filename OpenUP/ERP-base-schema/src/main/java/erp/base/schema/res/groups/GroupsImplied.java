package erp.base.schema.res.groups;

import java.util.List;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;

import erp.base.schema.res.Groups;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;

/**
 * 
 */
@Entity
@PrimaryKeyJoinColumn(name = "gid")
@NodeEntity
public class GroupsImplied extends Groups {

	/**
	 * 
	 */
	@ElementCollection
	@CollectionTable(name = "res_groups_implied_rel", joinColumns = {@JoinColumn(name = "gid")})
	@Column(name = "hid")
	@Description("Inherits")
	@Transient
	private List<Integer> implied_ids;
	
	/**
	 * 
	 */
	@ManyToMany(targetEntity = Groups.class)
	@JoinTable(name = "res_groups_implied_rel", joinColumns = {@JoinColumn(name = "gid")}, inverseJoinColumns = {@JoinColumn(name = "hid")})
	@Relationship(type = "INHERITS")
	private List<Groups> implieds;
	
	/**
	 * 
	 */
	@jakarta.persistence.Transient
	@Description("Transitively inherits")
	@Transient
	private List<Integer> trans_implied_ids;

	public List<Integer> getImplied_ids() {
		return implied_ids;
	}

	public void setImplied_ids(List<Integer> implied_ids) {
		this.implied_ids = implied_ids;
	}

	public List<Integer> getTrans_implied_ids() {
		return trans_implied_ids;
	}

	public void setTrans_implied_ids(List<Integer> trans_implied_ids) {
		this.trans_implied_ids = trans_implied_ids;
	}

	public List<Groups> getImplieds() {
		return implieds;
	}

	public void setImplieds(List<Groups> implieds) {
		this.implieds = implieds;
	}
}
