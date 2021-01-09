/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import java.util.Optional;
import openup.client.lang.MessageActionItem;
import openup.client.lang.MessageType;

/**
 *
 * @author FOXCONN
 */
public class ShowMessageRequestParams {
    private MessageType type;
    private String message;
    private Optional<MessageActionItem[]> actions;

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Optional<MessageActionItem[]> getActions() {
        return actions;
    }

    public void setActions(Optional<MessageActionItem[]> actions) {
        this.actions = actions;
    }
}
