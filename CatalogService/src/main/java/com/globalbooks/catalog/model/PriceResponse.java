package com.globalbooks.catalog.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;

/**
 * Price response DTO returned by getBookPrice operation.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PriceResponse", namespace = "http://catalog.globalbooks.com/types/v1",
    propOrder = {"bookId", "price", "currency"})
public class PriceResponse {

    @XmlElement(required = true)
    private String bookId;

    @XmlElement(required = true)
    private BigDecimal price;

    @XmlElement(required = true)
    private String currency;

    public PriceResponse() {}

    public PriceResponse(String bookId, BigDecimal price, String currency) {
        this.bookId   = bookId;
        this.price    = price;
        this.currency = currency;
    }

    public String getBookId()       { return bookId; }
    public BigDecimal getPrice()    { return price; }
    public String getCurrency()     { return currency; }

    public void setBookId(String bookId)         { this.bookId = bookId; }
    public void setPrice(BigDecimal price)       { this.price = price; }
    public void setCurrency(String currency)     { this.currency = currency; }
}
