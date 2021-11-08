package com.example.project_java2;

import java.time.LocalDate;

public class Customer {

    private int id, membershipStatusid, borrowed_film_id;
    private String firstName, lastName, eMail, borrowed_film, membershipStatus;
    private LocalDate last_visit_date;
    //private Membership status;


    // TODO: clean up
    public Customer(int id, int membershipStatusid, int borrowed_film_id, String firstName, String lastName, String eMail, String membershipStatus, LocalDate last_visit_date, Double balance, Double price, int numberOfBorrowFilm) {
        this.id = id;
        this.membershipStatusid = membershipStatusid;
        this.borrowed_film_id = borrowed_film_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.membershipStatus = membershipStatus;
        this.last_visit_date = last_visit_date;
        //this.status = status;

    }

    public Customer(int id, String lastName) {
        this.id = id;
        this.lastName = lastName;
    }

    public Customer(String firstName, String lastName, String eMail, String membershipStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.membershipStatus = membershipStatus;
    }

    public Customer(int id) {
        this.id = id;
    }

    public Customer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMembershipStatusid() {
        return membershipStatusid;
    }

    public void setMembershipStatusid(int membershipStatusid) {
        this.membershipStatusid = membershipStatusid;
    }

    public int getBorrowed_film_id() {
        return borrowed_film_id;
    }

    public void setBorrowed_film_id(int borrowed_film_id) {
        this.borrowed_film_id = borrowed_film_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getMembershipStatus() {return membershipStatus;}

   public void setMembershipStatus(String membershipStatus) {this.membershipStatus = membershipStatus;}

    public LocalDate getLast_visit_date() {
        return last_visit_date;
    }

    public void setLast_visit_date(LocalDate last_visit_date) {
        this.last_visit_date = last_visit_date;
    }

    //public Membership getStatus() {return status;}

    //public void setStatus(Membership status) {this.status = status;}

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", membershipStatusid=" + membershipStatusid +
                ", borrowed_film_id=" + borrowed_film_id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", eMail='" + eMail + '\'' +
               // ", membershipStatus='" + membershipStatus + '\'' +
                ", last_visit_date=" + last_visit_date +
                '}';
    }

    public String getBorrowed_film() {
        return borrowed_film;
    }

    public void setBorrowed_film(String titel) {
    }

    public void setPrice(double preis) {
    }
}
