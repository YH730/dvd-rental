package com.example.project_java2;

import java.time.LocalDate;

public class RentalInfo {

    private Customer customer;
    private Film film;
    private LocalDate last_visit;
    private LocalDate return_date;
    private int qty, id;
    private int availableQty;

    public RentalInfo() {
        this.customer = new Customer();
        this.film = new Film();
    }
    public RentalInfo(int customerId,int filmId, LocalDate last_visit) {
        this.customer = new Customer();
        this.film = new Film();
        this.customer.setId(customerId);
        this.last_visit = last_visit;
        this.film.setId(filmId);

    }

    public RentalInfo(Customer customer, Film film, LocalDate last_visit, LocalDate return_date) {
        this.customer = customer;
        this.film = film;
        this.last_visit = last_visit;
        this.return_date = return_date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getCustomerId() {
        return this.customer.getId();
    }

    public void setCustomerId(int customerId) {
        this.customer.setId(customerId);
    }

    public String getCustomerName() {
        return customer.getLastName();
    }

    public void setCustomerName(String customerName) {
        this.customer.setLastName(customerName);
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public String getFilmTitle() {
        return this.film.getTitle();
    }

    public void setFilmTitle(String film) {
        this.film.setTitle(film);
    }

    public int getFilmId() {
        return this.film.getId();
    }

    public void setFilmId(int filmId) {
        this.film.setId(filmId);
    }

    public LocalDate getLast_visit() {
        return last_visit;
    }

    public void setLast_visit(LocalDate last_visit) {
        this.last_visit = last_visit;
    }

    public LocalDate getReturn_date() {
        return return_date;
    }

    public void setReturn_date(LocalDate return_date) {
        this.return_date = return_date;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(int availableQty) {
        this.availableQty = availableQty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RentalInfo{" +
                "customer=" + customer +
                ", film=" + film +
                ", last_visit=" + last_visit +
                ", return_date=" + return_date +
                ", qty=" + qty +
                ", id=" + id +
                ", availableQty=" + availableQty +
                '}';
    }
}
