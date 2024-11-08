package shop.S5G.bookApi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.S5G.bookApi.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryNameAndParentCategory(String categoryName, Category parentCategory);
}
