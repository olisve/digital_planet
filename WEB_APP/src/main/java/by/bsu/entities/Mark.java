package by.bsu.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "marks")
public class Mark {

    private int idProduct;
    private int idClient;
    private int value;

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mark mark1 = (Mark) o;

        if (getIdProduct() != mark1.getIdProduct()) return false;
        if (getIdClient() != mark1.getIdClient()) return false;
        return getValue() == mark1.getValue();
    }

    @Override
    public int hashCode() {
        int result = getIdProduct();
        result = 31 * result + getIdClient();
        result = 31 * result + getValue();
        return result;
    }

    @Override
    public String toString() {
        return "Mark{" +
                "idProduct=" + idProduct +
                ", idClient=" + idClient +
                ", value=" + value +
                '}';
    }
}
