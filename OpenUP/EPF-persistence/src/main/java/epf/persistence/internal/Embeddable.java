package epf.persistence.internal;

import javax.persistence.metamodel.EmbeddableType;

/**
 *
 * @author FOXCONN
 */
public class Embeddable<T> {
    
    /**
     * 
     */
    private EmbeddableType<T> type;
    /**
     * 
     */
    private T object;

    public EmbeddableType<T> getType() {
        return type;
    }

    public void setType(final EmbeddableType<T> type) {
        this.type = type;
    }

    public T getObject() {
        return object;
    }

    public void setObject(final T object) {
        this.object = object;
    }
}
