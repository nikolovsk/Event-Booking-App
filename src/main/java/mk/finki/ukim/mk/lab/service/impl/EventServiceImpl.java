package mk.finki.ukim.mk.lab.service.impl;

import jakarta.transaction.Transactional;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.model.exceptions.LocationNotFoundException;
import mk.finki.ukim.mk.lab.repository.jpa.EventRepositoryNewImpl;
import mk.finki.ukim.mk.lab.repository.jpa.LocationRepositoryNewImpl;
import mk.finki.ukim.mk.lab.service.EventService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EventServiceImpl implements EventService {
    private final EventRepositoryNewImpl eventRepositoryNewImpl;
    private final LocationRepositoryNewImpl locationRepositoryNewImpl;

    public EventServiceImpl(EventRepositoryNewImpl eventRepositoryNewImpl,
                            LocationRepositoryNewImpl locationRepositoryNewImpl) {
        this.eventRepositoryNewImpl = eventRepositoryNewImpl;
        this.locationRepositoryNewImpl = locationRepositoryNewImpl;
    }

    @Override
    public List<Event> listAll() {
        return eventRepositoryNewImpl.findAll();
    }

    @Override
    public List<Event> searchEvents(String text) {
        return eventRepositoryNewImpl
                .searchEventByNameContainingOrDescriptionContaining(text, text);
    }

    @Override
    public Optional<Event> findById(Long id) {
        return eventRepositoryNewImpl.findById(id);
    }

    @Override
    public Optional<Event> findByName(String name) {
        return eventRepositoryNewImpl.findByName(name);
    }

    @Override
    public void deleteById(Long id) {
        this.eventRepositoryNewImpl.deleteById(id);
    }

    @Override
    public Optional<Event> save(String name, String description, Double popularityScore, Long locationId, LocalDate date) {
        Location location = this.locationRepositoryNewImpl.findById(locationId)
                .orElseThrow(()-> new LocationNotFoundException(locationId));

        if (this.eventRepositoryNewImpl.findByName(name).isPresent()) {
            this.eventRepositoryNewImpl.deleteByName(name);
        }

        return Optional.of(this.eventRepositoryNewImpl.save(new Event(name, description, popularityScore, location, date)));
    }

}
