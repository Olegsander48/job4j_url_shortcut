package ru.job4j.shortcut.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.shortcut.model.Link;

import java.util.Optional;

public interface LinkRepository extends CrudRepository<Link, Integer> {
    @Query(value = "UPDATE links SET total = total + 1 WHERE code = :code RETURNING *", nativeQuery = true)
    Optional<Link> findByCode(String code);

    Optional<Link> findByUrl(String url);
}
