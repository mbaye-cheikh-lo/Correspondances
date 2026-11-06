package Projet.Correspondances.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "fare")
public class Fare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fare_id")
    private Long fareId;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "period_id", nullable = false)          // POUR_PERIODE
    private FarePeriod period;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "distance_band_id", nullable = false)   // POUR_DISTANCE
    private DistanceBand distanceBand;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "class_id", nullable = false)           // EST_TARIFE_EN
    private TravelClass travelClass;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_id", nullable = false)         // POUR_PROFIL
    private TravelerProfile profile;

    public Fare() {}

    public Long getFareId() { return fareId; }
    public void setFareId(Long fareId) { this.fareId = fareId; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public FarePeriod getPeriod() { return period; }
    public void setPeriod(FarePeriod period) { this.period = period; }

    public DistanceBand getDistanceBand() { return distanceBand; }
    public void setDistanceBand(DistanceBand distanceBand) { this.distanceBand = distanceBand; }

    public TravelClass getTravelClass() { return travelClass; }
    public void setTravelClass(TravelClass travelClass) { this.travelClass = travelClass; }

    public TravelerProfile getProfile() { return profile; }
    public void setProfile(TravelerProfile profile) { this.profile = profile; }
}
