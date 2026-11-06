package Projet.Correspondances.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "circulation")
public class Circulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "circulation_id")
    private Long circulationId;

    @Column(name = "circulation_date")
    private LocalDate circulationDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "train_id")          // ASSURE
    private Train train;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "calendar_id")       // SUIT
    private ServiceCalendar calendar;

    public Circulation() {
    }

    public Circulation(LocalDate circulationDate, Train train, ServiceCalendar calendar) {
        this.circulationDate = circulationDate;
        this.train = train;
        this.calendar = calendar;
    }

    public Long getCirculationId() {
        return circulationId;
    }

    public void setCirculationId(Long circulationId) {
        this.circulationId = circulationId;
    }

    public LocalDate getCirculationDate() {
        return circulationDate;
    }

    public void setCirculationDate(LocalDate circulationDate) {
        this.circulationDate = circulationDate;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public ServiceCalendar getCalendar() {
        return calendar;
    }

    public void setCalendar(ServiceCalendar calendar) {
        this.calendar = calendar;
    }
}