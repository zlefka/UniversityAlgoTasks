package org.example.billionaires;

public class Billionaire {
    private String city;
    private final long wealth;
    private final String name;

    public Billionaire(String city, long wealth, String name) {
        this.city = city;
        this.wealth = wealth;
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getWealth() {
        return wealth;
    }

    public String getName() {
        return name;
    }
}
