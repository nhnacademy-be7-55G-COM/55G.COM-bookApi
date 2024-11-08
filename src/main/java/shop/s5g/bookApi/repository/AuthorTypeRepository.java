package shop.s5g.bookApi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.s5g.bookApi.entity.AuthorType;

public interface AuthorTypeRepository extends JpaRepository<AuthorType, Long> {

    Optional<AuthorType> findByTypeName(String typeName);

    boolean existsByTypeName(String typeName);
}
