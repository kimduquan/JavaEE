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
public class TextDocumentClientCapabilities {
    private Optional<TextDocumentSyncClientCapabilities> synchronization;
    private Optional<CompletionClientCapabilities> completion;
    private Optional<HoverClientCapabilities> hover;
    private Optional<SignatureHelpClientCapabilities> signatureHelp;
    private Optional<DeclarationClientCapabilities> declaration;
    private Optional<DefinitionClientCapabilities> definition;
    private Optional<TypeDefinitionClientCapabilities> typeDefinition;
    private Optional<ImplementationClientCapabilities> implementation;
    private Optional<ReferenceClientCapabilities> references;
    private Optional<DocumentHighlightClientCapabilities> documentHighlight;
    private Optional<DocumentSymbolClientCapabilities> documentSymbol;
    private Optional<CodeActionClientCapabilities> codeAction;
    private Optional<CodeLensClientCapabilities> codeLens;
    private Optional<DocumentLinkClientCapabilities> documentLink;
    private Optional<DocumentColorClientCapabilities> colorProvider;
    private Optional<DocumentFormattingClientCapabilities> formatting;
    private Optional<DocumentRangeFormattingClientCapabilities> rangeFormatting;
    private Optional<DocumentOnTypeFormattingClientCapabilities> onTypeFormatting;
    private Optional<RenameClientCapabilities> rename;
    private Optional<PublishDiagnosticsClientCapabilities> publishDiagnostics;
    private Optional<FoldingRangeClientCapabilities> foldingRange;
    private Optional<SelectionRangeClientCapabilities> selectionRange;
    private Optional<LinkedEditingRangeClientCapabilities> linkedEditingRange;
    private Optional<CallHierarchyClientCapabilities> callHierarchy;
    private Optional<SemanticTokensClientCapabilities> semanticTokens;
    private Optional<MonikerClientCapabilities> moniker;

    public Optional<TextDocumentSyncClientCapabilities> getSynchronization() {
        return synchronization;
    }

    public void setSynchronization(Optional<TextDocumentSyncClientCapabilities> synchronization) {
        this.synchronization = synchronization;
    }

    public Optional<CompletionClientCapabilities> getCompletion() {
        return completion;
    }

    public void setCompletion(Optional<CompletionClientCapabilities> completion) {
        this.completion = completion;
    }

    public Optional<HoverClientCapabilities> getHover() {
        return hover;
    }

    public void setHover(Optional<HoverClientCapabilities> hover) {
        this.hover = hover;
    }

    public Optional<SignatureHelpClientCapabilities> getSignatureHelp() {
        return signatureHelp;
    }

    public void setSignatureHelp(Optional<SignatureHelpClientCapabilities> signatureHelp) {
        this.signatureHelp = signatureHelp;
    }

    public Optional<DeclarationClientCapabilities> getDeclaration() {
        return declaration;
    }

    public void setDeclaration(Optional<DeclarationClientCapabilities> declaration) {
        this.declaration = declaration;
    }

    public Optional<DefinitionClientCapabilities> getDefinition() {
        return definition;
    }

    public void setDefinition(Optional<DefinitionClientCapabilities> definition) {
        this.definition = definition;
    }

    public Optional<TypeDefinitionClientCapabilities> getTypeDefinition() {
        return typeDefinition;
    }

    public void setTypeDefinition(Optional<TypeDefinitionClientCapabilities> typeDefinition) {
        this.typeDefinition = typeDefinition;
    }

    public Optional<ImplementationClientCapabilities> getImplementation() {
        return implementation;
    }

    public void setImplementation(Optional<ImplementationClientCapabilities> implementation) {
        this.implementation = implementation;
    }

    public Optional<ReferenceClientCapabilities> getReferences() {
        return references;
    }

    public void setReferences(Optional<ReferenceClientCapabilities> references) {
        this.references = references;
    }

    public Optional<DocumentHighlightClientCapabilities> getDocumentHighlight() {
        return documentHighlight;
    }

    public void setDocumentHighlight(Optional<DocumentHighlightClientCapabilities> documentHighlight) {
        this.documentHighlight = documentHighlight;
    }

    public Optional<DocumentSymbolClientCapabilities> getDocumentSymbol() {
        return documentSymbol;
    }

    public void setDocumentSymbol(Optional<DocumentSymbolClientCapabilities> documentSymbol) {
        this.documentSymbol = documentSymbol;
    }

    public Optional<CodeActionClientCapabilities> getCodeAction() {
        return codeAction;
    }

    public void setCodeAction(Optional<CodeActionClientCapabilities> codeAction) {
        this.codeAction = codeAction;
    }

    public Optional<CodeLensClientCapabilities> getCodeLens() {
        return codeLens;
    }

    public void setCodeLens(Optional<CodeLensClientCapabilities> codeLens) {
        this.codeLens = codeLens;
    }

    public Optional<DocumentLinkClientCapabilities> getDocumentLink() {
        return documentLink;
    }

    public void setDocumentLink(Optional<DocumentLinkClientCapabilities> documentLink) {
        this.documentLink = documentLink;
    }

    public Optional<DocumentColorClientCapabilities> getColorProvider() {
        return colorProvider;
    }

    public void setColorProvider(Optional<DocumentColorClientCapabilities> colorProvider) {
        this.colorProvider = colorProvider;
    }

    public Optional<DocumentFormattingClientCapabilities> getFormatting() {
        return formatting;
    }

    public void setFormatting(Optional<DocumentFormattingClientCapabilities> formatting) {
        this.formatting = formatting;
    }

    public Optional<DocumentRangeFormattingClientCapabilities> getRangeFormatting() {
        return rangeFormatting;
    }

    public void setRangeFormatting(Optional<DocumentRangeFormattingClientCapabilities> rangeFormatting) {
        this.rangeFormatting = rangeFormatting;
    }

    public Optional<DocumentOnTypeFormattingClientCapabilities> getOnTypeFormatting() {
        return onTypeFormatting;
    }

    public void setOnTypeFormatting(Optional<DocumentOnTypeFormattingClientCapabilities> onTypeFormatting) {
        this.onTypeFormatting = onTypeFormatting;
    }

    public Optional<RenameClientCapabilities> getRename() {
        return rename;
    }

    public void setRename(Optional<RenameClientCapabilities> rename) {
        this.rename = rename;
    }

    public Optional<PublishDiagnosticsClientCapabilities> getPublishDiagnostics() {
        return publishDiagnostics;
    }

    public void setPublishDiagnostics(Optional<PublishDiagnosticsClientCapabilities> publishDiagnostics) {
        this.publishDiagnostics = publishDiagnostics;
    }

    public Optional<FoldingRangeClientCapabilities> getFoldingRange() {
        return foldingRange;
    }

    public void setFoldingRange(Optional<FoldingRangeClientCapabilities> foldingRange) {
        this.foldingRange = foldingRange;
    }

    public Optional<SelectionRangeClientCapabilities> getSelectionRange() {
        return selectionRange;
    }

    public void setSelectionRange(Optional<SelectionRangeClientCapabilities> selectionRange) {
        this.selectionRange = selectionRange;
    }

    public Optional<LinkedEditingRangeClientCapabilities> getLinkedEditingRange() {
        return linkedEditingRange;
    }

    public void setLinkedEditingRange(Optional<LinkedEditingRangeClientCapabilities> linkedEditingRange) {
        this.linkedEditingRange = linkedEditingRange;
    }

    public Optional<CallHierarchyClientCapabilities> getCallHierarchy() {
        return callHierarchy;
    }

    public void setCallHierarchy(Optional<CallHierarchyClientCapabilities> callHierarchy) {
        this.callHierarchy = callHierarchy;
    }

    public Optional<SemanticTokensClientCapabilities> getSemanticTokens() {
        return semanticTokens;
    }

    public void setSemanticTokens(Optional<SemanticTokensClientCapabilities> semanticTokens) {
        this.semanticTokens = semanticTokens;
    }

    public Optional<MonikerClientCapabilities> getMoniker() {
        return moniker;
    }

    public void setMoniker(Optional<MonikerClientCapabilities> moniker) {
        this.moniker = moniker;
    }
}
