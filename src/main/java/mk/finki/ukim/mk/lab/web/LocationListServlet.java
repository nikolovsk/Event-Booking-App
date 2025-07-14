package mk.finki.ukim.mk.lab.web;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.service.LocationService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "LocationListServlet", urlPatterns = "/listLocations")
public class LocationListServlet extends HttpServlet {

    private final SpringTemplateEngine springTemplateEngine;
    private final LocationService locationService;

    public LocationListServlet(SpringTemplateEngine springTemplateEngine, LocationService locationService) {
        this.springTemplateEngine = springTemplateEngine;
        this.locationService = locationService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange iWebExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req,resp);
        WebContext context = new WebContext(iWebExchange);

        List<Location> locationList;
        locationList = this.locationService.findAll();

        context.setVariable("locations", locationList);
        this.springTemplateEngine.process("listLocations.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange iWebExchange = JakartaServletWebApplication
                .buildApplication(req.getServletContext())
                .buildExchange(req, resp);
        WebContext context = new WebContext(iWebExchange);

        String locationName = req.getParameter("loc");
        String address = req.getParameter("address");
        String capacity = req.getParameter("capacity");
        String description = req.getParameter("description");


        this.locationService.save(locationName, address, capacity, description);

        context.setVariable("location", locationName);
        context.setVariable("address", address);
        context.setVariable("capacity", capacity);
        context.setVariable("description", description);

        springTemplateEngine.process("listLocations.html", context, resp.getWriter());
    }
}
