package ru.job4j.shortcut.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.shortcut.dto.LinkConvertDto;
import ru.job4j.shortcut.dto.LinkStatisticDto;
import ru.job4j.shortcut.model.Link;
import ru.job4j.shortcut.service.link.LinkService;

import java.util.List;

@RestController
public class LinkController {
    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping("/convert")
    public LinkConvertDto convert(@RequestBody Link link) {
        return new LinkConvertDto(linkService.save(link).getCode());
    }

    @GetMapping("/redirect/{code}")
    public ResponseEntity<String> registration(@PathVariable("code") String uniqueCode) {
        return linkService.findByCode(uniqueCode).<ResponseEntity<String>>map(link -> ResponseEntity
                .status(HttpStatus.FOUND)
                .header("Location", link.getUrl())
                .build())
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/statistic")
    public List<LinkStatisticDto> statistic() {
        return linkService.findAll()
                .stream()
                .map(link -> new LinkStatisticDto(link.getUrl(), link.getTotal()))
                .toList();
    }
}
