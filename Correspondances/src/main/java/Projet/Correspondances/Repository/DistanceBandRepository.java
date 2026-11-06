package Projet.Correspondances.Repository;

import Projet.Correspondances.Entity.DistanceBand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistanceBandRepository extends JpaRepository<DistanceBand, Long> {
}
