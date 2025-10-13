package ru.job4j.shortcut.service.link;

import org.springframework.stereotype.Service;
import ru.job4j.shortcut.model.Link;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.repository.LinkRepository;
import ru.job4j.shortcut.repository.SiteRepository;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SimpleLinkService implements LinkService {
    private final LinkRepository linkRepository;
    private final SiteRepository siteRepository;

    public SimpleLinkService(LinkRepository linkRepository, SiteRepository siteRepository) {
        this.linkRepository = linkRepository;
        this.siteRepository = siteRepository;
    }

    @Override
    public Link save(Link link) {
        String domain = extractDomainName(link.getUrl());
        Optional<Site> siteOptional = siteRepository.findByDomainName(domain);
        if (siteOptional.isEmpty()) {
            throw new NoSuchElementException("Site with domain name " + domain + " not registered");
        }
        link.setSite(siteOptional.get());
        link.setCode(UUID.randomUUID().toString().substring(24));
        return linkRepository.save(link);
    }

    @Override
    public Optional<Link> findById(int id) {
        return linkRepository.findById(id);
    }

    @Override
    public Optional<Link> findByCode(String code) {
        return linkRepository.findByCode(code);
    }

    @Override
    public List<Link> findAll() {
        List<Link> links = new ArrayList<>();
        linkRepository.findAll().forEach(links::add);
        return links;
    }

    @Override
    public void deleteById(int id) {
        linkRepository.deleteById(id);
    }

    private String extractDomainName(String fullUrl) {
        Pattern pattern = Pattern.compile("^https?://((?:[a-z0-9-]+\\.)+[a-z]{2,})(?:/|$)");
        Matcher matcher = pattern.matcher(fullUrl);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
