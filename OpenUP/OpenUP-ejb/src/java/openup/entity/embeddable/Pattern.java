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
public class Pattern {
    
    private String Overview;

    /**
     * Get the value of Overview
     *
     * @return the value of Overview
     */
    public String getOverview() {
        return Overview;
    }

    /**
     * Set the value of Overview
     *
     * @param Overview new value of Overview
     */
    public void setOverview(String Overview) {
        this.Overview = Overview;
    }

    private String Structure;

    /**
     * Get the value of Structure
     *
     * @return the value of Structure
     */
    public String getStructure() {
        return Structure;
    }

    /**
     * Set the value of Structure
     *
     * @param Structure new value of Structure
     */
    public void setStructure(String Structure) {
        this.Structure = Structure;
    }

    private String Behavior;

    /**
     * Get the value of Behavior
     *
     * @return the value of Behavior
     */
    public String getBehavior() {
        return Behavior;
    }

    /**
     * Set the value of Behavior
     *
     * @param Behavior new value of Behavior
     */
    public void setBehavior(String Behavior) {
        this.Behavior = Behavior;
    }

    private String Example;

    /**
     * Get the value of Example
     *
     * @return the value of Example
     */
    public String getExample() {
        return Example;
    }

    /**
     * Set the value of Example
     *
     * @param Example new value of Example
     */
    public void setExample(String Example) {
        this.Example = Example;
    }

}
