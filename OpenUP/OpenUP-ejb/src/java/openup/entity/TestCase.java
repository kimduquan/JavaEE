/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author FOXCONN
 */
@Entity
public class TestCase implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TestCase)) {
            return false;
        }
        TestCase other = (TestCase) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "openup.entity.TestCase[ id=" + id + " ]";
    }
    
    private String TestCaseID;

    /**
     * Get the value of TestCaseID
     *
     * @return the value of TestCaseID
     */
    public String getTestCaseID() {
        return TestCaseID;
    }

    /**
     * Set the value of TestCaseID
     *
     * @param TestCaseID new value of TestCaseID
     */
    public void setTestCaseID(String TestCaseID) {
        this.TestCaseID = TestCaseID;
    }

    private String TestCaseName;

    /**
     * Get the value of TestCaseName
     *
     * @return the value of TestCaseName
     */
    public String getTestCaseName() {
        return TestCaseName;
    }

    /**
     * Set the value of TestCaseName
     *
     * @param TestCaseName new value of TestCaseName
     */
    public void setTestCaseName(String TestCaseName) {
        this.TestCaseName = TestCaseName;
    }

    private String Description;

    /**
     * Get the value of Description
     *
     * @return the value of Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * Set the value of Description
     *
     * @param Description new value of Description
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    private String PreConditions;

    /**
     * Get the value of PreConditions
     *
     * @return the value of PreConditions
     */
    public String getPreConditions() {
        return PreConditions;
    }

    /**
     * Set the value of PreConditions
     *
     * @param PreConditions new value of PreConditions
     */
    public void setPreConditions(String PreConditions) {
        this.PreConditions = PreConditions;
    }

    private String PostConditions;

    /**
     * Get the value of PostConditions
     *
     * @return the value of PostConditions
     */
    public String getPostConditions() {
        return PostConditions;
    }

    /**
     * Set the value of PostConditions
     *
     * @param PostConditions new value of PostConditions
     */
    public void setPostConditions(String PostConditions) {
        this.PostConditions = PostConditions;
    }

    private String DataRequired;

    /**
     * Get the value of DataRequired
     *
     * @return the value of DataRequired
     */
    public String getDataRequired() {
        return DataRequired;
    }

    /**
     * Set the value of DataRequired
     *
     * @param DataRequired new value of DataRequired
     */
    public void setDataRequired(String DataRequired) {
        this.DataRequired = DataRequired;
    }

}
