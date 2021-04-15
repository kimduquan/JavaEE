/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.batch.jsl;

/**
 *
 * @author FOXCONN
 */
public @interface Job {
    String id();
    boolean restartable() default true;
    Property[] properties() default {};
}
