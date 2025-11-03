package com.example.notes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)  // Muchas notas -> un usuario
    @JoinColumn(name = "user_id", nullable = false)  // FK en la tabla notes
    @JsonIgnore // ✅ Evita el error de serialización al devolver la nota
    private AppUser user;

    public Note() {}

    public Note(String title, String description, boolean completed, AppUser user) {
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.user = user;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}
