package com.globalbooks.catalog.exception;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Fault detail bean for BookNotFoundException SOAP fault.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BookNotFoundFaultDetail",
         namespace = "http://catalog.globalbooks.com/types/v1")
public class BookNotFoundFaultInfo {

    @XmlElement(required = true)
    private String bookId;

    @XmlElement(required = true)
    private String message;

    public BookNotFoundFaultInfo() {}

    public BookNotFoundFaultInfo(String bookId, String message) {
        this.bookId  = bookId;
        this.message = message;
    }

    public String getBookId()  { return bookId; }
    public String getMessage() { return message; }

    public void setBookId(String bookId)   { this.bookId = bookId; }
    public void setMessage(String message) { this.message = message; }
}
