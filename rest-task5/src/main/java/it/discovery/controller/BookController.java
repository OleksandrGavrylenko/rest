package it.discovery.controller;

import it.discovery.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

    @GetMapping(value = "sample", produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Book sample() {
        return new Book();
    }


}
