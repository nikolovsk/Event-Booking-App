package mk.finki.ukim.mk.lab.model;

import lombok.Data;

@Data
public class SavedBooking {
    private String attendeeName;
    private String eventName;
    private int numberOfTickets;

    public SavedBooking(String attendeeName, String eventName, int numberOfTickets) {
        this.attendeeName = attendeeName;
        this.eventName = eventName;
        this.numberOfTickets = numberOfTickets;
    }
}
