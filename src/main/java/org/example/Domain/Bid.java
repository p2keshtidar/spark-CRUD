package org.example.Domain;

public class Bid {
    private String biderEmail;
    private int price;
    private String description;
    private int months;
    private int weeks;
    private int days;

    public Bid(String biderEmail, int price, String description, int months, int weeks, int days) {
        this.biderEmail = biderEmail;
        this.price = price;
        this.description = description;
        this.months = months;
        this.weeks = weeks;
        this.days = days;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBiderEmail() {
        return biderEmail;
    }

    public void setBiderEmail(String biderEmail) {
        this.biderEmail = biderEmail;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public int getWeeks() {
        return weeks;
    }

    public void setWeeks(int weeks) {
        this.weeks = weeks;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
