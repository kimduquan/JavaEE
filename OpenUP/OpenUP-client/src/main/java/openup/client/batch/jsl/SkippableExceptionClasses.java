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
public @interface SkippableExceptionClasses {
    Include[] include() default {};
    Exclude[] exclude() default {};
}
