package shop.s5g.bookApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.s5g.bookApi.entity.BookAuthor;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, Long> {

}
