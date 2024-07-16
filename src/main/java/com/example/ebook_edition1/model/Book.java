package com.example.ebook_edition1.model;

import jakarta.persistence.*;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(nullable = false, length = 1000)
    private String thumbnail;

    @Column(nullable = false, length = 2000)
    private String textSnippet;

    @Column(nullable = false)
    private Boolean availability = true;

    // Getters and Setters

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null && description.length() > 2000) {
            this.description = description.substring(0, 2000);
        } else {
            this.description = description;
        }
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        if (thumbnail != null && thumbnail.length() > 1000) {
            this.thumbnail = thumbnail.substring(0, 1000);
        } else {
            this.thumbnail = thumbnail;
        }
    }

    public String getTextSnippet() {
        return textSnippet;
    }

    public void setTextSnippet(String textSnippet) {
        if (textSnippet != null && textSnippet.length() > 2000) {
            this.textSnippet = textSnippet.substring(0, 2000);
        } else {
            this.textSnippet = textSnippet;
        }
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }
}
