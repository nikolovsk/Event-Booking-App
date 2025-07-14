package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Location;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    public List<Location> findAll();
    public Optional<Location> findById(Long id);
    public Optional<Location> findByName(String name);
    public Optional<Location> save (String name, String address, String capacity, String description);
    public void deleteById(Long id);
}
