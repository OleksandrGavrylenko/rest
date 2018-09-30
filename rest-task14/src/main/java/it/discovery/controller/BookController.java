package it.discovery.controller;

import it.discovery.exception.BookNotFoundException;
import it.discovery.hypermedia.BookResource;
import it.discovery.model.Book;
import it.discovery.pagination.Page;
import it.discovery.pagination.PageCriteria;
import it.discovery.repository.BookRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<List<BookResource>> findAll(@RequestParam(required = false,
    defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "0")
            int size) {
        Page pageResponse = bookRepository.searchBooks(new PageCriteria(page, size));

        return ResponseEntity.ok().header("X-TOTAL-COUNT",
                String.valueOf(pageResponse.getTotalCount()))
                .body(pageResponse.getBooks().stream().map(BookResource::new).collect(Collectors.toList()));
    }

    @GetMapping(path = "/{id}")
    @Cacheable("books")
    public BookResource findById(@PathVariable int id) {
        Book book = bookRepository.findById(id);
        return Optional.ofNullable(book).map(BookResource::new)
                .orElseThrow(BookNotFoundException::new);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@Valid @RequestBody Book book) {
        bookRepository.save(book);
        return book;
    }

    @PutMapping("/{id}")
    @CachePut("books")
    public Book update(@PathVariable int id, @RequestBody Book book) {
        bookRepository.save(book);
        return book;
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict("books")
    public void delete(@PathVariable int id) {
        bookRepository.delete(id);
    }


}
