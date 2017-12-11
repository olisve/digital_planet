package com.digitalplanet.digitalplanet.dal;

/**
 * Created by marija.savtchouk on 11.12.2017.
 */

public class BasketItem {

    private String _id;
    private int count;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
