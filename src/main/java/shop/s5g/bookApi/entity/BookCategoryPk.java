package shop.s5g.bookApi.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class BookCategoryPk {

    private Long bookId;
    private Long categoryId;
}
