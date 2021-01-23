/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.server;

import openup.client.lang.ApplyWorkspaceEditResponse;
import openup.client.lang.WorkspaceEdit;
import openup.client.lang.WorkspaceFolder;
import openup.client.lang.information.SymbolInformation;
import openup.client.lang.params.ApplyWorkspaceEditParams;
import openup.client.lang.params.ConfigurationParams;
import openup.client.lang.params.CreateFilesParams;
import openup.client.lang.params.DeleteFilesParams;
import openup.client.lang.params.DidChangeConfigurationParams;
import openup.client.lang.params.DidChangeWatchedFilesParams;
import openup.client.lang.params.DidChangeWorkspaceFoldersParams;
import openup.client.lang.params.ExecuteCommandParams;
import openup.client.lang.params.RenameFilesParams;
import openup.client.lang.params.WorkspaceSymbolParams;

/**
 *
 * @author FOXCONN
 */
public interface Workspace extends openup.client.rpc.Server {
    WorkspaceFolder[] workspaceFolders() throws Error;
    void didChangeWorkspaceFolders(DidChangeWorkspaceFoldersParams params);
    void didChangeConfiguration(DidChangeConfigurationParams params);
    Object[] configuration(ConfigurationParams params) throws Error;
    void didChangeWatchedFiles(DidChangeWatchedFilesParams params);
    SymbolInformation[] symbol(WorkspaceSymbolParams params) throws Error;
    Object executeCommand(ExecuteCommandParams params) throws Error;
    ApplyWorkspaceEditResponse applyEdit(ApplyWorkspaceEditParams params) throws Error;
    WorkspaceEdit willCreateFiles(CreateFilesParams params) throws Error;
    void didCreateFiles(CreateFilesParams params);
    WorkspaceEdit willRenameFiles(RenameFilesParams params) throws Error;
    void didRenameFiles(RenameFilesParams params);
    WorkspaceEdit willDeleteFiles(DeleteFilesParams params) throws Error;
    void didDeleteFiles(DeleteFilesParams params);
}
