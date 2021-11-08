package com.example.project_java2;

import model.State;

import java.time.LocalDate;

public class Film {

    private String title, description, image, genre, cast;
    private int id, genre_id;
    private int availability = 3; // availability count e.g. 3 pieces
    private double price;
    private LocalDate release_date;
    private int borrowed;

    public Film(String title, String description, String image, int id, int availability, double price, LocalDate release_date, String genre) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.id = id;
        this.availability = availability;
        this.price = price;
        this.release_date = release_date;
        this.genre = genre;
    }

    public Film(int genre_id, String title, String description, LocalDate release_date, int availability) {
        this.title = title;
        this.description = description;
        this.availability = availability;
        this.release_date = release_date;
        this.genre_id = genre_id;
    }

    public Film() {
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getRelease_date() {
        return release_date;
    }

    public void setRelease_date(LocalDate release_date) {
        this.release_date = release_date;
    }

    public boolean isBorrowable() {
        return this.availability > 0;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(int borrowed) {
        this.borrowed = borrowed;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", id=" + id +
                ", availability=" + availability +
                ", price=" + price +
                ", release_date=" + release_date +
                '}';
    }
}
