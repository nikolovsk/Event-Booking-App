package mk.finki.ukim.mk.lab.repository.jpa;

import mk.finki.ukim.mk.lab.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepositoryNewImpl extends JpaSpecificationRepository<Event, Long> {
    List<Event> searchEventByNameContainingOrDescriptionContaining(String name, String description);
    Optional<Event> findByName(String name);
    void deleteByName(String name);

    void deleteById(Long id);
}
