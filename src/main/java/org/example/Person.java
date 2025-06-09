package org.example;

/**
 * Klasa abstrakcyjna reprezentujaca osobe w symulacji.
 * Zapewnia atrybuty identyfikacji dla wszystkich klas dziedziczacych.
 */
public abstract class Person {
    /**
     * Unikatowy identyfikator dla osoby
     */
    protected int id;
    /**
     * Imie danej osoby
     */
    protected String name;

    /**
     * Konstruktor instancji new Person
     * @param id unikatowy identyfikator
     * @param name imie danej osoby
     */
    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Zwraca identyfikator danej osoby
     * @return identyfikator
     */
    public int getId() {
        return id;
    }

    /**
     * Zwraca imie danej osoby
     * @return imie
     */
    public String getName() {
        return name;
    }
}
