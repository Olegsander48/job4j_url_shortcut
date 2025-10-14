package ru.job4j.shortcut.controller;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.job4j.shortcut.dto.SiteCredentialsDto;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.service.site.SiteService;
import ru.job4j.shortcut.validation.Operation;

import java.util.Optional;

@RestController
@Validated
public class SiteController {
    private final SiteService siteService;

    public SiteController(SiteService siteService) {
        this.siteService = siteService;
    }

    @PostMapping("/registration")
    @Validated({Operation.OnRegistration.class})
    public SiteCredentialsDto registration(@RequestBody @Valid Site site) {
        Optional<Site> siteOptional = siteService.findByDomainName(site.getDomainName());
        if (siteOptional.isPresent()) {
            return new SiteCredentialsDto(false,
                    siteOptional.get().getLogin(),
                    siteOptional.get().getPassword());
        }

        Site savedSite = siteService.save(site);
        return new SiteCredentialsDto(true,
                savedSite.getLogin(),
                savedSite.getPassword());
    }
}
