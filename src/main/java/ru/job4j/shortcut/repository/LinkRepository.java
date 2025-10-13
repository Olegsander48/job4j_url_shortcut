package ru.job4j.shortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.shortcut.model.Link;

import java.util.Optional;

public interface LinkRepository extends CrudRepository<Link, Integer> {
    Optional<Link> findByCode(String code);
}
