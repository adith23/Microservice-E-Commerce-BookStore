package com.globalbooks.catalog;

import com.globalbooks.catalog.exception.BookNotFoundException;
import com.globalbooks.catalog.model.Book;
import com.globalbooks.catalog.model.PriceResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

/**
 * SEI (Service Endpoint Interface) for CatalogService.
 * Defines the SOAP contract — matches the portType in catalog.wsdl.
 */
@WebService(
    name            = "CatalogPortType",
    targetNamespace = "http://catalog.globalbooks.com/v1"
)
public interface CatalogPortType {

    @WebMethod(operationName = "getBookById")
    Book getBookById(@WebParam(name = "bookId") String bookId)
            throws BookNotFoundException;

    @WebMethod(operationName = "getBookPrice")
    PriceResponse getBookPrice(@WebParam(name = "bookId") String bookId)
            throws BookNotFoundException;

    @WebMethod(operationName = "searchBooks")
    List<Book> searchBooks(@WebParam(name = "keyword") String keyword);
}
