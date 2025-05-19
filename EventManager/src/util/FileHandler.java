package util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.Event;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class FileHandler {
    private static final String FILE_NAME = "events.data";

    public static void saveEvents(List<Event> events) {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            Gson gson = new Gson();
            gson.toJson(events, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Event> loadEvents() {
        try (FileReader reader = new FileReader(FILE_NAME)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Event>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            return null;
        }
    }
}
