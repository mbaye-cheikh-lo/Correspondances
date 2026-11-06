package Projet.Correspondances.Repository;

import Projet.Correspondances.Entity.Traveler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TravelerRepository extends JpaRepository<Traveler, Long> {

    Optional<Traveler> findByEmail(String email);
}
