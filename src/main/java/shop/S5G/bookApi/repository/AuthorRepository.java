package shop.S5G.bookApi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.S5G.bookApi.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);
}
