package com.vladmeh.javaRushTestTask.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vladmeh.javaRushTestTask.Application;
import com.vladmeh.javaRushTestTask.BookBuilder;
import com.vladmeh.javaRushTestTask.Entity.Book;
import com.vladmeh.javaRushTestTask.Service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class BookControllerTest {

    private static final String PAGE_NUMBER_STRING = "2";
    private static final int PAGE_NUMBER = Integer.parseInt(PAGE_NUMBER_STRING) - 1;
    private static final int PAGE_SIZE = 10;
    private static final String FIELD_NAME_TITLE = "printYear";
    private static final String SORT_ORDER = "desc";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private List<Book> bookList = new ArrayList<>();

    private Pageable pageRequest;

    @Autowired
    private BookService bookService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        mappingJackson2HttpMessageConverter = Arrays.stream(converters)
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                mappingJackson2HttpMessageConverter);
    }


    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getAllBook_shouldReturnSizeColletionTitleFirstBookAsJson() throws Exception {
        mockMvc.perform(get("/books/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(this.contentType))
                .andExpect(jsonPath("$", hasSize(14)))
                .andExpect(jsonPath("$[0].title", is("Spring 4 для профессионалов")))
        ;
    }

    @Test
    public void getPageBooks_shouldReturnHttpResponseStatusOkNoParam() throws Exception {
        mockMvc.perform(get("/books")).andExpect(status().isOk());
    }

    @Test
    public void getPageBooks_shouldReturnHttpResponseStatusOkWithParam() throws Exception {
        mockMvc.perform(get("/books")
                .param("page", PAGE_NUMBER_STRING)
                .param("sortBy", FIELD_NAME_TITLE)
                .param("order", SORT_ORDER)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void getPageBooks_shouldReturnPageNumberAndPageSizeAsJsonNoParam() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(this.contentType))
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.size", is(PAGE_SIZE)))
                .andExpect(jsonPath("$.content", hasSize(10)))
        ;
    }

    @Test
    public void getPageBooks_shouldReturnPageNumberAndPageSizeAsJsonWithParam() throws Exception {
        mockMvc.perform(get("/books")
                .param("page", PAGE_NUMBER_STRING)
                .param("sortBy", FIELD_NAME_TITLE)
                .param("order", SORT_ORDER)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(this.contentType))
                .andExpect(jsonPath("$.number", is(PAGE_NUMBER)))
                .andExpect(jsonPath("$.size", is(PAGE_SIZE)));
    }

    @Test
    public void findBookById_shouldReturnHttpResponseStatusIsOKAndTitleAsJson() throws Exception{
        mockMvc.perform(get("/books/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(this.contentType))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Spring 4 для профессионалов")));
    }

    @Test
    public void create_shouldReturnHttpResponseStatusIsCreated() throws Exception {
        Book book = new BookBuilder()
                .autor("Иван Портянкин")
                .title("Swing. Эффектные пользовательские интерфейсы")
                .description("Создание пользовательских интерфейсов Java-приложений с помощью библиотеки Swing и Java Foundation Classes")
                .isbn("978-5-85582-305-9")
                .printYear(2011)
                .readAlready(false)
                .build();

        String bookJson = json(book);

        mockMvc.perform(post("/books")
                .contentType(contentType)
                .content(bookJson)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void update__shouldReturnHttpResponseStatusIsOK() throws Exception{
        Book book = new BookBuilder()
                .autor("Мет Зандстр")
                .title("PHP объекты, шаблоны и методики программирования")
                .description("Создавайте высокопрофессиональный код на PHP, изучив его объектно-орентированные возможности, шаблоны проектирования и важные средства разработ")
                .isbn("978-5-8459-1689-1")
                .printYear(2013)
                .readAlready(true)
                .build();

        String bookJson = json(book);

        mockMvc.perform(put("/books/{id}", 31L)
                .contentType(contentType)
                .content(bookJson)
        )
                .andExpect(status().isOk());
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();

        this.mappingJackson2HttpMessageConverter.write(
                o ,MediaType.APPLICATION_JSON, mockHttpOutputMessage);

        return mockHttpOutputMessage.getBodyAsString();
    }
}
