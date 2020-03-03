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
public class URPS {
    
    private String Usability;

    /**
     * Get the value of Usability
     *
     * @return the value of Usability
     */
    public String getUsability() {
        return Usability;
    }

    /**
     * Set the value of Usability
     *
     * @param Usability new value of Usability
     */
    public void setUsability(String Usability) {
        this.Usability = Usability;
    }

    private String Reliability;

    /**
     * Get the value of Reliability
     *
     * @return the value of Reliability
     */
    public String getReliability() {
        return Reliability;
    }

    /**
     * Set the value of Reliability
     *
     * @param Reliability new value of Reliability
     */
    public void setReliability(String Reliability) {
        this.Reliability = Reliability;
    }

    private String Performance;

    /**
     * Get the value of Performance
     *
     * @return the value of Performance
     */
    public String getPerformance() {
        return Performance;
    }

    /**
     * Set the value of Performance
     *
     * @param Performance new value of Performance
     */
    public void setPerformance(String Performance) {
        this.Performance = Performance;
    }

    private String Supportability;

    /**
     * Get the value of Supportability
     *
     * @return the value of Supportability
     */
    public String getSupportability() {
        return Supportability;
    }

    /**
     * Set the value of Supportability
     *
     * @param Supportability new value of Supportability
     */
    public void setSupportability(String Supportability) {
        this.Supportability = Supportability;
    }

}
