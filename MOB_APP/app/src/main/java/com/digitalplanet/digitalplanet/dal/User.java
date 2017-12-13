package com.digitalplanet.digitalplanet.dal;

/**
 * Created by marija.savtchouk on 13.12.2017.
 */

public class User {

    private String _id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String address;

    private String phone;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!get_id().equals(user.get_id())) return false;
        return getEmail().equals(user.getEmail());
    }

    @Override
    public int hashCode() {
        int result = get_id().hashCode();
        result = 31 * result + getEmail().hashCode();
        return result;
    }
}
