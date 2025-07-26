package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.service.EventService;
import mk.finki.ukim.mk.lab.service.LocationService;
import mk.finki.ukim.mk.lab.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping({"/", "/events"})
public class EventController {
    private final EventService eventService;
    private final LocationService locationService;
    private final UserService userService;

    public EventController(EventService eventService, LocationService locationService, UserService userService) {
        this.eventService = eventService;
        this.locationService = locationService;
        this.userService = userService;
    }

    @GetMapping
    public String getEventsPage(
            @RequestParam(required = false) String error,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long locationId,
            @RequestParam(required = false) Double minRating,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            Model model, Principal principal
    ) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        Page<Event> page = this.eventService.findPage(name, locationId, minRating, pageNum, pageSize);
        String username = principal.getName();
        User user = userService.loadUserByUsername(username);
        model.addAttribute("currentUser", user);

        model.addAttribute("page", page);
        model.addAttribute("locations", this.locationService.findAll());
        model.addAttribute("name", name);
        model.addAttribute("locationId", locationId);
        model.addAttribute("minRating", minRating);

        model.addAttribute("bodyContent", "listEvents");
        return "master-template";
    }

    @GetMapping("/access_denied")
    public String accessDeniedPage(Model model) {
        model.addAttribute("bodyContent", "access-denied");
        return "master-template";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveEvent(@RequestParam String name,
                            @RequestParam String description,
                            @RequestParam Double popularityScore,
                            @RequestParam Long location,
                            @RequestParam LocalDate date) {

        this.eventService.save(name, description, popularityScore, location, date);
        return "redirect:/events";
    }

    @GetMapping("/edit-form/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editEvent(@PathVariable Long id,
                            Model model) {
        if (this.eventService.findById(id).isPresent()) {
            Event event = this.eventService.findById(id).get();
            List<Location> locations = this.locationService.findAll();

            model.addAttribute("event", event);
            model.addAttribute("locations", locations);

            return "add-event";
        }

        return "redirect:/events?error=EventNotFound";
    }

    @DeleteMapping ("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteEvent(@PathVariable Long id) {
        this.eventService.deleteById(id);
        return "redirect:/events";
    }


    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ADMIN')")
    public String addEventPage(Model model) {
        List<Location> locations = this.locationService.findAll();
        model.addAttribute("locations", locations);
        return "add-event";
    }
}
