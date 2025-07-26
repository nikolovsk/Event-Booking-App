package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.service.EventService;
import mk.finki.ukim.mk.lab.service.LocationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public String getLocationsPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Location> locations = this.locationService.findAll();
        model.addAttribute("locations", locations);
        model.addAttribute("bodyContent", "listLocations");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveLocation(@RequestParam String name,
                            @RequestParam String address,
                            @RequestParam String capacity,
                            @RequestParam String description) {

        this.locationService.save(name, address, capacity, description);
        return "redirect:/locations";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteLocation(@PathVariable Long id) {
        this.locationService.deleteById(id);
        return "redirect:/locations";
    }


    @GetMapping("/add-form")
    public String addLocationPage(Model model) {
        List<Location> locations = this.locationService.findAll();
        model.addAttribute("locations", locations);
        return "add-location";
    }
}
