package com.example.fables.data.model;

public class Fable {
    private String title;
    private String content;
    private String author;

    public Fable(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent(){
        return this.content;
    }

    public String getAuthor() {
        return author;
    }
    // Getters and setters for title, content, and author

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
