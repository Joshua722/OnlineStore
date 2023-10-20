package com.pluralsight;

public class Product {
    private String SKU;
    private String name;
    private double price;
    private String department;
    public Product(String SKU, String name, double productPrice, String department) {
        this.SKU = SKU;
        this.name = name;
        this.price = productPrice;
        this.department = department;
    }
    public String getSKU() {
        return this.SKU;
    }
    public String getName() {
        return this.name;
    }
    public double getPrice() {
        return this.price;
    }
    public String getDepartment() {
        return this.department;
    }
    @Override
    public String toString() {
        return this.getSKU() + "|" + this.getName() + "|" + this.getPrice() + "|" + this.getDepartment();
    }
}

