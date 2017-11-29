package by.bsu.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "order_products")
public class OrderProducts {

    private int idOrder;
    private int idProduct;

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderProducts that = (OrderProducts) o;

        if (getIdOrder() != that.getIdOrder()) return false;
        return getIdProduct() == that.getIdProduct();
    }

    @Override
    public int hashCode() {
        int result = getIdOrder();
        result = 31 * result + getIdProduct();
        return result;
    }

    @Override
    public String toString() {
        return "OrderProducts{" +
                "idOrder=" + idOrder +
                ", idProduct=" + idProduct +
                '}';
    }
}
