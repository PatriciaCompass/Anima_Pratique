package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Event {
    private String name;
    private String address;
    private Category category;
    private LocalDateTime time;
    private String description;
    private List<UUID> participants;

    public Event(String name, String address, Category category, LocalDateTime time, String description) {
        this.name = name;
        this.address = address;
        this.category = category;
        this.time = time;
        this.description = description;
        this.participants = new ArrayList<>();
    }

    public void addParticipant(UUID userId) {
        if (!participants.contains(userId)) {
            participants.add(userId);
        }
    }

    public void removeParticipant(UUID userId) {
        participants.remove(userId);
    }

    public boolean isOngoing() {
        LocalDateTime now = LocalDateTime.now();
        return time.isAfter(now.minusHours(1)) && time.isBefore(now.plusHours(2));
    }

    public boolean isPast() {
        return time.isBefore(LocalDateTime.now());
    }

    public String getName() { return name; }
    public String getAddress() { return address; }
    public Category getCategory() { return category; }
    public LocalDateTime getTime() { return time; }
    public String getDescription() { return description; }
    public List<UUID> getParticipants() { return participants; }

    @Override
    public String toString() {
        return name + " | " + time + " | " + category + " | " + address;
    }
}
