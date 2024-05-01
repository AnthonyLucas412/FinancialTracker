package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Payment extends Transaction {
    private LocalDateTime dateTime;
    private String vendor;
    private String type;
    private double price;

    public Payment(LocalDateTime dateTime, String vendor,String type, double price) {
        super(dateTime.toLocalDate(), dateTime.toLocalTime(), vendor);
        this.dateTime = dateTime;
        this.vendor = vendor;
        this.type = type;
        this.price = -price;
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

    public double getPrice() {
        return price;
    }
}
