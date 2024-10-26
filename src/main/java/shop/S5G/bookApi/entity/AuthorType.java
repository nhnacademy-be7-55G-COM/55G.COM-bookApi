package shop.S5G.bookApi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long authorTypeId;
    private String typeName;
    private boolean active;
}
