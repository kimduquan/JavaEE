/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author FOXCONN
 */
@Entity
public class TestCases implements Serializable {

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
        if (!(object instanceof TestCases)) {
            return false;
        }
        TestCases other = (TestCases) object;
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

    private List<TestCase> TestCases;

    /**
     * Get the value of TestCases
     *
     * @return the value of TestCases
     */
    public List<TestCase> getTestCases() {
        return TestCases;
    }

    /**
     * Set the value of TestCases
     *
     * @param TestCases new value of TestCases
     */
    public void setTestCases(List<TestCase> TestCases) {
        this.TestCases = TestCases;
    }

}
