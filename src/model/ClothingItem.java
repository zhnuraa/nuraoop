package model;

import exception.InvalidInputException;

/**
 * Parent class (Week 4) upgraded to an abstract class (Week 6).
 *
 * NOTE: Validation is done by throwing InvalidInputException instead of printing warnings.
 */
public abstract class ClothingItem {
    // Week 4 guideline: use protected fields in the parent so children can access them if needed
    protected int itemId;
    protected String name;
    protected String size;
    protected double price;
    protected String brand;
    protected int stockQuantity;

    public ClothingItem(int itemId, String name, String size, double price, String brand, int stockQuantity) {
        setItemId(itemId);
        setName(name);
        setSize(size);
        setPrice(price);
        setBrand(brand);
        setStockQuantity(stockQuantity);
    }

    public ClothingItem() {
        // Default safe values
        this.itemId = 0;
        this.name = "Unknown Item";
        this.size = "N/A";
        this.price = 0.0;
        this.brand = "No Brand";
        this.stockQuantity = 0;
    }

    // Getters
    public int getItemId() { return itemId; }
    public String getName() { return name; }
    public String getSize() { return size; }
    public double getPrice() { return price; }
    public String getBrand() { return brand; }
    public int getStockQuantity() { return stockQuantity; }

    // Setters (validation)
    public void setItemId(int itemId) {
        if (itemId < 0) {
            throw new InvalidInputException("Item ID must be >= 0");
        }
        this.itemId = itemId;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Name cannot be empty");
        }
        this.name = name.trim();
    }

    public void setSize(String size) {
        if (size == null || size.trim().isEmpty()) {
            throw new InvalidInputException("Size cannot be empty");
        }
        this.size = size.trim();
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new InvalidInputException("Price must be >= 0");
        }
        this.price = price;
    }

    public void setBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            throw new InvalidInputException("Brand cannot be empty");
        }
        this.brand = brand.trim();
    }

    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity < 0) {
            throw new InvalidInputException("Stock quantity must be >= 0");
        }
        this.stockQuantity = stockQuantity;
    }

    // Week 3 store logic
    public boolean isPremium() {
        return price > 35000.0;
    }

    public boolean isInStock() {
        return stockQuantity > 0;
    }

    public boolean reduceStock(int amount) {
        if (amount <= 0) {
            throw new InvalidInputException("Amount must be positive");
        }
        if (amount > stockQuantity) {
            return false;
        }
        stockQuantity -= amount;
        return true;
    }

    public void increaseStock(int amount) {
        if (amount <= 0) {
            throw new InvalidInputException("Amount must be positive");
        }
        stockQuantity += amount;
    }

    // ===== Week 4 polymorphism (children override these) =====
    // Week 6: at least one abstract method
    public abstract String getType();

    public String getCareInstructions() {
        return "Standard care: wash at 30C, do not bleach.";
    }

    public String getDisplayInfo() {
        return "[" + getType() + "] " +
                "id=" + itemId +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                ", stock=" + stockQuantity;
    }

    @Override
    public String toString() {
        return "ClothingItem{" +
                "itemId=" + itemId +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
}
