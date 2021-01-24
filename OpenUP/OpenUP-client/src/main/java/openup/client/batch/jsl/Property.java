/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.batch.jsl;

import java.lang.annotation.Repeatable;

/**
 *
 * @author FOXCONN
 */
@Repeatable(Properties.class)
public @interface Property {
    String name();
    String value();
}
