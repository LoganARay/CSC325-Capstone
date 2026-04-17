package edu.farmingdale.csc325capstone.model;

import java.util.Map;

public class Part {

    private String id;
    private String name;
    private String category;
    private String brand;
    private double price;
    private String link;
    private Integer year; // can be null
    private Map<String, Object> specs;

    //No-arg constructor (for Jackson)
    public Part() {}

    //Full constructor
    public Part(String id, String name, String category, String brand,
                double price, String link, Integer year, Map<String, Object> specs) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.price = price;
        this.link = link;
        this.year = year;
        this.specs = specs;
    }

    //  Getters & Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
    // For getting the whole list of specs
    public Map<String, Object> getSpecs() {
        return specs;
    }
    // For getting just a single value inside specs
    public String getSpec(String key) {
        if (specs == null) return null;

        Object value = specs.get(key);

        return value != null ? value.toString() : null;
    }

    public void setSpecs(Map<String, Object> specs) {
        this.specs = specs;
    }

    //For debugging purposes
    @Override
    public String toString() {
        return "Part{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", link='" + link + '\'' +
                ", year=" + year +
                ", specs=" + specs +
                '}';
    }
    public double getSpecAsDouble(String key, double defaultValue) {
        if (specs == null) return defaultValue;
        Object val = specs.get(key);
        if (val instanceof Number) return ((Number) val).doubleValue();
        if (val instanceof String) {
            try { return Double.parseDouble((String) val); } catch (NumberFormatException e) {}
        }
        return defaultValue;
    }
}