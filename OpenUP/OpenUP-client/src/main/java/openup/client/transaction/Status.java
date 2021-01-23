/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.transaction;

/**
 *
 * @author FOXCONN
 */
public enum Status {
    STATUS_ACTIVE,
    STATUS_MARKED_ROLLBACK,
    STATUS_PREPARED,
    STATUS_COMMITTED,
    STATUS_ROLLEDBACK,
    STATUS_UNKNOWN,
    STATUS_NO_TRANSACTION,
    STATUS_PREPARING,
    STATUS_COMMITTING,
    STATUS_ROLLING_BACK
}
