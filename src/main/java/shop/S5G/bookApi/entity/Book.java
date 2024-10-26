package shop.S5G.bookApi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookId;

    @ManyToOne
    @JoinColumn(name = "publisherId")
    private Publisher publisher;

    @ManyToOne
    @JoinColumn(name = "bookStatusId")
    private BookStatus bookStatus;

    private String title;
    private String chapter;
    private String description;
    private LocalDateTime publishedDate;
    private String isbn;
    private long price;
    private BigDecimal discountRate;
    private boolean isPacked;
    private int stock;
    private long views;
    private LocalDateTime createdAt;
}
