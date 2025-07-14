package mk.finki.ukim.mk.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.service.EventService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "EventListServlet", urlPatterns = "")
public class EventListServlet extends HttpServlet {

    private final SpringTemplateEngine springTemplateEngine;
    private final EventService eventService;

    public EventListServlet(SpringTemplateEngine springTemplateEngine, EventService eventService) {
        this.springTemplateEngine = springTemplateEngine;
        this.eventService = eventService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        IWebExchange iWebExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req,resp);
        WebContext context = new WebContext(iWebExchange);

        String searchName = req.getParameter("searchName");
        String minRating = req.getParameter("minRating");

        List<Event> eventList;
        if (searchName != null && !searchName.isEmpty() && minRating != null && !Objects.equals(minRating, "")) {
            eventList = this.eventService.searchEvents(searchName).stream().filter((event) -> {
                return event.getPopularityScore() == Double.parseDouble(minRating);
            }).toList();
        } else if (minRating != null && !Objects.equals(minRating, "")) {
            eventList = this.eventService.listAll().stream().filter((event) -> {
                return event.getPopularityScore() == Double.parseDouble(minRating);
            }).toList();
        } else if (searchName != null && !searchName.isEmpty()) {
            eventList = this.eventService.searchEvents(searchName);
        } else {
            eventList = this.eventService.listAll();
        }

        context.setVariable("events", eventList);

        this.springTemplateEngine.process("listEvents.html", context, resp.getWriter());
    }
}
