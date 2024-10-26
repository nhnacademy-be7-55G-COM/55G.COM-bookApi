package shop.S5G.bookApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.S5G.bookApi.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
