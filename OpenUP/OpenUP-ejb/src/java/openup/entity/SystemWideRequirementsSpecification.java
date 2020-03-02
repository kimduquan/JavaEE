/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.entity;

import openup.entity.embeddable.URPS;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
public class SystemWideRequirementsSpecification implements Serializable {

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
        if (!(object instanceof SystemWideRequirementsSpecification)) {
            return false;
        }
        SystemWideRequirementsSpecification other = (SystemWideRequirementsSpecification) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "openup.entity.SystemWideRequirementsSpecification[ id=" + id + " ]";
    }
    
    private String ProjectName;

    /**
     * Get the value of ProjectName
     *
     * @return the value of ProjectName
     */
    public String getProjectName() {
        return ProjectName;
    }

    /**
     * Set the value of ProjectName
     *
     * @param ProjectName new value of ProjectName
     */
    public void setProjectName(String ProjectName) {
        this.ProjectName = ProjectName;
    }

    private Date Date;

    /**
     * Get the value of Date
     *
     * @return the value of Date
     */
    public Date getDate() {
        return Date;
    }

    /**
     * Set the value of Date
     *
     * @param Date new value of Date
     */
    public void setDate(Date Date) {
        this.Date = Date;
    }

    private String Introduction;

    /**
     * Get the value of Introduction
     *
     * @return the value of Introduction
     */
    public String getIntroduction() {
        return Introduction;
    }

    /**
     * Set the value of Introduction
     *
     * @param Introduction new value of Introduction
     */
    public void setIntroduction(String Introduction) {
        this.Introduction = Introduction;
    }

    private String SystemWideFunctionalRequirements;

    /**
     * Get the value of SystemWideFunctionalRequirements
     *
     * @return the value of SystemWideFunctionalRequirements
     */
    public String getSystemWideFunctionalRequirements() {
        return SystemWideFunctionalRequirements;
    }

    /**
     * Set the value of SystemWideFunctionalRequirements
     *
     * @param SystemWideFunctionalRequirements new value of
     * SystemWideFunctionalRequirements
     */
    public void setSystemWideFunctionalRequirements(String SystemWideFunctionalRequirements) {
        this.SystemWideFunctionalRequirements = SystemWideFunctionalRequirements;
    }

    @Embedded
    private URPS SystemQualities;

    /**
     * Get the value of SystemQualities
     *
     * @return the value of SystemQualities
     */
    public URPS getSystemQualities() {
        return SystemQualities;
    }

    /**
     * Set the value of SystemQualities
     *
     * @param SystemQualities new value of SystemQualities
     */
    public void setSystemQualities(URPS SystemQualities) {
        this.SystemQualities = SystemQualities;
    }

    private String LookAndFeel;

    /**
     * Get the value of LookAndFeel
     *
     * @return the value of LookAndFeel
     */
    public String getLookAndFeel() {
        return LookAndFeel;
    }

    /**
     * Set the value of LookAndFeel
     *
     * @param LookAndFeel new value of LookAndFeel
     */
    public void setLookAndFeel(String LookAndFeel) {
        this.LookAndFeel = LookAndFeel;
    }

    private String LayoutAndNavigationRequirements;

    /**
     * Get the value of LayoutAndNavigationRequirements
     *
     * @return the value of LayoutAndNavigationRequirements
     */
    public String getLayoutAndNavigationRequirements() {
        return LayoutAndNavigationRequirements;
    }

    /**
     * Set the value of LayoutAndNavigationRequirements
     *
     * @param LayoutAndNavigationRequirements new value of
     * LayoutAndNavigationRequirements
     */
    public void setLayoutAndNavigationRequirements(String LayoutAndNavigationRequirements) {
        this.LayoutAndNavigationRequirements = LayoutAndNavigationRequirements;
    }

    private String Consistency;

    /**
     * Get the value of Consistency
     *
     * @return the value of Consistency
     */
    public String getConsistency() {
        return Consistency;
    }

    /**
     * Set the value of Consistency
     *
     * @param Consistency new value of Consistency
     */
    public void setConsistency(String Consistency) {
        this.Consistency = Consistency;
    }

    private String UserPersonalizationCustomizationRequirements;

    /**
     * Get the value of UserPersonalizationCustomizationRequirements
     *
     * @return the value of UserPersonalizationCustomizationRequirements
     */
    public String getUserPersonalizationCustomizationRequirements() {
        return UserPersonalizationCustomizationRequirements;
    }

    /**
     * Set the value of UserPersonalizationCustomizationRequirements
     *
     * @param UserPersonalizationCustomizationRequirements new value of
     * UserPersonalizationCustomizationRequirements
     */
    public void setUserPersonalizationCustomizationRequirements(String UserPersonalizationCustomizationRequirements) {
        this.UserPersonalizationCustomizationRequirements = UserPersonalizationCustomizationRequirements;
    }

    private String SoftwareInterfaces;

    /**
     * Get the value of SoftwareInterfaces
     *
     * @return the value of SoftwareInterfaces
     */
    public String getSoftwareInterfaces() {
        return SoftwareInterfaces;
    }

    /**
     * Set the value of SoftwareInterfaces
     *
     * @param SoftwareInterfaces new value of SoftwareInterfaces
     */
    public void setSoftwareInterfaces(String SoftwareInterfaces) {
        this.SoftwareInterfaces = SoftwareInterfaces;
    }

    private String HardwareInterfaces;

    /**
     * Get the value of HardwareInterfaces
     *
     * @return the value of HardwareInterfaces
     */
    public String getHardwareInterfaces() {
        return HardwareInterfaces;
    }

    /**
     * Set the value of HardwareInterfaces
     *
     * @param HardwareInterfaces new value of HardwareInterfaces
     */
    public void setHardwareInterfaces(String HardwareInterfaces) {
        this.HardwareInterfaces = HardwareInterfaces;
    }

    private String CommunicationsInterfaces;

    /**
     * Get the value of CommunicationsInterfaces
     *
     * @return the value of CommunicationsInterfaces
     */
    public String getCommunicationsInterfaces() {
        return CommunicationsInterfaces;
    }

    /**
     * Set the value of CommunicationsInterfaces
     *
     * @param CommunicationsInterfaces new value of CommunicationsInterfaces
     */
    public void setCommunicationsInterfaces(String CommunicationsInterfaces) {
        this.CommunicationsInterfaces = CommunicationsInterfaces;
    }

    private List<String> BusinessRules;

    /**
     * Get the value of BusinessRules
     *
     * @return the value of BusinessRules
     */
    public List<String> getBusinessRules() {
        return BusinessRules;
    }

    /**
     * Set the value of BusinessRules
     *
     * @param BusinessRules new value of BusinessRules
     */
    public void setBusinessRules(List<String> BusinessRules) {
        this.BusinessRules = BusinessRules;
    }

    private String SystemConstraints;

    /**
     * Get the value of SystemConstraints
     *
     * @return the value of SystemConstraints
     */
    public String getSystemConstraints() {
        return SystemConstraints;
    }

    /**
     * Set the value of SystemConstraints
     *
     * @param SystemConstraints new value of SystemConstraints
     */
    public void setSystemConstraints(String SystemConstraints) {
        this.SystemConstraints = SystemConstraints;
    }
    private String LicensingRequirements;

    /**
     * Get the value of LicensingRequirements
     *
     * @return the value of LicensingRequirements
     */
    public String getLicensingRequirements() {
        return LicensingRequirements;
    }

    /**
     * Set the value of LicensingRequirements
     *
     * @param LicensingRequirements new value of LicensingRequirements
     */
    public void setLicensingRequirements(String LicensingRequirements) {
        this.LicensingRequirements = LicensingRequirements;
    }

    private String LegalCopyrightAndOtherNotices;

    /**
     * Get the value of LegalCopyrightAndOtherNotices
     *
     * @return the value of LegalCopyrightAndOtherNotices
     */
    public String getLegalCopyrightAndOtherNotices() {
        return LegalCopyrightAndOtherNotices;
    }

    /**
     * Set the value of LegalCopyrightAndOtherNotices
     *
     * @param LegalCopyrightAndOtherNotices new value of
     * LegalCopyrightAndOtherNotices
     */
    public void setLegalCopyrightAndOtherNotices(String LegalCopyrightAndOtherNotices) {
        this.LegalCopyrightAndOtherNotices = LegalCopyrightAndOtherNotices;
    }

    private String ApplicableStandards;

    /**
     * Get the value of ApplicableStandards
     *
     * @return the value of ApplicableStandards
     */
    public String getApplicableStandards() {
        return ApplicableStandards;
    }

    /**
     * Set the value of ApplicableStandards
     *
     * @param ApplicableStandards new value of ApplicableStandards
     */
    public void setApplicableStandards(String ApplicableStandards) {
        this.ApplicableStandards = ApplicableStandards;
    }

    private String SystemDocumentation;

    /**
     * Get the value of SystemDocumentation
     *
     * @return the value of SystemDocumentation
     */
    public String getSystemDocumentation() {
        return SystemDocumentation;
    }

    /**
     * Set the value of SystemDocumentation
     *
     * @param SystemDocumentation new value of SystemDocumentation
     */
    public void setSystemDocumentation(String SystemDocumentation) {
        this.SystemDocumentation = SystemDocumentation;
    }

}
