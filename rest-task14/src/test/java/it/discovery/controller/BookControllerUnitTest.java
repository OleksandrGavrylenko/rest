package it.discovery.controller;

import io.restassured.http.ContentType;
import it.discovery.RestApplication;
import it.discovery.model.Book;
import it.discovery.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(RestApplication.class)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class BookControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<Book> jacksonTester;

    @MockBean
    private BookRepository bookRepository;

    @Test
    public void findById_invalidId_bookNotFound() throws Exception {
        //Given
        int bookId = 1000;
        given(bookRepository.findById(bookId)).willReturn(null);
        //Then
        ResultActions resultActions = mockMvc.perform(get("/book/" + bookId))
                .andDo(print());
        //When
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void findById_nonNumericId_returnBadRequest() throws Exception {
        //Given
        //Then
        ResultActions resultActions = mockMvc.perform(get("/book/aaa"))
                .andDo(print());
        //When
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void save_bookAuthorEmpty_returnBadRequest() throws Exception {
        //Given
        Book book = new Book();
        book.setName("Java");
        book.setYear(2010);
        //Then
        ResultActions resultActions = mockMvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jacksonTester.write(book).getJson()));
        //When
        resultActions.andExpect(status().isBadRequest());
    }


}

