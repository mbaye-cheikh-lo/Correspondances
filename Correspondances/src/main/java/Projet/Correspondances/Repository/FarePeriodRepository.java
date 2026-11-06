package Projet.Correspondances.Repository;

import Projet.Correspondances.Entity.FarePeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarePeriodRepository extends JpaRepository<FarePeriod, Long> {
}
