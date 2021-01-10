/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.rpc;

import javax.ws.rs.core.MediaType;

/**
 *
 * @author FOXCONN
 */
public class Message {
    private String jsonrpc;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }
    
    public static final String APPLICATION_JSON_RPC = "application/vscode-jsonrpc";
    public static final MediaType APPLICATION_JSON_RPC_TYPE = MediaType.valueOf(APPLICATION_JSON_RPC).withCharset("utf-8");
}
