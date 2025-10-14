package ru.job4j.shortcut.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.shortcut.model.Link;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.service.link.LinkService;
import ru.job4j.shortcut.service.site.SiteService;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class LinkControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SiteService siteService;

    @Autowired
    private LinkService linkService;

    @Test
    @WithMockUser
    @Sql(statements = {"DELETE FROM links", "DELETE FROM sites"})
    void shouldConvertLink() throws Exception {
        Site site = registerSite();
        Link link = new Link();
        link.setUrl("https://job4j.ru/article/view/7");

        mockMvc.perform(post("/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(link)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").isNotEmpty());

        List<Link> links = linkService.findAll();
        assertThat(links).hasSize(1);
        assertThat(links.get(0).getUrl()).isEqualTo(link.getUrl());
        assertThat(links.get(0).getTotal()).isEqualTo(0);
        assertThat(links.get(0).getSite().getId()).isEqualTo(site.getId());
        assertThat(links.get(0).getCode()).isNotEmpty();
    }

    @Test
    @WithMockUser
    @Sql(statements = {"DELETE FROM links", "DELETE FROM sites"})
    void shouldReturnErrorMessageAboutNotRegisteredDomain() throws Exception {
        Link link = new Link();
        link.setUrl("https://job4j.ru/article/view/7");

        mockMvc.perform(post("/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(link)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Site with domain name job4j.ru not registered"));
    }

    @Test
    @WithMockUser
    @Sql(statements = {"DELETE FROM links", "DELETE FROM sites"})
    void shouldReturnErrorMessageAboutAlreadyRegisteredUrl() throws Exception {
        registerSite();
        Link link = new Link();
        link.setUrl("https://job4j.ru/article/view/7");

        mockMvc.perform(post("/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(link)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").isNotEmpty());

        mockMvc.perform(post("/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(link)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string("Link with url " + link.getUrl() + " already exists"));
    }

    @Test
    @WithMockUser
    @Sql(statements = {"DELETE FROM links", "DELETE FROM sites"})
    void shouldReturnUrlAndMakeRedirect() throws Exception {
        registerSite();
        Link link = registerLink("https://job4j.ru/article/view/7");

        mockMvc.perform(get("/redirect/" + link.getCode()))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(header().string("Location", link.getUrl()));
    }

    @Test
    @WithMockUser
    @Sql(statements = {"DELETE FROM links", "DELETE FROM sites"})
    void shouldReturnNotFoundResponse() throws Exception {
        mockMvc.perform(get("/redirect/" + "test"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    @Sql(statements = {"DELETE FROM links", "DELETE FROM sites"})
    void shouldReturnAllLinks() throws Exception {
        registerSite();
        Link link1 = registerLink("https://job4j.ru/article/view/7");
        Link link2 = registerLink("https://job4j.ru/exercise/106/task-view/532");
        mockMvc.perform(get("/statistic"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].url").value(link1.getUrl()))
                .andExpect(jsonPath("$[0].total").value(0))
                .andExpect(jsonPath("$[1].url").value(link2.getUrl()))
                .andExpect(jsonPath("$[1].total").value(0));
    }

    @Test
    @WithMockUser
    @Sql(statements = {"DELETE FROM links", "DELETE FROM sites"})
    void shouldReturnAllLinksWithTotal5ForFirstLink() throws Exception {
        registerSite();
        Link link1 = registerLink("https://job4j.ru/article/view/7");
        Link link2 = registerLink("https://job4j.ru/exercise/106/task-view/532");

        linkService.findByCode(link1.getCode());
        linkService.findByCode(link1.getCode());
        linkService.findByCode(link1.getCode());
        linkService.findByCode(link1.getCode());
        linkService.findByCode(link1.getCode());

        mockMvc.perform(get("/statistic"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].url").value(link1.getUrl()))
                .andExpect(jsonPath("$[0].total").value(5))
                .andExpect(jsonPath("$[1].url").value(link2.getUrl()))
                .andExpect(jsonPath("$[1].total").value(0));
    }

    private Site registerSite() {
        Site site = new Site();
        site.setDomainName("job4j.ru");
        site.setLogin("job4j");
        site.setPassword("job4j");
        return siteService.save(site);
    }

    private Link registerLink(String url) {
        Link link = new Link();
        link.setUrl(url);
        return linkService.save(link);
    }
}