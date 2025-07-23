package mk.finki.ukim.mk.lab.service.impl;

import jakarta.transaction.Transactional;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.model.exceptions.LocationNotFoundException;
import mk.finki.ukim.mk.lab.repository.jpa.EventRepositoryNewImpl;
import mk.finki.ukim.mk.lab.repository.jpa.LocationRepositoryNewImpl;
import mk.finki.ukim.mk.lab.service.EventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static mk.finki.ukim.mk.lab.service.specifications.FieldFilterSpecification.*;


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

    @Override
    public Page<Event> findPage(String name, Long locationId, Double minRating, Integer pageNum, Integer pageSize) {
        Specification<Event> specification = Specification
                .where(filterContainsText(Event.class, "name", name))
                .and(filterEquals(Event.class, "location.id", locationId))
                .and(greaterThan(Event.class, "popularityScore", minRating));

        return this.eventRepositoryNewImpl.findAll(
                specification,
                PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "name"))
        );

    }

}
