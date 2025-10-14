package ru.job4j.shortcut.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.job4j.shortcut.dto.LinkConvertDto;
import ru.job4j.shortcut.dto.LinkStatisticDto;
import ru.job4j.shortcut.model.Link;
import ru.job4j.shortcut.service.link.LinkService;
import ru.job4j.shortcut.validation.Operation;

import java.util.List;

@RestController
@Validated
public class LinkController {
    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping("/convert")
    @Validated({Operation.OnConversion.class})
    public LinkConvertDto convert(@RequestBody @Valid Link link) {
        return new LinkConvertDto(linkService.save(link).getCode());
    }

    @GetMapping("/redirect/{code}")
    @Validated
    public ResponseEntity<String> registration(@PathVariable("code")
                                                   @NotBlank(message = "Unique code can not be null or empty")
                                                   String uniqueCode) {
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
