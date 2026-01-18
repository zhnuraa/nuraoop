package model;

import exception.InvalidInputException;

import java.util.ArrayList;

public class Order {

    public enum Status { PENDING, COMPLETED, CANCELLED }

    private static class Line {
        private ClothingItem item;
        private int quantity;

        Line(ClothingItem item, int quantity) {
            this.item = item;
            this.quantity = (quantity <= 0) ? 1 : quantity;
        }

        ClothingItem getItem() { return item; }
        int getQuantity() { return quantity; }

        void addQuantity(int add) {
            if (add > 0) quantity += add;
        }

        double getLineTotal() {
            return item.getPrice() * quantity;
        }

        @Override
        public String toString() {
            return "Line{itemId=" + item.getItemId() +
                    ", name='" + item.getName() + '\'' +
                    ", qty=" + quantity +
                    ", unitPrice=" + item.getPrice() +
                    ", lineTotal=" + getLineTotal() + "}";
        }
    }

    private int orderId;
    private Customer customer;
    private ArrayList<Line> lines;
    private Status status;

    public Order(int orderId, Customer customer) {
        setOrderId(orderId);
        setCustomer(customer);
        this.lines = new ArrayList<Line>();
        this.status = Status.PENDING;
    }

    public Order() {
        this.orderId = 0;
        this.customer = null;
        this.lines = new ArrayList<Line>();
        this.status = Status.PENDING;
    }

    public int getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public Status getStatus() { return status; }

    public void setOrderId(int orderId) {
        if (orderId < 0) {
            throw new InvalidInputException("Order ID must be >= 0");
        }
        this.orderId = orderId;
    }

    public void setCustomer(Customer customer) {
        if (customer == null) {
            throw new InvalidInputException("Customer cannot be null");
        }
        this.customer = customer;
    }

    public boolean isPending() {
        return status == Status.PENDING;
    }

    public double calculateTotal() {
        double sum = 0.0;
        for (int i = 0; i < lines.size(); i++) {
            sum += lines.get(i).getLineTotal();
        }
        return sum;
    }

    public boolean addItem(ClothingItem item, int quantity) {
        if (status != Status.PENDING) {
            System.out.println("Cannot add items. Order is not PENDING.");
            return false;
        }
        if (customer == null) {
            System.out.println("Cannot add items without customer.");
            return false;
        }
        if (item == null) {
            System.out.println("Item cannot be null.");
            return false;
        }
        if (quantity <= 0) {
            System.out.println("Quantity must be positive.");
            return false;
        }

        // First, reduce stock (real store behavior)
        boolean reduced = item.reduceStock(quantity);
        if (!reduced) return false;

        // If item already exists in the order, just increase qty
        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);
            if (line.getItem().getItemId() == item.getItemId()) {
                line.addQuantity(quantity);
                return true;
            }
        }

        // Otherwise add a new order line
        lines.add(new Line(item, quantity));
        return true;
    }

    public void complete() {
        if (status != Status.PENDING) {
            System.out.println("Order cannot be completed. Current status: " + status);
            return;
        }
        if (customer == null) {
            System.out.println("Cannot complete order without customer.");
            return;
        }
        if (lines.isEmpty()) {
            System.out.println("Cannot complete empty order.");
            return;
        }
        status = Status.COMPLETED;
    }

    public void cancel() {
        if (status != Status.PENDING) {
            System.out.println("Order cannot be cancelled. Current status: " + status);
            return;
        }

        // Return stock back
        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);
            line.getItem().increaseStock(line.getQuantity());
        }

        status = Status.CANCELLED;
    }

    // Print order lines for MenuManager (without exposing internal list)
    public void printLines() {
        if (lines.isEmpty()) {
            System.out.println("   (empty order)");
            return;
        }
        for (int i = 0; i < lines.size(); i++) {
            System.out.println("   - " + lines.get(i));
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customer=" + (customer == null ? "null" : customer.getName()) +
                ", linesCount=" + lines.size() +
                ", total=" + calculateTotal() +
                ", status=" + status +
                '}';
    }
}
