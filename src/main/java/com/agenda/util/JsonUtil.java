package com.agenda.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();
    
    static {
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    
    public static <T> void saveToFile(List<T> data, String filename) {
        try {
            File file = new File(filename);
            // Garante que o diretório existe
            file.getParentFile().mkdirs();
            mapper.writeValue(file, data);
        } catch (IOException e) {
            System.err.println("Erro ao salvar arquivo JSON: " + filename);
            e.printStackTrace();
        }
    }
    
    public static <T> List<T> loadFromFile(String filename, Class<T> clazz) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                return new java.util.ArrayList<>();
            }
            return mapper.readValue(file, 
                mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            System.err.println("Erro ao carregar arquivo JSON: " + filename);
            return new java.util.ArrayList<>();
        }
    }
}
