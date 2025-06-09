package org.example;

/**Reprezentuje danie z konkretnymi parametrami, jak czas przygotowania i nazwa
 */
public class Dish {
    /**
     * Nazwa dania
     */
    private String name;
    /**
     * Czas przygotowania dania w minutach
     */
    private int preparationTime;

    /**
     * Konstrukt do tworzenia nowej instancji new Dish
     * @param name nazwa dania
     * @param preparationTime czas przygotowania dania w minutach
     */
    public Dish(String name, int preparationTime) {
        this.name = name;
        this.preparationTime = preparationTime;
    }

    /**
     * Zwraca nazwe konkretnego dania
     * @return nazwa dania
     */
    public String getName() {
        return name;
    }

    /**
     * Zwraca czas przygotowania konkretnego dania
     * @return czas przygotowania dania w minutach
     */
    public int getPreparationTime() {
        return preparationTime;
    }
}
