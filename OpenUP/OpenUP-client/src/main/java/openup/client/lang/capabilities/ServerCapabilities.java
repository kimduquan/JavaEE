/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.capabilities;

import openup.client.lang.options.MonikerOptions;
import openup.client.lang.options.DocumentRangeFormattingOptions;
import openup.client.lang.options.SelectionRangeOptions;
import openup.client.lang.options.TypeDefinitionOptions;
import openup.client.lang.options.FoldingRangeOptions;
import openup.client.lang.options.SemanticTokensOptions;
import openup.client.lang.options.ExecuteCommandOptions;
import openup.client.lang.options.DocumentLinkOptions;
import openup.client.lang.options.DeclarationOptions;
import openup.client.lang.options.CompletionOptions;
import openup.client.lang.options.DocumentColorOptions;
import openup.client.lang.options.TextDocumentSyncOptions;
import openup.client.lang.options.DocumentHighlightOptions;
import openup.client.lang.options.CallHierarchyOptions;
import openup.client.lang.options.HoverOptions;
import openup.client.lang.options.LinkedEditingRangeOptions;
import openup.client.lang.options.DocumentFormattingOptions;
import openup.client.lang.options.ReferenceOptions;
import openup.client.lang.options.RenameOptions;
import openup.client.lang.options.FileOperationRegistrationOptions;
import openup.client.lang.options.CodeLensOptions;
import openup.client.lang.options.CodeActionOptions;
import openup.client.lang.options.DocumentSymbolOptions;
import openup.client.lang.options.ImplementationOptions;
import openup.client.lang.options.DocumentOnTypeFormattingOptions;
import openup.client.lang.options.WorkspaceSymbolOptions;
import java.util.Optional;
import openup.client.lang.options.DefinitionOptions;

/**
 *
 * @author FOXCONN
 */
public class ServerCapabilities {

    public Optional<TextDocumentSyncOptions> getTextDocumentSync() {
        return textDocumentSync;
    }

    public void setTextDocumentSync(Optional<TextDocumentSyncOptions> textDocumentSync) {
        this.textDocumentSync = textDocumentSync;
    }

    public Optional<CompletionOptions> getCompletionProvider() {
        return completionProvider;
    }

    public void setCompletionProvider(Optional<CompletionOptions> completionProvider) {
        this.completionProvider = completionProvider;
    }

    public Optional<HoverOptions> getHoverProvider() {
        return hoverProvider;
    }

    public void setHoverProvider(Optional<HoverOptions> hoverProvider) {
        this.hoverProvider = hoverProvider;
    }

    public Optional<DeclarationOptions> getDeclarationProvider() {
        return declarationProvider;
    }

    public void setDeclarationProvider(Optional<DeclarationOptions> declarationProvider) {
        this.declarationProvider = declarationProvider;
    }

    public Optional<DefinitionOptions> getDefinitionProvider() {
        return definitionProvider;
    }

    public void setDefinitionProvider(Optional<DefinitionOptions> definitionProvider) {
        this.definitionProvider = definitionProvider;
    }

    public Optional<TypeDefinitionOptions> getTypeDefinitionProvider() {
        return typeDefinitionProvider;
    }

    public void setTypeDefinitionProvider(Optional<TypeDefinitionOptions> typeDefinitionProvider) {
        this.typeDefinitionProvider = typeDefinitionProvider;
    }

    public Optional<ImplementationOptions> getImplementationProvider() {
        return implementationProvider;
    }

    public void setImplementationProvider(Optional<ImplementationOptions> implementationProvider) {
        this.implementationProvider = implementationProvider;
    }

    public Optional<ReferenceOptions> getReferencesProvider() {
        return referencesProvider;
    }

    public void setReferencesProvider(Optional<ReferenceOptions> referencesProvider) {
        this.referencesProvider = referencesProvider;
    }

    public Optional<DocumentHighlightOptions> getDocumentHighlightProvider() {
        return documentHighlightProvider;
    }

    public void setDocumentHighlightProvider(Optional<DocumentHighlightOptions> documentHighlightProvider) {
        this.documentHighlightProvider = documentHighlightProvider;
    }

    public Optional<DocumentSymbolOptions> getDocumentSymbolProvider() {
        return documentSymbolProvider;
    }

    public void setDocumentSymbolProvider(Optional<DocumentSymbolOptions> documentSymbolProvider) {
        this.documentSymbolProvider = documentSymbolProvider;
    }

    public Optional<CodeActionOptions> getCodeActionProvider() {
        return codeActionProvider;
    }

    public void setCodeActionProvider(Optional<CodeActionOptions> codeActionProvider) {
        this.codeActionProvider = codeActionProvider;
    }

    public Optional<CodeLensOptions> getCodeLensProvider() {
        return codeLensProvider;
    }

    public void setCodeLensProvider(Optional<CodeLensOptions> codeLensProvider) {
        this.codeLensProvider = codeLensProvider;
    }

    public Optional<DocumentLinkOptions> getDocumentLinkProvider() {
        return documentLinkProvider;
    }

    public void setDocumentLinkProvider(Optional<DocumentLinkOptions> documentLinkProvider) {
        this.documentLinkProvider = documentLinkProvider;
    }

    public Optional<DocumentColorOptions> getColorProvider() {
        return colorProvider;
    }

    public void setColorProvider(Optional<DocumentColorOptions> colorProvider) {
        this.colorProvider = colorProvider;
    }

    public Optional<DocumentFormattingOptions> getDocumentFormattingProvider() {
        return documentFormattingProvider;
    }

    public void setDocumentFormattingProvider(Optional<DocumentFormattingOptions> documentFormattingProvider) {
        this.documentFormattingProvider = documentFormattingProvider;
    }

    public Optional<DocumentRangeFormattingOptions> getDocumentRangeFormattingProvider() {
        return documentRangeFormattingProvider;
    }

    public void setDocumentRangeFormattingProvider(Optional<DocumentRangeFormattingOptions> documentRangeFormattingProvider) {
        this.documentRangeFormattingProvider = documentRangeFormattingProvider;
    }

    public Optional<DocumentOnTypeFormattingOptions> getDocumentOnTypeFormattingProvider() {
        return documentOnTypeFormattingProvider;
    }

    public void setDocumentOnTypeFormattingProvider(Optional<DocumentOnTypeFormattingOptions> documentOnTypeFormattingProvider) {
        this.documentOnTypeFormattingProvider = documentOnTypeFormattingProvider;
    }

    public Optional<RenameOptions> getRenameProvider() {
        return renameProvider;
    }

    public void setRenameProvider(Optional<RenameOptions> renameProvider) {
        this.renameProvider = renameProvider;
    }

    public Optional<FoldingRangeOptions> getFoldingRangeProvider() {
        return foldingRangeProvider;
    }

    public void setFoldingRangeProvider(Optional<FoldingRangeOptions> foldingRangeProvider) {
        this.foldingRangeProvider = foldingRangeProvider;
    }

    public Optional<ExecuteCommandOptions> getExecuteCommandProvider() {
        return executeCommandProvider;
    }

    public void setExecuteCommandProvider(Optional<ExecuteCommandOptions> executeCommandProvider) {
        this.executeCommandProvider = executeCommandProvider;
    }

    public Optional<SelectionRangeOptions> getSelectionRangeProvider() {
        return selectionRangeProvider;
    }

    public void setSelectionRangeProvider(Optional<SelectionRangeOptions> selectionRangeProvider) {
        this.selectionRangeProvider = selectionRangeProvider;
    }

    public Optional<LinkedEditingRangeOptions> getLinkedEditingRangeProvider() {
        return linkedEditingRangeProvider;
    }

    public void setLinkedEditingRangeProvider(Optional<LinkedEditingRangeOptions> linkedEditingRangeProvider) {
        this.linkedEditingRangeProvider = linkedEditingRangeProvider;
    }

    public Optional<CallHierarchyOptions> getCallHierarchyProvider() {
        return callHierarchyProvider;
    }

    public void setCallHierarchyProvider(Optional<CallHierarchyOptions> callHierarchyProvider) {
        this.callHierarchyProvider = callHierarchyProvider;
    }

    public Optional<SemanticTokensOptions> getSemanticTokensProvider() {
        return semanticTokensProvider;
    }

    public void setSemanticTokensProvider(Optional<SemanticTokensOptions> semanticTokensProvider) {
        this.semanticTokensProvider = semanticTokensProvider;
    }

    public Optional<MonikerOptions> getMonikerProvider() {
        return monikerProvider;
    }

    public void setMonikerProvider(Optional<MonikerOptions> monikerProvider) {
        this.monikerProvider = monikerProvider;
    }

    public Optional<WorkspaceSymbolOptions> getWorkspaceSymbolProvider() {
        return workspaceSymbolProvider;
    }

    public void setWorkspaceSymbolProvider(Optional<WorkspaceSymbolOptions> workspaceSymbolProvider) {
        this.workspaceSymbolProvider = workspaceSymbolProvider;
    }
    
    public class FileOperation {
        private Optional<FileOperationRegistrationOptions> didCreate;
        private Optional<FileOperationRegistrationOptions> willCreate;
        private Optional<FileOperationRegistrationOptions> didRename;
        private Optional<FileOperationRegistrationOptions> willRename;
        private Optional<FileOperationRegistrationOptions> didDelete;
        private Optional<FileOperationRegistrationOptions> willDelete;

        public Optional<FileOperationRegistrationOptions> getDidCreate() {
            return didCreate;
        }

        public void setDidCreate(Optional<FileOperationRegistrationOptions> didCreate) {
            this.didCreate = didCreate;
        }

        public Optional<FileOperationRegistrationOptions> getWillCreate() {
            return willCreate;
        }

        public void setWillCreate(Optional<FileOperationRegistrationOptions> willCreate) {
            this.willCreate = willCreate;
        }

        public Optional<FileOperationRegistrationOptions> getDidRename() {
            return didRename;
        }

        public void setDidRename(Optional<FileOperationRegistrationOptions> didRename) {
            this.didRename = didRename;
        }

        public Optional<FileOperationRegistrationOptions> getWillRename() {
            return willRename;
        }

        public void setWillRename(Optional<FileOperationRegistrationOptions> willRename) {
            this.willRename = willRename;
        }

        public Optional<FileOperationRegistrationOptions> getDidDelete() {
            return didDelete;
        }

        public void setDidDelete(Optional<FileOperationRegistrationOptions> didDelete) {
            this.didDelete = didDelete;
        }

        public Optional<FileOperationRegistrationOptions> getWillDelete() {
            return willDelete;
        }

        public void setWillDelete(Optional<FileOperationRegistrationOptions> willDelete) {
            this.willDelete = willDelete;
        }
    }
    
    public class Workspace {
       private Optional<WorkspaceFoldersServerCapabilities> workspaceFolders;
       private Optional<FileOperation> fileOperations;
       private Optional experimental;

        public Optional<WorkspaceFoldersServerCapabilities> getWorkspaceFolders() {
            return workspaceFolders;
        }

        public void setWorkspaceFolders(Optional<WorkspaceFoldersServerCapabilities> workspaceFolders) {
            this.workspaceFolders = workspaceFolders;
        }

        public Optional<FileOperation> getFileOperations() {
            return fileOperations;
        }

        public void setFileOperations(Optional<FileOperation> fileOperations) {
            this.fileOperations = fileOperations;
        }

        public Optional getExperimental() {
            return experimental;
        }

        public void setExperimental(Optional experimental) {
            this.experimental = experimental;
        }
    }
    
    private Optional<TextDocumentSyncOptions> textDocumentSync;
    private Optional<CompletionOptions> completionProvider;
    private Optional<HoverOptions> hoverProvider;
    private Optional<DeclarationOptions> declarationProvider;
    private Optional<DefinitionOptions> definitionProvider;
    private Optional<TypeDefinitionOptions> typeDefinitionProvider;
    private Optional<ImplementationOptions> implementationProvider;
    private Optional<ReferenceOptions> referencesProvider;
    private Optional<DocumentHighlightOptions> documentHighlightProvider;
    private Optional<DocumentSymbolOptions> documentSymbolProvider;
    private Optional<CodeActionOptions> codeActionProvider;
    private Optional<CodeLensOptions> codeLensProvider;
    private Optional<DocumentLinkOptions> documentLinkProvider;
    private Optional<DocumentColorOptions> colorProvider;
    private Optional<DocumentFormattingOptions> documentFormattingProvider;
    private Optional<DocumentRangeFormattingOptions> documentRangeFormattingProvider;
    private Optional<DocumentOnTypeFormattingOptions> documentOnTypeFormattingProvider;
    private Optional<RenameOptions> renameProvider;
    private Optional<FoldingRangeOptions> foldingRangeProvider;
    private Optional<ExecuteCommandOptions> executeCommandProvider;
    private Optional<SelectionRangeOptions> selectionRangeProvider;
    private Optional<LinkedEditingRangeOptions> linkedEditingRangeProvider;
    private Optional<CallHierarchyOptions> callHierarchyProvider;
    private Optional<SemanticTokensOptions> semanticTokensProvider;
    private Optional<MonikerOptions> monikerProvider;
    private Optional<WorkspaceSymbolOptions> workspaceSymbolProvider;
}
