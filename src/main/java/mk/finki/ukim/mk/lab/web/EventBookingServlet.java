package mk.finki.ukim.mk.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.SavedBooking;
import mk.finki.ukim.mk.lab.service.EventService;
import mk.finki.ukim.mk.lab.service.SavedBookingService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "EventBookingServlet", urlPatterns = "/eventBooking")
public class EventBookingServlet extends HttpServlet {

    private final SpringTemplateEngine springTemplateEngine;
    private final EventService eventService;
    private final SavedBookingService savedBookingService;

    public EventBookingServlet(SpringTemplateEngine springTemplateEngine,
                               EventService eventService,
                               SavedBookingService savedBookingService) {
        this.springTemplateEngine = springTemplateEngine;
        this.eventService = eventService;
        this.savedBookingService = savedBookingService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        IWebExchange iWebExchange = JakartaServletWebApplication
                .buildApplication(req.getServletContext())
                .buildExchange(req, resp);
        WebContext context = new WebContext(iWebExchange);

        String bookingSearch = req.getParameter("bookingSearch");
        List<SavedBooking> bookingsToSend = this.savedBookingService.findAll().stream().filter((booking) ->
            booking.getAttendeeName().toLowerCase().contains(bookingSearch.toLowerCase())).collect(Collectors.toList());

        String hostname = bookingsToSend.get(0).getAttendeeName();
        String eventName = bookingsToSend.get(0).getEventName();
        Integer numOfTickets = bookingsToSend.get(0).getNumberOfTickets();

        String location = "";
        List<Event> searchEvents = new ArrayList<>();
        if (eventName != null && !eventName.isEmpty()) {
            searchEvents = this.eventService.searchEvents(eventName);
        }

        if (!searchEvents.isEmpty()) {
            location = String.valueOf(searchEvents.get(0).getLocation().getName());
        }

        context.setVariable("hostName", hostname);
        context.setVariable("hostAddress", req.getRemoteAddr());
        context.setVariable("eventName", eventName);
        context.setVariable("numOfTickets", numOfTickets);
        context.setVariable("location", location);

        //context.setVariable("savedBookingsList", bookingsToSend);
        springTemplateEngine.process("bookingConfirmation.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        IWebExchange iWebExchange = JakartaServletWebApplication
                .buildApplication(req.getServletContext())
                .buildExchange(req, resp);
        WebContext context = new WebContext(iWebExchange);

        String eventName = req.getParameter("rad");
        Integer numOfTickets = Integer.parseInt(req.getParameter("numTickets"));
        String hostName = req.getParameter("hostName");
        String location = "";
        List<Event> searchEvents = new ArrayList<>();
        if (eventName != null && !eventName.isEmpty()) {
            searchEvents = this.eventService.searchEvents(eventName);
        }

        if (!searchEvents.isEmpty()) {
            location = String.valueOf(searchEvents.get(0).getLocation().getName());
        }

        this.savedBookingService.save(hostName, eventName, numOfTickets);

        context.setVariable("hostName", hostName);
        context.setVariable("hostAddress", req.getRemoteAddr());
        context.setVariable("eventName", eventName);
        context.setVariable("numOfTickets", numOfTickets);
        context.setVariable("location", location);

        springTemplateEngine.process("bookingConfirmation.html", context, resp.getWriter());
    }
}
