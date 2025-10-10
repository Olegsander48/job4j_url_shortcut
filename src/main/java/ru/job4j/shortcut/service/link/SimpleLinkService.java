package ru.job4j.shortcut.service.link;

import org.springframework.stereotype.Service;
import ru.job4j.shortcut.model.Link;
import ru.job4j.shortcut.repository.LinkRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SimpleLinkService implements LinkService {
    private final LinkRepository linkRepository;

    public SimpleLinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public Link save(Link link) {
        return linkRepository.save(link);
    }

    @Override
    public Optional<Link> findById(int id) {
        return linkRepository.findById(id);
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
}
