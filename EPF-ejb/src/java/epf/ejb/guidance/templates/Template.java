/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.ejb.guidance.templates;

import java.io.File;
import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;

/**
 *
 * @author FOXCONN
 */
@Stateful
@LocalBean
public class Template {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private String MainDescription;

    /**
     * Get the value of MainDescription
     *
     * @return the value of MainDescription
     */
    public String getMainDescription() {
        return MainDescription;
    }

    /**
     * Set the value of MainDescription
     *
     * @param MainDescription new value of MainDescription
     */
    public void setMainDescription(String MainDescription) {
        this.MainDescription = MainDescription;
    }

    private List<File> AttachedFiles;

    /**
     * Get the value of AttachedFiles
     *
     * @return the value of AttachedFiles
     */
    public List<File> getAttachedFiles() {
        return AttachedFiles;
    }

    /**
     * Set the value of AttachedFiles
     *
     * @param AttachedFiles new value of AttachedFiles
     */
    public void setAttachedFiles(List<File> AttachedFiles) {
        this.AttachedFiles = AttachedFiles;
    }

}
