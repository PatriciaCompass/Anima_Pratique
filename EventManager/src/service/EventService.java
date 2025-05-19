package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import models.Event;

public class EventService {
    private List<Event> events = new ArrayList<>();

    public void addEvent(Event event) {
        events.add(event);
    }

    public List<Event> getAllEvents() {
        return events;
    }

    public List<Event> getUpcomingEvents() {
        return events.stream()
                .filter(e -> e.getTime().isAfter(LocalDateTime.now()))
                .sorted(Comparator.comparing(Event::getTime))
                .collect(Collectors.toList());
    }

    public List<Event> getPastEvents() {
        return events.stream()
                .filter(Event::isPast)
                .collect(Collectors.toList());
    }

    public List<Event> getOngoingEvents() {
        return events.stream()
                .filter(Event::isOngoing)
                .collect(Collectors.toList());
    }

    public Event getEventByIndex(int index) {
        if (index >= 0 && index < events.size()) {
            return events.get(index);
        }
        return null;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
