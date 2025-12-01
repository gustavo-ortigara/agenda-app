package com.agenda.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class Contact {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String category;
    private String observations;
    
    @JsonCreator
    public Contact(@JsonProperty("id") String id,
                   @JsonProperty("name") String name,
                   @JsonProperty("phone") String phone,
                   @JsonProperty("email") String email,
                   @JsonProperty("category") String category,
                   @JsonProperty("observations") String observations) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.category = category;
        this.observations = observations;
    }
    
    // Construtor para novo contato
    public Contact(String name, String phone, String email, 
                   String category, String observations) {
        this.id = generateId();
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.category = category;
        this.observations = observations;
    }
    
    private String generateId() {
        return "C" + System.currentTimeMillis();
    }
    
    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(id, contact.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return name + " (" + phone + ")";
    }
}
