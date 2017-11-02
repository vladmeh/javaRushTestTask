package com.vladmeh.javaRushTestTask.Controller;

import com.vladmeh.javaRushTestTask.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class BookControllerTest {

    private static final String PAGE_NUMBER_STRING = "2";
    private static final int PAGE_NUMBER = Integer.parseInt(PAGE_NUMBER_STRING) - 1;
    private static final int PAGE_SIZE = 10;
    private static final String FIELD_NAME_TITLE = "printYear";
    private static final String SORT_ORDER = "desc";
    private static final Long TEST_BOOK_ID = 1L;

    private MediaType contentType = new MediaType(MediaType.TEXT_HTML.getType(),
            MediaType.TEXT_HTML.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void viewBooksList_shouldReturnHttpResponseStatusOkNoParam() throws Exception {
        this.mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(this.contentType))
                .andExpect(content().string(containsString("список книг")));
    }

    @Test
    public void viewBooksList_houldReturnHttpResponseStatusOkWithParam() throws Exception{
        mockMvc.perform(get("/books")
                .param("page", PAGE_NUMBER_STRING)
                .param("sortBy", FIELD_NAME_TITLE)
                .param("order", SORT_ORDER)
        )
                .andExpect(status().isOk());
    }
}