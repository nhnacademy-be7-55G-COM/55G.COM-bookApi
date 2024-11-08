package shop.s5g.bookApi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookAuthor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookAuthorId;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "authorTypeId")
    private AuthorType authorType;

    @ManyToOne
    @JoinColumn(name = "authorId")
    private Author author;

    public BookAuthor(Book book,Author author,AuthorType authorType){
        this.book=book;
        this.author=author;
        this.authorType=authorType;
    }
}
