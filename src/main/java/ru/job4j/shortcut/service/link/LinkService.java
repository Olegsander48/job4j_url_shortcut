package ru.job4j.shortcut.service.link;

import ru.job4j.shortcut.model.Link;

import java.util.List;
import java.util.Optional;

public interface LinkService {
    Link save(Link link);

    Optional<Link> findById(int id);

    Optional<Link> findByCode(String code);

    List<Link> findAll();

    void deleteById(int id);
}
