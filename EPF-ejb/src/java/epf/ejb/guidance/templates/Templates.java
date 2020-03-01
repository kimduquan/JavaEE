/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.ejb.guidance.templates;

import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;

/**
 *
 * Alphabetical list of included templates.
 */
@Stateful
@LocalBean
public class Templates {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private List<Template> Contents;

    /**
     * Get the value of Contents
     *
     * @return the value of Contents
     */
    public List<Template> getContents() {
        return Contents;
    }

    /**
     * Set the value of Contents
     *
     * @param Contents new value of Contents
     */
    public void setContents(List<Template> Contents) {
        this.Contents = Contents;
    }

}
