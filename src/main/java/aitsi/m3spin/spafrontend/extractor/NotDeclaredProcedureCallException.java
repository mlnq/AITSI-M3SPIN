package aitsi.m3spin.spafrontend.extractor;

import aitsi.m3spin.spafrontend.SpaFrontendException;

public class NotDeclaredProcedureCallException extends SpaFrontendException {
    public NotDeclaredProcedureCallException(String notDeclaredProcName, String callingProc) {
        super(String.format("Encountered call to not declared procedure '%s' in procedure '%s'",
                notDeclaredProcName, callingProc));
    }
}
