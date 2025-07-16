package mk.finki.ukim.mk.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.model.SavedBooking;
import mk.finki.ukim.mk.lab.service.EventService;
import mk.finki.ukim.mk.lab.service.SavedBookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/eventBookingController")
public class EventBookingController {
    private final EventService eventService;
    private final SavedBookingService savedBookingService;

    public EventBookingController(EventService eventService, SavedBookingService savedBookingService) {
        this.eventService = eventService;
        this.savedBookingService = savedBookingService;
    }

    @GetMapping
    public String getBookingConfirmationPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<SavedBooking> bookings = this.savedBookingService.findAll();
        model.addAttribute("bookings", bookings);
        model.addAttribute("bodyContent", "bookingConfirmation");
        return "master-template";
    }

    @PostMapping
    public String addReservation (HttpServletRequest req, Model model) {
        try {
            String eventName = req.getParameter("rad");
            int numOfTickets = Integer.parseInt(req.getParameter("numTickets"));
            String hostName = req.getParameter("hostName");
            Event event = this.eventService.findByName(eventName).orElseThrow();
            String location = event.getLocation().getAddress();

            this.savedBookingService.save(hostName, eventName, numOfTickets);

            model.addAttribute("hostName", hostName);
            model.addAttribute("eventName", eventName);
            model.addAttribute("numTickets", numOfTickets);
            model.addAttribute("location", location);
            model.addAttribute("clientIp", req.getRemoteAddr());
            model.addAttribute("bodyContent", "bookingConfirmation");

            return "master-template";

        } catch (RuntimeException exception) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("bodyContent", "listEvents");
            return "master-template";
        }
    }
}
