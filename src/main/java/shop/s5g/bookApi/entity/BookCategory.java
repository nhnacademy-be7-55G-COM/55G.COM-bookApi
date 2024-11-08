package shop.s5g.bookApi.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BookCategory {

    @EmbeddedId
    private BookCategoryPk bookCategoryId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @MapsId("bookId")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @MapsId("categoryId")
    private Category category;

    public BookCategory(Book book, Category category) {
        this.book = book;
        this.category = category;
        this.bookCategoryId = new BookCategoryPk(book.getBookId(), category.getCategoryId());
    }
}
