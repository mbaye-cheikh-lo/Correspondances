package Projet.Correspondances.Dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BookingDto {
    public LocalDateTime bookingDate;
    public String status;
    public BigDecimal pricePaid;
    public Long travelerId;
    public Long circulationId;
    public Long seatId;
    public Long fareId;
}
