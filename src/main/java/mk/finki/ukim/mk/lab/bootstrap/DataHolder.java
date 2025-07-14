package mk.finki.ukim.mk.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.model.SavedBooking;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.repository.impl.LocationRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Event> events = new ArrayList<>();
    public static List<SavedBooking> savedBookings = new ArrayList<>();
    public static List<User> users = new ArrayList<>();
    private final LocationRepository locationRepository;


    public DataHolder(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    /*@PostConstruct
    public void init() {
        savedBookings.add(new SavedBooking("Petko Petkov", "Oppenheimer", 2));

        Location l1 = new Location("Location 1", "3041 Walnut Street", "170", "Location 1 description...");
        Location l2 = new Location("Location 2", "2358 Collins Street", "910", "Location 2 description...");
        Location l3 = new Location("Location 3", "3312 Black Stallion Road", "511", "Location 3 description...");
        Location l4 = new Location("Location 4", "2089 Coburn Hollow Road", "420", "Location 4 description...");

        this.locationRepository.findAll().add(l1);
        this.locationRepository.findAll().add(l2);
        this.locationRepository.findAll().add(l3);
        this.locationRepository.findAll().add(l4);

        Event event1 = new Event("Event 1", "Event 1 description...", 7, l1);
        Event event2 = new Event("Event 2", "Event 2 description...", 5, l2);
        Event event3 = new Event("Event 3", "Event 3 description...", 4, l3);
        Event event4 = new Event("Event 4", "Event 4 description...", 1, l4);

        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
    }*/
}
