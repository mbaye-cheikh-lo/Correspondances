package Projet.Correspondances.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @Column(name = "booking_date")
    private LocalDateTime bookingDate;

    @Column(name = "status")
    private String status;

    @Column(name = "price_paid", precision = 10, scale = 2)
    private BigDecimal pricePaid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "traveler_id")      // EFFECTUE
    private Traveler traveler;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "circulation_id")   // CONCERNE
    private Circulation circulation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")          // OCCUPE
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fare_id")          // APPLIQUE
    private Fare fare;

    // Constructeur sans argument requis par JPA
    public Booking() {
    }

    public Booking(LocalDateTime bookingDate, String status, BigDecimal pricePaid,
                   Traveler traveler, Circulation circulation, Seat seat, Fare fare) {
        this.bookingDate = bookingDate;
        this.status = status;
        this.pricePaid = pricePaid;
        this.traveler = traveler;
        this.circulation = circulation;
        this.seat = seat;
        this.fare = fare;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getPricePaid() {
        return pricePaid;
    }

    public void setPricePaid(BigDecimal pricePaid) {
        this.pricePaid = pricePaid;
    }

    public Traveler getTraveler() {
        return traveler;
    }

    public void setTraveler(Traveler traveler) {
        this.traveler = traveler;
    }

    public Circulation getCirculation() {
        return circulation;
    }

    public void setCirculation(Circulation circulation) {
        this.circulation = circulation;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Fare getFare() {
        return fare;
    }

    public void setFare(Fare fare) {
        this.fare = fare;
    }
}