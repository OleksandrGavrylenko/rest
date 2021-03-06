package it.discovery.hypermedia;

import it.discovery.controller.BookController;
import it.discovery.model.Book;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class BookResource extends ResourceSupport {
    private int id;

    private String name;

    private String author;

    private int year;

    private boolean rented;

    public BookResource(Book book) {
        id = book.getId();
        name = book.getName();
        author = book.getAuthor();
        year = book.getYear();
        add(linkTo(methodOn(BookController.class)
                .findById(id)).withSelfRel());
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
