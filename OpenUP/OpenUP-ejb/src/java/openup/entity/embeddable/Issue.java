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
public class Issue {
    
    private String Issue;

    /**
     * Get the value of Issue
     *
     * @return the value of Issue
     */
    public String getIssue() {
        return Issue;
    }

    /**
     * Set the value of Issue
     *
     * @param Issue new value of Issue
     */
    public void setIssue(String Issue) {
        this.Issue = Issue;
    }

    private String Status;

    /**
     * Get the value of Status
     *
     * @return the value of Status
     */
    public String getStatus() {
        return Status;
    }

    /**
     * Set the value of Status
     *
     * @param Status new value of Status
     */
    public void setStatus(String Status) {
        this.Status = Status;
    }

    private String Notes;

    /**
     * Get the value of Notes
     *
     * @return the value of Notes
     */
    public String getNotes() {
        return Notes;
    }

    /**
     * Set the value of Notes
     *
     * @param Notes new value of Notes
     */
    public void setNotes(String Notes) {
        this.Notes = Notes;
    }

}
