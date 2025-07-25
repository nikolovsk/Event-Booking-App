package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Event;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> listAll();
    List<Event> searchEvents(String text);
    Optional<Event> findById(Long id);

    Optional<Event> findByName(String name);

    void deleteById(Long id);
    Optional<Event> save(String name, String description, Double popularityScore, Long locationId, LocalDate date);
    Page<Event> findPage(String name, Long locationId, Double minRating, Integer pageNum, Integer pageSize);
}
