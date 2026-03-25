package com.globalbooks.catalog.exception;

import javax.xml.ws.WebFault;

/**
 * Fault thrown when a requested book is not found in the catalog.
 * The @WebFault annotation maps this to a named SOAP Fault element.
 */
@WebFault(
    name            = "BookNotFoundFault",
    targetNamespace = "http://catalog.globalbooks.com/types/v1",
    faultBean       = "com.globalbooks.catalog.exception.BookNotFoundFaultInfo"
)
public class BookNotFoundException extends Exception {

    private final BookNotFoundFaultInfo faultInfo;

    public BookNotFoundException(String message, BookNotFoundFaultInfo faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    public BookNotFoundFaultInfo getFaultInfo() {
        return faultInfo;
    }
}
