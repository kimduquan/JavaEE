/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.capabilities;

import java.util.Optional;

/**
 *
 * @author FOXCONN
 */
public class ClientCapabilities {
    public class FileOperation {
        private Optional<Boolean> dynamicRegistration;
        private Optional<Boolean> didCreate;
        private Optional<Boolean> willCreate;
        private Optional<Boolean> didRename;
        private Optional<Boolean> willRename;
        private Optional<Boolean> didDelete;
        private Optional<Boolean> willDelete;

        public Optional<Boolean> getDynamicRegistration() {
            return dynamicRegistration;
        }

        public void setDynamicRegistration(Optional<Boolean> dynamicRegistration) {
            this.dynamicRegistration = dynamicRegistration;
        }

        public Optional<Boolean> getDidCreate() {
            return didCreate;
        }

        public void setDidCreate(Optional<Boolean> didCreate) {
            this.didCreate = didCreate;
        }

        public Optional<Boolean> getWillCreate() {
            return willCreate;
        }

        public void setWillCreate(Optional<Boolean> willCreate) {
            this.willCreate = willCreate;
        }

        public Optional<Boolean> getDidRename() {
            return didRename;
        }

        public void setDidRename(Optional<Boolean> didRename) {
            this.didRename = didRename;
        }

        public Optional<Boolean> getWillRename() {
            return willRename;
        }

        public void setWillRename(Optional<Boolean> willRename) {
            this.willRename = willRename;
        }

        public Optional<Boolean> getDidDelete() {
            return didDelete;
        }

        public void setDidDelete(Optional<Boolean> didDelete) {
            this.didDelete = didDelete;
        }

        public Optional<Boolean> getWillDelete() {
            return willDelete;
        }

        public void setWillDelete(Optional<Boolean> willDelete) {
            this.willDelete = willDelete;
        }
    }
    
    public class Window {
        private Optional<Boolean> workDoneProgress;
        private Optional<ShowMessageRequestClientCapabilities> showMessage;
        private Optional<ShowDocumentClientCapabilities> showDocument;

        public Optional<Boolean> getWorkDoneProgress() {
            return workDoneProgress;
        }

        public void setWorkDoneProgress(Optional<Boolean> workDoneProgress) {
            this.workDoneProgress = workDoneProgress;
        }

        public Optional<ShowMessageRequestClientCapabilities> getShowMessage() {
            return showMessage;
        }

        public void setShowMessage(Optional<ShowMessageRequestClientCapabilities> showMessage) {
            this.showMessage = showMessage;
        }

        public Optional<ShowDocumentClientCapabilities> getShowDocument() {
            return showDocument;
        }

        public void setShowDocument(Optional<ShowDocumentClientCapabilities> showDocument) {
            this.showDocument = showDocument;
        }
    }
    
    public class General {
        private Optional<RegularExpressionsClientCapabilities> regularExpressions;
        private Optional<MarkdownClientCapabilities> markdown;

        public Optional<RegularExpressionsClientCapabilities> getRegularExpressions() {
            return regularExpressions;
        }

        public void setRegularExpressions(Optional<RegularExpressionsClientCapabilities> regularExpressions) {
            this.regularExpressions = regularExpressions;
        }

        public Optional<MarkdownClientCapabilities> getMarkdown() {
            return markdown;
        }

        public void setMarkdown(Optional<MarkdownClientCapabilities> markdown) {
            this.markdown = markdown;
        }
    }
    
    public class Workspace{

        public Optional<FileOperation> getFileOperations() {
            return fileOperations;
        }

        public void setFileOperations(Optional<FileOperation> fileOperations) {
            this.fileOperations = fileOperations;
        }

        public Optional<TextDocumentClientCapabilities> getTextDocument() {
            return textDocument;
        }

        public void setTextDocument(Optional<TextDocumentClientCapabilities> textDocument) {
            this.textDocument = textDocument;
        }

        public Optional<Window> getWindow() {
            return window;
        }

        public void setWindow(Optional<Window> window) {
            this.window = window;
        }

        public Optional<General> getGeneral() {
            return general;
        }

        public void setGeneral(Optional<General> general) {
            this.general = general;
        }

        public Optional getExperimental() {
            return experimental;
        }

        public void setExperimental(Optional experimental) {
            this.experimental = experimental;
        }
        private Optional<Boolean> applyEdit;
        private Optional<WorkspaceEditClientCapabilities> workspaceEdit;
        private Optional<DidChangeConfigurationClientCapabilities> didChangeConfiguration;
        private Optional<DidChangeWatchedFilesClientCapabilities> didChangeWatchedFiles;
        private Optional<WorkspaceSymbolClientCapabilities> symbol;
        private Optional<ExecuteCommandClientCapabilities> executeCommand;
        private Optional<Boolean> workspaceFolders;
        private Optional<Boolean> configuration;
        private Optional<SemanticTokensWorkspaceClientCapabilities> semanticTokens;
        private Optional<CodeLensWorkspaceClientCapabilities> codeLens;
        Optional<FileOperation> fileOperations;
        Optional<TextDocumentClientCapabilities> textDocument;
        Optional<Window> window;
        Optional<General> general;
        Optional experimental;

        public Optional<Boolean> getApplyEdit() {
            return applyEdit;
        }

        public void setApplyEdit(Optional<Boolean> applyEdit) {
            this.applyEdit = applyEdit;
        }

        public Optional<WorkspaceEditClientCapabilities> getWorkspaceEdit() {
            return workspaceEdit;
        }

        public void setWorkspaceEdit(Optional<WorkspaceEditClientCapabilities> workspaceEdit) {
            this.workspaceEdit = workspaceEdit;
        }

        public Optional<DidChangeConfigurationClientCapabilities> getDidChangeConfiguration() {
            return didChangeConfiguration;
        }

        public void setDidChangeConfiguration(Optional<DidChangeConfigurationClientCapabilities> didChangeConfiguration) {
            this.didChangeConfiguration = didChangeConfiguration;
        }

        public Optional<DidChangeWatchedFilesClientCapabilities> getDidChangeWatchedFiles() {
            return didChangeWatchedFiles;
        }

        public void setDidChangeWatchedFiles(Optional<DidChangeWatchedFilesClientCapabilities> didChangeWatchedFiles) {
            this.didChangeWatchedFiles = didChangeWatchedFiles;
        }

        public Optional<WorkspaceSymbolClientCapabilities> getSymbol() {
            return symbol;
        }

        public void setSymbol(Optional<WorkspaceSymbolClientCapabilities> symbol) {
            this.symbol = symbol;
        }

        public Optional<ExecuteCommandClientCapabilities> getExecuteCommand() {
            return executeCommand;
        }

        public void setExecuteCommand(Optional<ExecuteCommandClientCapabilities> executeCommand) {
            this.executeCommand = executeCommand;
        }

        public Optional<Boolean> getWorkspaceFolders() {
            return workspaceFolders;
        }

        public void setWorkspaceFolders(Optional<Boolean> workspaceFolders) {
            this.workspaceFolders = workspaceFolders;
        }

        public Optional<Boolean> getConfiguration() {
            return configuration;
        }

        public void setConfiguration(Optional<Boolean> configuration) {
            this.configuration = configuration;
        }

        public Optional<SemanticTokensWorkspaceClientCapabilities> getSemanticTokens() {
            return semanticTokens;
        }

        public void setSemanticTokens(Optional<SemanticTokensWorkspaceClientCapabilities> semanticTokens) {
            this.semanticTokens = semanticTokens;
        }

        public Optional<CodeLensWorkspaceClientCapabilities> getCodeLens() {
            return codeLens;
        }

        public void setCodeLens(Optional<CodeLensWorkspaceClientCapabilities> codeLens) {
            this.codeLens = codeLens;
        }
    }
    
    Optional<Workspace> workspace;
}
