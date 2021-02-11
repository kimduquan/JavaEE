/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.schema;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author FOXCONN
 */
@Type(OpenUP.DeliveryProcess)
@Schema(name = OpenUP.DeliveryProcess, title = "Delivery Process")
@Entity(name = OpenUP.DeliveryProcess)
@Table(schema = OpenUP.Schema, name = "OPENUP_DELIVERY_PROCESS")
public class DeliveryProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "DELIVERY_PROCESS")
    private epf.schema.delivery_processes.DeliveryProcess deliveryProcess;
    
    @Column(name = "NAME", unique = true, nullable = false)
    private String name;
    
    @Column(name = "SUMMARY")
    private String summary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public epf.schema.delivery_processes.DeliveryProcess getDeliveryProcess() {
        return deliveryProcess;
    }

    public void setDeliveryProcess(epf.schema.delivery_processes.DeliveryProcess deliveryProcess) {
        this.deliveryProcess = deliveryProcess;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
