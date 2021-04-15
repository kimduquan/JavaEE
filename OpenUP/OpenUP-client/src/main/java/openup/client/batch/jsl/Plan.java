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
public @interface Plan {
    int partitions() default 1;
    int threads() default 1;
    Property[] properties() default {};
}
