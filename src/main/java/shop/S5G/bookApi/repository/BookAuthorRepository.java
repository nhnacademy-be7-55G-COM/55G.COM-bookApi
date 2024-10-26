package shop.S5G.bookApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.S5G.bookApi.entity.BookAuthor;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, Long> {

}
