package shop.S5G.bookApi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long authorId;

    @OneToOne
    @JoinColumn(name = "profileId")
    private Profile profile;

    private String name;
    private boolean active;

    public Author(String name, boolean active, Profile profile) {
        this.name = name;
        this.active = active;
        this.profile = profile;
    }

    public Author(String name, boolean active) {
        this(name, active, null);
    }
}
