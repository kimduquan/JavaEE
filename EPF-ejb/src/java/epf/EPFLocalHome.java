/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 *
 * @author FOXCONN
 */
public interface EPFLocalHome extends EJBLocalHome {
    
    epf.EPFLocal findByPrimaryKey(java.lang.Long key) throws FinderException;
    
    epf.EPFLocal create(java.lang.Long key) throws CreateException;
    
}
