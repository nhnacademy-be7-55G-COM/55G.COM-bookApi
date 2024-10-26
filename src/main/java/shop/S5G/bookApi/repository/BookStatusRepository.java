package shop.S5G.bookApi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.S5G.bookApi.entity.BookStatus;

public interface BookStatusRepository extends JpaRepository<BookStatus, Long> {
    Optional<BookStatus> findByTypeName(String typeName);
}
