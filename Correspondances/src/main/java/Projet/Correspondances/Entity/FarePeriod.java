package Projet.Correspondances.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "fare_period")
public class FarePeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "period_id")
    private Long periodId;

    @Column(name = "period_name", nullable = false, length = 100)
    private String periodName;

    public FarePeriod() {}

    public Long getPeriodId() { return periodId; }
    public void setPeriodId(Long periodId) { this.periodId = periodId; }

    public String getPeriodName() { return periodName; }
    public void setPeriodName(String periodName) { this.periodName = periodName; }
}
