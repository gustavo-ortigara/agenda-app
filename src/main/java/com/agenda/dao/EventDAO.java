package com.agenda.dao;

import com.agenda.model.Event;
import com.agenda.util.JsonUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventDAO {
    private static final String EVENTS_FILE = "data/eventos.json";
    private List<Event> events;
    
    public EventDAO() {
        loadEvents();
    }
    
    private void loadEvents() {
        events = JsonUtil.loadFromFile(EVENTS_FILE, Event.class);
    }
    
    private void saveEvents() {
        JsonUtil.saveToFile(events, EVENTS_FILE);
    }
    
    public void save(Event event) {
        Optional<Event> existing = events.stream()
            .filter(e -> e.getId().equals(event.getId()))
            .findFirst();
            
        if (existing.isPresent()) {
            // Atualiza evento existente
            Event toUpdate = existing.get();
            toUpdate.setTitle(event.getTitle());
            toUpdate.setDate(event.getDate());
            toUpdate.setLocation(event.getLocation());
            toUpdate.setRelatedContact(event.getRelatedContact());
            toUpdate.setDescription(event.getDescription());
        } else {
            // Adiciona novo evento
            events.add(event);
        }
        saveEvents();
    }
    
    public List<Event> findAll() {
        return new ArrayList<>(events);
    }
    
    public Optional<Event> findById(String id) {
        return events.stream()
            .filter(event -> event.getId().equals(id))
            .findFirst();
    }
    
    public void delete(String id) {
        events.removeIf(event -> event.getId().equals(id));
        saveEvents();
    }
    
    public List<Event> findByMonth(int month, int year) {
        return events.stream()
            .filter(event -> {
                LocalDate date = event.getDate();
                return date.getMonthValue() == month && date.getYear() == year;
            })
            .collect(java.util.stream.Collectors.toList());
    }
}
