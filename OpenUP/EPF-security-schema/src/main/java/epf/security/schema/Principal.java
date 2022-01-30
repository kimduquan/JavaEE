package epf.security.schema;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author FOXCONN
 */
@Type(Security.PRINCIPAL)
@Schema(name = Security.PRINCIPAL, title = "Principal")
@Entity(name = Security.PRINCIPAL)
@Table(schema = Security.SCHEMA, name = "PRINCIPAL", indexes = {@Index(columnList = "NAME")})
public class Principal implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    @Id
    @Column(name = "NAME")
    private String name;
    
    /**
     * 
     */
    @ElementCollection
    @CollectionTable(
    		name="PRINCIPAL_CLAIMS", 
    		schema = Security.SCHEMA,
    		uniqueConstraints = {@UniqueConstraint(columnNames = {"PRINCIPAL_NAME", "NAME"})}
    		)
    @MapKeyColumn(name = "NAME")
    @Column(name = "VALUE")
    private Map<String, String> claims = new ConcurrentHashMap<>();
    
    @Override
    public String toString() {
    	return String.format("%s@%s", getClass().getName(), name);
    }
    
    public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Map<String, String> getClaims() {
		return claims;
	}

	public void setClaims(final Map<String, String> claims) {
		this.claims = claims;
	}
}
