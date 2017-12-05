package by.bsu.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "orders")
public class _Order {

    @Id
    private String _id;

    @DBRef
    private User client;

    private String shippingType;

    private String status;

    @DBRef
    private List<Product> productList;

}
