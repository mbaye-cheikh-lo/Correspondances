package Projet.Correspondances.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "distance_band")
public class DistanceBand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "distance_band_id")
    private Long distanceBandId;

    @Column(name = "min_distance_km", precision = 10, scale = 2)
    private BigDecimal minDistanceKm;

    @Column(name = "max_distance_km", precision = 10, scale = 2)
    private BigDecimal maxDistanceKm;

    public DistanceBand() {}

    public Long getDistanceBandId() { return distanceBandId; }
    public void setDistanceBandId(Long distanceBandId) { this.distanceBandId = distanceBandId; }

    public BigDecimal getMinDistanceKm() { return minDistanceKm; }
    public void setMinDistanceKm(BigDecimal minDistanceKm) { this.minDistanceKm = minDistanceKm; }

    public BigDecimal getMaxDistanceKm() { return maxDistanceKm; }
    public void setMaxDistanceKm(BigDecimal maxDistanceKm) { this.maxDistanceKm = maxDistanceKm; }
}