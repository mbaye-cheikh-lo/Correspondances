package Projet.Correspondances.Repository;

import Projet.Correspondances.Entity.TravelerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelerProfileRepository extends JpaRepository<TravelerProfile, Long> {
}
