package com.agenda.dao;

import com.agenda.model.Contact;
import com.agenda.util.JsonUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContactDAO {
    private static final String CONTACTS_FILE = "data/contatos.json";
    private List<Contact> contacts;
    
    public ContactDAO() {
        loadContacts();
    }
    
    private void loadContacts() {
        contacts = JsonUtil.loadFromFile(CONTACTS_FILE, Contact.class);
    }
    
    private void saveContacts() {
        JsonUtil.saveToFile(contacts, CONTACTS_FILE);
    }
    
    public void save(Contact contact) {
        Optional<Contact> existing = contacts.stream()
            .filter(c -> c.getId().equals(contact.getId()))
            .findFirst();
            
        if (existing.isPresent()) {
            // Atualiza contato existente
            Contact toUpdate = existing.get();
            toUpdate.setName(contact.getName());
            toUpdate.setPhone(contact.getPhone());
            toUpdate.setEmail(contact.getEmail());
            toUpdate.setCategory(contact.getCategory());
            toUpdate.setObservations(contact.getObservations());
        } else {
            // Adiciona novo contato
            contacts.add(contact);
        }
        saveContacts();
    }
    
    public List<Contact> findAll() {
        return new ArrayList<>(contacts);
    }
    
    public Optional<Contact> findById(String id) {
        return contacts.stream()
            .filter(contact -> contact.getId().equals(id))
            .findFirst();
    }
    
    public void delete(String id) {
        contacts.removeIf(contact -> contact.getId().equals(id));
        saveContacts();
    }
    
    public List<String> getCategories() {
        return contacts.stream()
            .map(Contact::getCategory)
            .filter(category -> category != null && !category.isEmpty())
            .distinct()
            .collect(java.util.stream.Collectors.toList());
    }
}
