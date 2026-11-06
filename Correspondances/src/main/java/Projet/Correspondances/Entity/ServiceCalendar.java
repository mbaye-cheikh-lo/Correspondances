package Projet.Correspondances.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "service_calendar")
public class ServiceCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_id")
    private Long calendarId;

    @Column(name = "calendar_type", length = 50)
    private String calendarType;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToMany(mappedBy = "calendar")
    private List<ServiceException> exceptions = new ArrayList<>();

    @OneToMany(mappedBy = "calendar")
    private List<Circulation> circulations = new ArrayList<>();

    public ServiceCalendar() {}

    public Long getCalendarId() { return calendarId; }
    public void setCalendarId(Long calendarId) { this.calendarId = calendarId; }

    public String getCalendarType() { return calendarType; }
    public void setCalendarType(String calendarType) { this.calendarType = calendarType; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public List<ServiceException> getExceptions() { return exceptions; }
    public void setExceptions(List<ServiceException> exceptions) { this.exceptions = exceptions; }

    public List<Circulation> getCirculations() { return circulations; }
    public void setCirculations(List<Circulation> circulations) { this.circulations = circulations; }
}
