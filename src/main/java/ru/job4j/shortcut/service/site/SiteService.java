package ru.job4j.shortcut.service.site;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.job4j.shortcut.model.Site;

import java.util.List;
import java.util.Optional;

public interface SiteService extends UserDetailsService {
    Site save(Site site);

    Optional<Site> findById(int id);

    List<Site> findAll();

    Optional<Site> findByDomainName(String domainName);

    void deleteById(int id);
}
