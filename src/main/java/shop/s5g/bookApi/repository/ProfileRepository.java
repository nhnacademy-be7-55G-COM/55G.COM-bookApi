package shop.s5g.bookApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.s5g.bookApi.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
