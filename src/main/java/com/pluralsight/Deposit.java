package com.pluralsight;


import java.time.LocalDateTime;

public class Deposit extends Transaction {
    private LocalDateTime dateTime;
    private String vendor;
    private String type;
    private double price;

    public Deposit(LocalDateTime dateTime, String vendor, String type, double price) {
        super(dateTime, vendor);
        this.price = price;
        this.vendor = vendor;
        this.type = type;
    }

    @Override
    public String getType() {
        return type;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String getVendor() {
        return vendor;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "dateTime=" + dateTime +
                ", vendor='" + vendor + '\'' +
                ", price=" + price +
                '}';
    }
}

