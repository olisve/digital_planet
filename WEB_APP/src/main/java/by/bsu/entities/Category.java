package by.bsu.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "categories")
public class Category {

    @Id
    private String _id;

    private String name;

    private String requestName;

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

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (!get_id().equals(category.get_id())) return false;
        if (!getName().equals(category.getName())) return false;
        return getRequestName().equals(category.getRequestName());
    }

    @Override
    public int hashCode() {
        int result = get_id().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getRequestName().hashCode();
        return result;
    }
}
