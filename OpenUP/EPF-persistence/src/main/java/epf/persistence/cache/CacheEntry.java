package epf.persistence.cache;

import jakarta.nosql.Column;
import jakarta.nosql.Id;
import jakarta.nosql.Entity;

@Entity
public class CacheEntry {

	@Id
    private String key;
    
    @Column
    private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
