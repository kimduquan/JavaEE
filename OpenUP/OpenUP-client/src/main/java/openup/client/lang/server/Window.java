/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.server;

import openup.client.lang.ShowDocumentResult;
import openup.client.lang.item.MessageActionItem;
import openup.client.lang.params.LogMessageParams;
import openup.client.lang.params.ShowDocumentParams;
import openup.client.lang.params.ShowMessageParams;
import openup.client.lang.params.ShowMessageRequestParams;
import openup.client.lang.params.WorkDoneProgressCancelParams;
import openup.client.lang.params.WorkDoneProgressCreateParams;

/**
 *
 * @author FOXCONN
 */
public interface Window extends openup.client.rpc.Server {
    void showMessage(ShowMessageParams params);
    MessageActionItem showMessageRequest(ShowMessageRequestParams params) throws Error;
    ShowDocumentResult showDocument(ShowDocumentParams params) throws Error;
    void logMessage(LogMessageParams params);
    Object create(WorkDoneProgressCreateParams params) throws Error;
    void cancel(WorkDoneProgressCancelParams params);
}
