/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.webdriver;

/**
 *
 * @author FOXCONN
 */
public class WebDriverException extends Exception {
    private Error value;

    public Error getValue() {
        return value;
    }

    public void setValue(Error value) {
        this.value = value;
    }
}
