package shop.S5G.bookApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.S5G.bookApi.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
