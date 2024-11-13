package shop.s5g.bookApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.s5g.bookApi.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByIsbn(String isbn);
}
