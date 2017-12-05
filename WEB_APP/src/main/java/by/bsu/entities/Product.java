package by.bsu.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "products")
public class Product {

    @Id
    private String _id;

    private String name;

    private String brand;

    private Category category;

    private String description;

    private String imagePath;

    private int price;

    private Map<String, String> characteristics;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Map<String, String> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(Map<String, String> characteristics) {
        this.characteristics = characteristics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (getPrice() != product.getPrice()) return false;
        if (!get_id().equals(product.get_id())) return false;
        if (!getName().equals(product.getName())) return false;
        if (!getBrand().equals(product.getBrand())) return false;
        if (!getCategory().equals(product.getCategory())) return false;
        if (!getDescription().equals(product.getDescription())) return false;
        if (!getImagePath().equals(product.getImagePath())) return false;
        return getCharacteristics().equals(product.getCharacteristics());
    }

    @Override
    public int hashCode() {
        int result = get_id().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getBrand().hashCode();
        result = 31 * result + getCategory().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + getImagePath().hashCode();
        result = 31 * result + getPrice();
        result = 31 * result + getCharacteristics().hashCode();
        return result;
    }
}
