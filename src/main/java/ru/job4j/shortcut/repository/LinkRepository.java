package ru.job4j.shortcut.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.shortcut.model.Link;

import java.util.Optional;

public interface LinkRepository extends CrudRepository<Link, Integer> {
    @Modifying
    @Query(value = "UPDATE links SET total = total + 1 WHERE code = :code", nativeQuery = true)
    void incrementTotalByCode(String code);

    Optional<Link> findByCode(String code);

    Optional<Link> findByUrl(String url);
}
