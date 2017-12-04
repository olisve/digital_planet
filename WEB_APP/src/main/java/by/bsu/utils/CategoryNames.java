package by.bsu.utils;

public enum  CategoryNames {

    MOBILE("Mobile phones"),
    TABLET("Tablets"),
    LAPTOP("Laptops"),
    TV("Tv"),
    CAMERA("Cameras"),
    ACCESSORY("Accessories");

    String name;

    CategoryNames(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
