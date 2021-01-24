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
public @interface Chunk {
    CheckpointPolicy checkpoint_policy() default CheckpointPolicy.Item;
    int item_count() default 10;
    int time_limit() default 0;
    int skip_limit() default 0;
    int retry_limit() default 0;
}
