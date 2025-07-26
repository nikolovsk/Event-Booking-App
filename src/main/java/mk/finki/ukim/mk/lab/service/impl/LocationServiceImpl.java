package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.repository.jpa.EventRepositoryNewImpl;
import mk.finki.ukim.mk.lab.repository.jpa.LocationRepositoryNewImpl;
import mk.finki.ukim.mk.lab.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepositoryNewImpl locationRepositoryNewImpl;
    private final EventRepositoryNewImpl eventRepository;

    public LocationServiceImpl(LocationRepositoryNewImpl locationRepositoryNewImpl,
                               EventRepositoryNewImpl eventRepository) {
        this.locationRepositoryNewImpl = locationRepositoryNewImpl;
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Location> findAll() {
        return this.locationRepositoryNewImpl.findAll();
    }

    @Override
    public Optional<Location> findById(Long id) {
        return this.locationRepositoryNewImpl.findById(id);
    }

    @Override
    public Optional<Location> findByName(String name) {
        return this.locationRepositoryNewImpl.findByName(name);
    }

    @Override
    public Optional<Location> save(String name, String address, String capacity, String description) {
        return Optional.of(this.locationRepositoryNewImpl.save(new Location(name, address, capacity, description)));
    }

    @Override
    public void deleteById(Long id) {
        List<Event> events = eventRepository.findAllByLocationId(id);
        for (Event event : events) {
            event.setLocation(null);
        }
        eventRepository.saveAll(events);

        this.locationRepositoryNewImpl.deleteById(id);
    }
}
