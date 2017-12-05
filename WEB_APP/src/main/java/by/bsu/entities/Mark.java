package by.bsu.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "marks")
public class Mark {

    @Id
    private String _id;

    @DBRef
    private Product product;

    @DBRef
    private User client;

    private int value;


}
