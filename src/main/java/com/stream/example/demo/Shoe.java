package com.stream.example.demo;

import java.util.Objects;

public class Shoe {
    private int id;
    private String brand;
    private String size;
    private String color;

    public Shoe(int id, String brand, String size, String color) {
        this.id = id;
        this.brand = brand;
        this.size = size;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shoe car = (Shoe) o;
        return id == car.id &&
                Objects.equals(brand, car.brand) &&
                Objects.equals(size, car.size) &&
                Objects.equals(color, car.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, size, color);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", type='" + size + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
