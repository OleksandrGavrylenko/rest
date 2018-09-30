package it.discovery.advice;

import it.discovery.exception.BookNotFoundException;
import it.discovery.model.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Book> handleNotFound(
            BookNotFoundException ex, WebRequest req) {
        return ResponseEntity.notFound().build();
    }
}
