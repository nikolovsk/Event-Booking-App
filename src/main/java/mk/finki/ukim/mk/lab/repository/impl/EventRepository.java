package mk.finki.ukim.mk.lab.repository.impl;

import mk.finki.ukim.mk.lab.bootstrap.DataHolder;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EventRepository {

    public List<Event> findAll() {
        return DataHolder.events.stream().collect(Collectors.toList());
    }

    public List<Event> searchEvents(String text) {
        return DataHolder.events.stream().filter(r-> r.getName().contains(text) ||
                r.getDescription().contains(text)).collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        DataHolder.events.removeIf(r-> r.getId().equals(id));
    }

    public Optional<Event> findById(Long id) {
        return DataHolder.events.stream().filter(r-> r.getId().equals(id)).findFirst();
    }

    public Optional<Event> save (String name, String description, Double popularityScore, Location location) {
        DataHolder.events.removeIf(r-> r.getName().equals(name));
        Event event = new Event(name, description, popularityScore, location);
        DataHolder.events.add(event);
        return Optional.of(event);
    }


}
