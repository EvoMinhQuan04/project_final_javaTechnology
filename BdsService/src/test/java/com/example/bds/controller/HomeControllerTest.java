

package com.example.bds.controller;

import com.example.bds.config.SecurityConfigTest;
import com.example.bds.model.News;
import com.example.bds.service.NewsService;
import com.example.bds.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfigTest.class)
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private NewsService newsService;

    @Mock
    private UserService userService;

    @InjectMocks
    private HomeController homeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHome() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    void testDangTin() throws Exception {
        mockMvc.perform(get("/dang-tin"))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboardAccount"));
    }

    @Test
    void testChitietTinTuc() throws Exception {
        News news = new News();
        news.setId(1);
        news.setTitle("Sample News");
        when(newsService.getNewsById(1)).thenReturn(news);

        mockMvc.perform(get("/chitiet-tin-tuc/5"))
                .andExpect(status().isOk())
                .andExpect(view().name("chitiet_tin_tuc"))
                .andExpect(model().attribute("newsDetail", news));
    }

    @Test
    void testTinTuc() throws Exception {
        News news1 = new News();
        News news2 = new News();
        when(newsService.listLand()).thenReturn(Collections.singletonList(news1));
        when(newsService.listLandTop4()).thenReturn(Collections.singletonList(news2));

        mockMvc.perform(get("/tin-tuc"))
                .andExpect(status().isOk())
                .andExpect(view().name("tin_tuc"))
                .andExpect(model().attribute("news", Collections.singletonList(news1)))
                .andExpect(model().attribute("top4", Collections.singletonList(news2)));
    }

    @Test
    void testLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void testForgot() throws Exception {
        mockMvc.perform(get("/forgot"))
                .andExpect(status().isOk())
                .andExpect(view().name("forgot"));
    }

    @Test
    void testRegister() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }
}


