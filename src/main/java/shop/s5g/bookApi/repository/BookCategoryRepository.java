package shop.s5g.bookApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.s5g.bookApi.entity.BookCategory;
import shop.s5g.bookApi.entity.BookCategoryPk;

public interface BookCategoryRepository extends JpaRepository<BookCategory, BookCategoryPk> {

}
