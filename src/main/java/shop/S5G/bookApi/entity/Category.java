package shop.S5G.bookApi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long categoryId;

    @ManyToOne
    @JoinColumn(name = "categorySuperId")
    private Category parentCategory;

    private String categoryName;
    private boolean active;

    public Category(String categoryName, boolean active, Category parentCategory) {
        this.categoryName = categoryName;
        this.active = active;
        this.parentCategory = parentCategory;
    }

    public Category(String categoryName, boolean active) {
        this(categoryName, active, null);
    }
}
