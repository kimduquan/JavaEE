/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.entity.embeddable;

import javax.persistence.Embeddable;

/**
 *
 * @author FOXCONN
 */
@Embeddable
public class Reference {
    
    private String ReferenceName;

    /**
     * Get the value of ReferenceName
     *
     * @return the value of ReferenceName
     */
    public String getReferenceName() {
        return ReferenceName;
    }

    /**
     * Set the value of ReferenceName
     *
     * @param ReferenceName new value of ReferenceName
     */
    public void setReferenceName(String ReferenceName) {
        this.ReferenceName = ReferenceName;
    }

    private String OwnerAuthor;

    /**
     * Get the value of OwnerAuthor
     *
     * @return the value of OwnerAuthor
     */
    public String getOwnerAuthor() {
        return OwnerAuthor;
    }

    /**
     * Set the value of OwnerAuthor
     *
     * @param OwnerAuthor new value of OwnerAuthor
     */
    public void setOwnerAuthor(String OwnerAuthor) {
        this.OwnerAuthor = OwnerAuthor;
    }

    private String WhereStored;

    /**
     * Get the value of WhereStored
     *
     * @return the value of WhereStored
     */
    public String getWhereStored() {
        return WhereStored;
    }

    /**
     * Set the value of WhereStored
     *
     * @param WhereStored new value of WhereStored
     */
    public void setWhereStored(String WhereStored) {
        this.WhereStored = WhereStored;
    }

}
