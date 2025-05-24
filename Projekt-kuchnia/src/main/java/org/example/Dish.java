package org.example;

public class Dish {
    private String name;
    private int preparationTime;

    public Dish(String name, int preparationTime) {
        this.name = name;
        this.preparationTime = preparationTime;
    }

    public String getName() {
        return name;
    }

    public int getPreparationTime() {
        return preparationTime;
    }
}
