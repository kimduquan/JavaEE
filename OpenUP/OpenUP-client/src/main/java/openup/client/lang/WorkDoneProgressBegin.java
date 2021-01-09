/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang;

import java.util.Optional;

/**
 *
 * @author FOXCONN
 */
public class WorkDoneProgressBegin {
    private final String kind = "begin";
    private String title;
    private Optional<Boolean> cancellable;
    private Optional<String> message;
    private Optional<Integer> percentage;

    public String getKind() {
        return kind;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Optional<Boolean> getCancellable() {
        return cancellable;
    }

    public void setCancellable(Optional<Boolean> cancellable) {
        this.cancellable = cancellable;
    }

    public Optional<String> getMessage() {
        return message;
    }

    public void setMessage(Optional<String> message) {
        this.message = message;
    }

    public Optional<Integer> getPercentage() {
        return percentage;
    }

    public void setPercentage(Optional<Integer> percentage) {
        this.percentage = percentage;
    }
}
