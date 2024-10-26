package shop.S5G.bookApi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Profile {

    @Id
    private long profileId;

    private String birth;
    private int debutYear;
    private String introduction;
    private String imageName;
    private boolean active;
}
