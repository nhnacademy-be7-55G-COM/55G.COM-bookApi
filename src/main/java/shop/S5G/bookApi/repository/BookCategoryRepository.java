package shop.S5G.bookApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.S5G.bookApi.entity.BookCategory;
import shop.S5G.bookApi.entity.BookCategoryPk;

public interface BookCategoryRepository extends JpaRepository<BookCategory, BookCategoryPk> {

}
