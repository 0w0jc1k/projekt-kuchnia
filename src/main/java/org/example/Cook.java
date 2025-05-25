package org.example;

public class Cook extends Person {
private boolean isAvailable;

    public Cook(int id, String name) {
        super(id, name);
        this.isAvailable = true; //poczatkowo jest wolny
    }

    public void prepareDish(Dish dish) {

    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
