package Projet.Correspondances.Repository;

import Projet.Correspondances.Entity.TravelClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelClassRepository extends JpaRepository<TravelClass, Long> {
}
