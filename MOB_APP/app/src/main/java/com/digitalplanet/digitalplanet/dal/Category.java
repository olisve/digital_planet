package com.digitalplanet.digitalplanet.dal;

/**
 * Created by marija.savtchouk on 04.12.2017.
 */

public class Category {

    private String _id;

    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (!get_id().equals(category.get_id())) return false;
        return getName().equals(category.getName());
    }

    @Override
    public int hashCode() {
        int result = get_id().hashCode();
        result = 31 * result + getName().hashCode();
        return result;
    }
}
