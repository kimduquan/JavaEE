/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.capabilities;

import java.util.Optional;
import openup.client.lang.MessageActionItem;

/**
 *
 * @author FOXCONN
 */
public class ShowMessageRequestClientCapabilities {
    
    private Optional<MessageActionItem> messageActionItem;

    public Optional<MessageActionItem> getMessageActionItem() {
        return messageActionItem;
    }

    public void setMessageActionItem(Optional<MessageActionItem> messageActionItem) {
        this.messageActionItem = messageActionItem;
    }
}
