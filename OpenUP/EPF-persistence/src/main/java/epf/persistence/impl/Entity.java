/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.persistence.impl;

import javax.persistence.metamodel.EntityType;

/**
 *
 * @author FOXCONN
 */
public class Entity<T> {
    
    /**
     * 
     */
    private transient EntityType<T> type;
    /**
     * 
     */
    private transient T object;

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
