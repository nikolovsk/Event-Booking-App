package mk.finki.ukim.mk.lab.repository.impl;

import mk.finki.ukim.mk.lab.bootstrap.DataHolder;
import mk.finki.ukim.mk.lab.model.SavedBooking;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class SavedBookingRepository {
    public List<SavedBooking> findAll() {
        return DataHolder.savedBookings;
    }

    public List<SavedBooking> findByName (String name) {
        return DataHolder.savedBookings.stream().filter(r-> r.getAttendeeName().contains(name))
                .collect(Collectors.toList());
    }

    public Optional<SavedBooking> save(String attendeeName, String eventName, int numberOfTickets) {
        SavedBooking savedBooking = new SavedBooking(attendeeName, eventName, numberOfTickets);
        DataHolder.savedBookings.add(savedBooking);
        return Optional.of(savedBooking);
    }
}
