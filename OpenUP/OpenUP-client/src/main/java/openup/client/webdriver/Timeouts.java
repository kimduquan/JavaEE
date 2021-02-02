/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.webdriver;

import java.util.Optional;

/**
 *
 * @author FOXCONN
 */
public class Timeouts {
    private Optional<Integer> script;
    private Optional<Integer> pageLoad;
    private Optional<Integer> implicit;

    public Optional<Integer> getScript() {
        return script;
    }

    public void setScript(Optional<Integer> script) {
        this.script = script;
    }

    public Optional<Integer> getPageLoad() {
        return pageLoad;
    }

    public void setPageLoad(Optional<Integer> pageLoad) {
        this.pageLoad = pageLoad;
    }

    public Optional<Integer> getImplicit() {
        return implicit;
    }

    public void setImplicit(Optional<Integer> implicit) {
        this.implicit = implicit;
    }
}
