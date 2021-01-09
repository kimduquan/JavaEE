/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang;

import openup.client.lang.capabilities.ServerCapabilities;
import java.util.Optional;

/**
 *
 * @author FOXCONN
 */
public class InitializeResult {

    public class ServerInfo {
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
    
    private ServerCapabilities capabilities;
    private Optional<ServerInfo> serverInfo;
    
    public ServerCapabilities getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(ServerCapabilities capabilities) {
        this.capabilities = capabilities;
    }

    public Optional<ServerInfo> getServerInfo() {
        return serverInfo;
    }

    public void setServerInfo(Optional<ServerInfo> serverInfo) {
        this.serverInfo = serverInfo;
    }
}
