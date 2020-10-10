/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.config;

/**
 *
 * @author FOXCONN
 */
public interface Config {
    String MP_JWT_PRIVATE_KEY = "mp.jwt.issue.privatekey";
    String JWT_EXPIRE_DURATION = "openup.auth.jwt.exp.duration";
    String JWT_EXPIRE_TIMEUNIT = "openup.auth.jwt.exp.timeunit";
}
