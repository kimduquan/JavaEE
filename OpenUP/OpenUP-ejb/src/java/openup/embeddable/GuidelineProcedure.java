/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.embeddable;

import javax.persistence.Embeddable;

/**
 *
 * @author FOXCONN
 */
@Embeddable
public class GuidelineProcedure {
    
    private String GuidelineOrProcedure;

    /**
     * Get the value of GuidelineOrProcedure
     *
     * @return the value of GuidelineOrProcedure
     */
    public String getGuidelineOrProcedure() {
        return GuidelineOrProcedure;
    }

    /**
     * Set the value of GuidelineOrProcedure
     *
     * @param GuidelineOrProcedure new value of GuidelineOrProcedure
     */
    public void setGuidelineOrProcedure(String GuidelineOrProcedure) {
        this.GuidelineOrProcedure = GuidelineOrProcedure;
    }

    private String Owner;

    /**
     * Get the value of Owner
     *
     * @return the value of Owner
     */
    public String getOwner() {
        return Owner;
    }

    /**
     * Set the value of Owner
     *
     * @param Owner new value of Owner
     */
    public void setOwner(String Owner) {
        this.Owner = Owner;
    }

    private String UsedBy;

    /**
     * Get the value of UsedBy
     *
     * @return the value of UsedBy
     */
    public String getUsedBy() {
        return UsedBy;
    }

    /**
     * Set the value of UsedBy
     *
     * @param UsedBy new value of UsedBy
     */
    public void setUsedBy(String UsedBy) {
        this.UsedBy = UsedBy;
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
