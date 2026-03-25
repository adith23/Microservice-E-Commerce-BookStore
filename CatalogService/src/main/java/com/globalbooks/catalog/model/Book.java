package com.globalbooks.catalog.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;

/**
 * Book domain model for GlobalBooks CatalogService.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Book", namespace = "http://catalog.globalbooks.com/types/v1",
    propOrder = {"bookId", "title", "author", "isbn", "price", "currency", "category"})
public class Book {

    @XmlElement(required = true)
    private String bookId;

    @XmlElement(required = true)
    private String title;

    @XmlElement(required = true)
    private String author;

    @XmlElement(required = true)
    private String isbn;

    @XmlElement(required = true)
    private BigDecimal price;

    @XmlElement(required = true)
    private String currency;

    @XmlElement
    private String category;

    // Default constructor required for JAX-WS
    public Book() {}

    public Book(String bookId, String title, String author, String isbn,
                BigDecimal price, String currency, String category) {
        this.bookId   = bookId;
        this.title    = title;
        this.author   = author;
        this.isbn     = isbn;
        this.price    = price;
        this.currency = currency;
        this.category = category;
    }

    public String getBookId()       { return bookId; }
    public String getTitle()        { return title; }
    public String getAuthor()       { return author; }
    public String getIsbn()         { return isbn; }
    public BigDecimal getPrice()    { return price; }
    public String getCurrency()     { return currency; }
    public String getCategory()     { return category; }

    public void setBookId(String bookId)         { this.bookId = bookId; }
    public void setTitle(String title)           { this.title = title; }
    public void setAuthor(String author)         { this.author = author; }
    public void setIsbn(String isbn)             { this.isbn = isbn; }
    public void setPrice(BigDecimal price)       { this.price = price; }
    public void setCurrency(String currency)     { this.currency = currency; }
    public void setCategory(String category)     { this.category = category; }
}
