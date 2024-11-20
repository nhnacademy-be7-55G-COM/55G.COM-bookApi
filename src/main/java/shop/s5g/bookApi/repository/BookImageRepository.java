package shop.s5g.bookApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.s5g.bookApi.entity.BookImage;

public interface BookImageRepository extends JpaRepository<BookImage, Long> {

}
