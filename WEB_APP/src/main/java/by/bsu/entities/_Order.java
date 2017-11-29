package by.bsu.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class _Order {

    private int idClient;
    private String shippingType;
    private String status;

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        _Order order = (_Order) o;

        if (getIdClient() != order.getIdClient()) return false;
        if (!getShippingType().equals(order.getShippingType())) return false;
        return getStatus().equals(order.getStatus());
    }

    @Override
    public int hashCode() {
        int result = getIdClient();
        result = 31 * result + getShippingType().hashCode();
        result = 31 * result + getStatus().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "_Order{" +
                "idClient=" + idClient +
                ", shippingType='" + shippingType + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
