package kg.askaromurkanov.appliancestoreandroid.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Product implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String model;
    private String factory;
    private String category;
    private double price;
    private int discount;
    private int quantity;
    private int image;
    private String description;
    private double rating;
    private String add_date;
    private int sales;


    public Product(String name, String model, String factory, String category, double price, int discount, int quantity, int image, String description, double rating, String add_date, int sales) {
        this.name = name;
        this.model = model;
        this.factory = factory;
        this.category = category;
        this.price = price;
        this.discount = discount;
        this.quantity = quantity;
        this.image = image;
        this.description = description;
        this.rating = rating;
        this.add_date = add_date;
        this.sales = sales;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getAdd_date() {
        return add_date;
    }

    public void setAdd_date(String add_date) {
        this.add_date = add_date;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }
}