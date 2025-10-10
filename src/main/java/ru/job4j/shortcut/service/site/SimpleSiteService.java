package ru.job4j.shortcut.service.site;

import org.springframework.stereotype.Service;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.repository.SiteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SimpleSiteService implements SiteService {
    private final SiteRepository siteRepository;

    public SimpleSiteService(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    @Override
    public Site save(Site site) {
        return siteRepository.save(site);
    }

    @Override
    public Optional<Site> findById(int id) {
        return siteRepository.findById(id);
    }

    @Override
    public List<Site> findAll() {
        List<Site> sites = new ArrayList<>();
        siteRepository.findAll().forEach(sites::add);
        return sites;
    }

    @Override
    public void deleteById(int id) {
        siteRepository.deleteById(id);
    }
}
