package Projet.Correspondances.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "service_exception")
public class ServiceException {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exception_id")
    private Long exceptionId;

    @Column(name = "exception_date", nullable = false)
    private LocalDate exceptionDate;

    @Column(name = "exception_type", length = 50)
    private String exceptionType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "calendar_id", nullable = false)   // AVOIR_EXCEPTION
    private ServiceCalendar calendar;

    public ServiceException() {}

    public Long getExceptionId() { return exceptionId; }
    public void setExceptionId(Long exceptionId) { this.exceptionId = exceptionId; }

    public LocalDate getExceptionDate() { return exceptionDate; }
    public void setExceptionDate(LocalDate exceptionDate) { this.exceptionDate = exceptionDate; }

    public String getExceptionType() { return exceptionType; }
    public void setExceptionType(String exceptionType) { this.exceptionType = exceptionType; }

    public ServiceCalendar getCalendar() { return calendar; }
    public void setCalendar(ServiceCalendar calendar) { this.calendar = calendar; }
}
