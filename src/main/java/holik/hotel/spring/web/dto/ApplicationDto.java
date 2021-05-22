package holik.hotel.spring.web.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class ApplicationDto {
    private int space;
    private int roomClassId;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime arrival;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime leaving;

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public int getRoomClassId() {
        return roomClassId;
    }

    public void setRoomClassId(int roomClassId) {
        this.roomClassId = roomClassId;
    }

    public LocalDateTime getArrival() {
        return arrival;
    }

    public void setArrival(LocalDateTime arrival) {
        this.arrival = arrival;
    }

    public LocalDateTime getLeaving() {
        return leaving;
    }

    public void setLeaving(LocalDateTime leaving) {
        this.leaving = leaving;
    }
}
