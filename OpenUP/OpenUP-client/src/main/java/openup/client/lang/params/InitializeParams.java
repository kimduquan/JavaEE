/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import java.util.Optional;
import openup.client.lang.capabilities.ClientCapabilities;
import openup.client.lang.DocumentUri;
import openup.client.lang.TraceValue;
import openup.client.lang.WorkspaceFolder;

/**
 *
 * @author FOXCONN
 */
public class InitializeParams extends WorkDoneProgressParams {
    public class ClientInfo{
        private String name;
        private Optional<String> version;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Optional<String> getVersion() {
            return version;
        }

        public void setVersion(Optional<String> version) {
            this.version = version;
        }
    }
    
    Integer processId;
    Optional<ClientInfo> clientInfo;
    Optional<String> locale;
    Optional<String> rootPath;
    DocumentUri rootUri;
    Optional initializationOptions;
    ClientCapabilities capabilities;
    Optional<TraceValue> trace;
    Optional<WorkspaceFolder[]> workspaceFolders;
}
