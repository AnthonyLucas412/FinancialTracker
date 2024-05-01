package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Transaction {
    private LocalDate date;
    private LocalTime time;
    private String type;
    private String vendor;
    private double price;

    public Transaction(LocalDate date, LocalTime time, String vendor, String type, double price) {
        this.date = date;
        this.time = time;
        this.vendor = vendor;
        this.type = type;
        this.price = price;
    }

    public Transaction(LocalDate date, LocalTime time, String vendor){
        this.date = date;
        this.time = time;
        this.vendor = vendor;
        this.price = 0.0;

    }

    public  Transaction(LocalDateTime dateTime, String vendor){
        this.date = dateTime.toLocalDate();
        this.time = dateTime.toLocalTime();
        this.vendor = vendor;
        this.price = 0.0;

    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public String getVendor() {
        return vendor;
    }

    public double getPrice() {
        return price;
    }
}



