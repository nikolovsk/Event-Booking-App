package mk.finki.ukim.mk.lab.model;

import jakarta.persistence.*;
import lombok.Data;

import java.text.DateFormat;
import java.time.LocalDate;

@Data
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private double popularityScore;

    private LocalDate date;

    @ManyToOne
    private Location location;

    public Event(String name, String description, double popularityScore, Location location, LocalDate date) {
        this.name = name;
        this.description = description;
        this.popularityScore = popularityScore;
        this.location = location;
        this.date = date;
    }

    public Event() {

    }
}
