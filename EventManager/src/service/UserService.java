package service;

import models.Event;
import models.User;

public class UserService {
    private User user;

    public void registerUser(String name, String email, String city) {
        this.user = new User(name, email,city);
    }

    public User getUser() {
        return user;
    }

    public void confirmParticipation(Event event) {
        if (!event.getParticipants().contains(user.getId())) {
            event.addParticipant(user.getId());
            user.getConfirmedEvents().add(event);
        }
    }

    public void cancelParticipation(Event event) {
        event.removeParticipant(user.getId());
        user.getConfirmedEvents().remove(event);
    }
}
