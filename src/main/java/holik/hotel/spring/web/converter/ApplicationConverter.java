package holik.hotel.spring.web.converter;

import holik.hotel.spring.persistence.model.Application;
import holik.hotel.spring.persistence.model.ApplicationStatus;
import holik.hotel.spring.persistence.model.Room;
import holik.hotel.spring.persistence.model.RoomClass;
import holik.hotel.spring.web.dto.ApplicationDto;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConverter implements Converter<ApplicationDto, Application> {

    @Override
    public Application convertToEntity(ApplicationDto applicationDto) {
        Application application = new Application();
        application.setUser(applicationDto.getUser());
        application.setSpace(applicationDto.getSpace());
        application.setArrival(applicationDto.getArrival());
        application.setLeaving(applicationDto.getLeaving());

        Room defaultRoom = new Room();
        defaultRoom.setId(1);
        application.setRoom(defaultRoom);

        ApplicationStatus status = new ApplicationStatus();
        status.setId(1);
        application.setStatus(status);

        RoomClass roomClass = new RoomClass();
        roomClass.setId(applicationDto.getRoomClassId());
        application.setRoomClass(roomClass);
        return application;
    }
}
