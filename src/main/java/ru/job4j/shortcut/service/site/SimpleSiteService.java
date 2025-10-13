package ru.job4j.shortcut.service.site;

import org.springframework.stereotype.Service;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.repository.SiteRepository;

import java.util.*;

@Service
public class SimpleSiteService implements SiteService {
    private final SiteRepository siteRepository;

    public SimpleSiteService(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    @Override
    public Site save(Site site) {
        site.setLogin(UUID.randomUUID().toString().substring(24));
        site.setPassword(UUID.randomUUID().toString().substring(24));
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
    public Optional<Site> findByDomainName(String domainName) {
        return siteRepository.findByDomainName(domainName);
    }

    @Override
    public void deleteById(int id) {
        siteRepository.deleteById(id);
    }
}
