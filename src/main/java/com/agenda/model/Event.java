package com.agenda.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.Objects;

public class Event {
    private String id;
    private String title;
    private LocalDate date;
    private String location;
    private String relatedContact;
    private String description;
    
    @JsonCreator
    public Event(@JsonProperty("id") String id,
                 @JsonProperty("title") String title,
                 @JsonProperty("date") LocalDate date,
                 @JsonProperty("location") String location,
                 @JsonProperty("relatedContact") String relatedContact,
                 @JsonProperty("description") String description) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.location = location;
        this.relatedContact = relatedContact;
        this.description = description;
    }
    
    // Construtor para novo evento
    public Event(String title, LocalDate date, String location, 
                 String relatedContact, String description) {
        this.id = generateId();
        this.title = title;
        this.date = date;
        this.location = location;
        this.relatedContact = relatedContact;
        this.description = description;
    }
    
    private String generateId() {
        return "E" + System.currentTimeMillis();
    }
    
    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public String getRelatedContact() { return relatedContact; }
    public void setRelatedContact(String relatedContact) { this.relatedContact = relatedContact; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return title + " - " + date.toString();
    }
}
