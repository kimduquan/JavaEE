/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.ejb.guidance.templates;

import openup.embeddable.Test_Case;
import java.io.Serializable;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
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
    
    private String Project;

    /**
     * Get the value of Project
     *
     * @return the value of Project
     */
    public String getProject() {
        return Project;
    }

    /**
     * Set the value of Project
     *
     * @param Project new value of Project
     */
    public void setProject(String Project) {
        this.Project = Project;
    }

    private String UseCaseName;

    /**
     * Get the value of UseCaseName
     *
     * @return the value of UseCaseName
     */
    public String getUseCaseName() {
        return UseCaseName;
    }

    /**
     * Set the value of UseCaseName
     *
     * @param UseCaseName new value of UseCaseName
     */
    public void setUseCaseName(String UseCaseName) {
        this.UseCaseName = UseCaseName;
    }

    @ElementCollection
    private List<Test_Case> TestCases;

    /**
     * Get the value of TestCase
     *
     * @return the value of TestCase
     */
    public List<Test_Case> getTestCases() {
        return TestCases;
    }

    /**
     * Set the value of TestCase
     *
     * @param TestCases new value of TestCase
     */
    public void setTestCases(List<Test_Case> TestCases) {
        this.TestCases = TestCases;
    }

}
