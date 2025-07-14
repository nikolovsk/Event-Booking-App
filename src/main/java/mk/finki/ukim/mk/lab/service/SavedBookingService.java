package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.SavedBooking;

import java.util.List;
import java.util.Optional;

public interface SavedBookingService {
    public List<SavedBooking> findAll();
    public List<SavedBooking> findByName(String name);
    public Optional<SavedBooking> save(String attendeeName, String eventName, int numberOfTickets);
}
