package ru.job4j.shortcut.service.site;

import ru.job4j.shortcut.model.Site;

import java.util.List;
import java.util.Optional;

public interface SiteService {
    Site save(Site site);

    Optional<Site> findById(int id);

    List<Site> findAll();

    void deleteById(int id);
}
