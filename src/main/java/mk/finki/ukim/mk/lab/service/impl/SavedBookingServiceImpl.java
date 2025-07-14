package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.SavedBooking;
import mk.finki.ukim.mk.lab.repository.impl.SavedBookingRepository;
import mk.finki.ukim.mk.lab.service.SavedBookingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SavedBookingServiceImpl implements SavedBookingService {
    private final SavedBookingRepository savedBookingRepository;

    public SavedBookingServiceImpl(SavedBookingRepository savedBookingRepository) {
        this.savedBookingRepository = savedBookingRepository;
    }

    @Override
    public List<SavedBooking> findAll() {
        return this.savedBookingRepository.findAll();
    }

    @Override
    public List<SavedBooking> findByName(String name) {
        return this.savedBookingRepository.findByName(name);
    }

    @Override
    public Optional<SavedBooking> save(String attendeeName, String eventName, int numberOfTickets) {
        return this.savedBookingRepository.save(attendeeName, eventName, numberOfTickets);
    }
}
