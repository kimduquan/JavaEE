/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.work_products;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author FOXCONN
 */
@Embeddable
public class Template implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
