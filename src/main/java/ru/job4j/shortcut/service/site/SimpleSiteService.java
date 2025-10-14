package ru.job4j.shortcut.service.site;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.repository.SiteRepository;

import java.util.*;

@Service
public class SimpleSiteService implements SiteService {
    private final SiteRepository siteRepository;
    private final PasswordEncoder passwordEncoder;

    public SimpleSiteService(SiteRepository siteRepository, PasswordEncoder passwordEncoder) {
        this.siteRepository = siteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Site save(Site site) {
        String login = UUID.randomUUID().toString().substring(24);
        site.setLogin(login);
        site.setPassword(passwordEncoder.encode(login));
        return siteRepository.save(site);
    }

    @Override
    @Transactional
    public Optional<Site> findById(int id) {
        return siteRepository.findById(id);
    }

    @Override
    @Transactional
    public List<Site> findAll() {
        List<Site> sites = new ArrayList<>();
        siteRepository.findAll().forEach(sites::add);
        return sites;
    }

    @Override
    @Transactional
    public Optional<Site> findByDomainName(String domainName) {
        return siteRepository.findByDomainName(domainName);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        siteRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Site> site = siteRepository.findSiteByLogin(login);
        if (site.isEmpty()) {
            throw new UsernameNotFoundException(login);
        }
        return new User(site.get().getLogin(), site.get().getPassword(), new ArrayList<>());
    }
}
