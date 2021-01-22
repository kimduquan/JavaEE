/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.batch.api;

/**
 *
 * @author FOXCONN
 */
public class PurgeResult {
    private long instanceId;
    private PurgeStatus purgeStatus;
    private String message;
    private String redirectUrl;

    public long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(long instanceId) {
        this.instanceId = instanceId;
    }

    public PurgeStatus getPurgeStatus() {
        return purgeStatus;
    }

    public void setPurgeStatus(PurgeStatus purgeStatus) {
        this.purgeStatus = purgeStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
