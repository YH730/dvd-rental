package com.example.project_java2;

public enum Membership {

    Gold("Gold"),
    Silver("Silver"),
    Bronze("Bronze");

    private String status;
    Membership(String status){
        this.status = status;
    }

    public String toString(){
        return status;
    }
}

