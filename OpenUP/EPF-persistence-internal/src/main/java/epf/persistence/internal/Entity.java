package epf.persistence.internal;

import javax.persistence.metamodel.EntityType;

/**
 *
 * @author FOXCONN
 */
public class Entity<T> {
    
    /**
     * 
     */
    private EntityType<T> type;
    /**
     * 
     */
    private T object;

    public EntityType<T> getType() {
        return type;
    }

    public void setType(final EntityType<T> type) {
        this.type = type;
    }

    public T getObject() {
        return object;
    }

    public void setObject(final T object) {
        this.object = object;
    }
}
