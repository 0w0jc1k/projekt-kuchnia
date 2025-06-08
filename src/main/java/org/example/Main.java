package org.example;
import java.util.Scanner;

/**
 * Glowne miejsce dostepu do symulacji.
 * Przetwarza dane wprowadzone przez uzytkownika i uruchamia przebieg symulacji.
 */
public class Main {
    /**
     * Glowna metoda, ktora uruchamia symulacje
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Wprowadz liczbe dostepnych kucharzy: ");
        int numberOfCooks = scanner.nextInt();
        if (numberOfCooks <=0) {
            System.out.println("Liczba kucharzy musi byc wieksza niz 0, podaj ponowna liczbe kucharzy: ");
            numberOfCooks = scanner.nextInt();
        }
        System.out.println("Wprowadz liczbe klientow: ");
        int numberOfClients = scanner.nextInt();
        if (numberOfClients <=0) {
            System.out.println("Liczba klientow musi byc wieksza niz 0, podaj ponowna liczbe klientow: ");
            numberOfClients = scanner.nextInt();
        }
        System.out.println("Wprowadz czas trwania symulacji (w minutach): ");
        int simulationDuration = scanner.nextInt();
        if (simulationDuration <=0) {
            System.out.println("Czas trwania symulacji musi byc wiekszy niz 0, podaj ponowny czas trwania symulacji: ");
            simulationDuration = scanner.nextInt();
        }
        scanner.close();

        Configuration config = new Configuration(numberOfCooks,numberOfClients,simulationDuration);
        Simulation simulation = new Simulation(config);
        simulation.initialize(); //inicjalizuje liczbe klientow i kucharzy
        simulation.run(); //uruchamia symulacje
        simulation.saveResults(); //zapisuje wyniki symulacji
    }
}