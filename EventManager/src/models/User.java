package models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private UUID id;
    private String name;
    private String email;
    private String city;
    private List<Event> confirmedEvents;

    public User(String name, String email, String city) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.city = city;
        this.confirmedEvents = new ArrayList<>();
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getCity() { return city; }
    public List<Event> getConfirmedEvents() { return confirmedEvents; }
}
