package mk.finki.ukim.mk.lab.repository.impl;

import mk.finki.ukim.mk.lab.bootstrap.DataHolder;
import mk.finki.ukim.mk.lab.model.Location;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class LocationRepository {
    private List<Location> locations = new ArrayList<>(5);

    public List<Location> findAll() {
        return this.locations;
    }
    public Optional<Location> findById(Long id) {
        return this.locations.stream().filter(r-> r.getId().equals(id)).findFirst();
    }

    public Optional<Location> findByName(String name) {
        return this.locations.stream().filter(r-> r.getName().equals(name)).findFirst();
    }

    public Optional<Location> save(String name, String address, String capacity, String description) {
        locations.removeIf(r-> r.getName().equals(name));
        Location location = new Location(name, address, capacity, description);
        locations.add(location);
        return Optional.of(location);
    }

    public void deleteById(Long id) {
        this.locations.removeIf(r-> r.getId().equals(id));
    }
}
