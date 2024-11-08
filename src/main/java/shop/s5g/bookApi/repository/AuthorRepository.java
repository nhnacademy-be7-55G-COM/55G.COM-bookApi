package shop.s5g.bookApi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.s5g.bookApi.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);
}
