package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.EventBooking;
import mk.finki.ukim.mk.lab.repository.impl.EventRepository;
import mk.finki.ukim.mk.lab.service.EventBookingService;
import org.springframework.stereotype.Service;

@Service
public class EventBookingServiceImpl implements EventBookingService {

    private final EventRepository eventRepository;

    public EventBookingServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public EventBooking placeBooking(String eventName, String attendeeName, String attendeeAddress,
                                     int numberOfTickets) {

        EventBooking eventBooking = null;

        if (eventRepository.findAll().contains(eventName)) {
            if ((attendeeName != null && !attendeeName.isEmpty()) &&
                    (attendeeAddress != null && !attendeeAddress.isEmpty()) && (numberOfTickets > 0)) {
                eventBooking = new EventBooking(eventName, attendeeName, attendeeAddress,
                        (long) numberOfTickets);
            }
        }

        return eventBooking;
    }
}
