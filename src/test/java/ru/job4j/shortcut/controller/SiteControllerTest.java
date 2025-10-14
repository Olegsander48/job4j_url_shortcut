package ru.job4j.shortcut.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.service.site.SiteService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class SiteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SiteService siteService;

    @Test
    @Sql(statements = {"DELETE FROM links", "DELETE FROM sites"})
    void shouldRegisterSite() throws Exception {
        Site site = new Site();
        site.setDomainName("job4j.ru");

        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(site)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.registration").value(true))
                .andExpect(jsonPath("$.login").isNotEmpty())
                .andExpect(jsonPath("$.password").isNotEmpty());

        List<Site> sites = siteService.findAll();
        assertThat(sites).hasSize(1);
        assertThat(sites.get(0).getDomainName()).isEqualTo("job4j.ru");
        assertThat(sites.get(0).getLogin()).isNotBlank();
        assertThat(sites.get(0).getPassword()).isNotBlank();
    }

    @Test
    @Sql(statements = {"DELETE FROM links", "DELETE FROM sites"})
    void shouldReturnFalseAndSiteCredentials() throws Exception {
        Site site = new Site();
        site.setDomainName("job4j.ru");

        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(site)))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(site)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.registration").value(false))
                .andExpect(jsonPath("$.login").isNotEmpty())
                .andExpect(jsonPath("$.password").isNotEmpty());
    }

    @Test
    @Sql(statements = {"DELETE FROM links", "DELETE FROM sites"})
    void shouldReturnErrorMessageAboutEmptyDomainName() throws Exception {
        Site site = new Site();
        site.setDomainName(" ");

        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(site)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string("registration.site.domainName: Site domain can not be null or empty"));
    }
}