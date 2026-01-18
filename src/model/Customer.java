package model;

import exception.InvalidInputException;

public class Customer implements Discountable {
    private int customerId;
    private String name;
    private String preferredSize;
    private int points;

    public Customer(int customerId, String name, String preferredSize, int points) {
        setCustomerId(customerId);
        setName(name);
        setPreferredSize(preferredSize);
        setPoints(points);
    }

    public Customer() {
        this.customerId = 0;
        this.name = "Unknown Customer";
        this.preferredSize = "N/A";
        this.points = 0;
    }

    public int getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getPreferredSize() { return preferredSize; }
    public int getPoints() { return points; }

    public void setCustomerId(int customerId) {
        if (customerId < 0) {
            throw new InvalidInputException("Customer ID must be >= 0");
        }
        this.customerId = customerId;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Customer name cannot be empty");
        }
        this.name = name.trim();
    }

    public void setPreferredSize(String preferredSize) {
        if (preferredSize == null || preferredSize.trim().isEmpty()) {
            throw new InvalidInputException("Preferred size cannot be empty");
        }
        this.preferredSize = preferredSize.trim();
    }

    public void setPoints(int points) {
        if (points < 0) {
            throw new InvalidInputException("Points must be >= 0");
        }
        this.points = points;
    }

    public void addPoints(int points) {
        if (points <= 0) {
            throw new InvalidInputException("Points must be positive");
        }
        this.points += points;
    }

    /**
     * Week 6 interface implementation.
     * VIP customers (points > 100) get 10% off, others get 0%.
     */
    @Override
    public double getDiscountRate() {
        return isVIP() ? 0.10 : 0.0;
    }

    public boolean isVIP() {
        return this.points > 100;
    }

    public String getProfile() {
        return "Customer#" + customerId + " " + name +
                " | preferredSize=" + preferredSize +
                " | points=" + points;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", preferredSize='" + preferredSize + '\'' +
                ", points=" + points +
                '}';
    }
}
