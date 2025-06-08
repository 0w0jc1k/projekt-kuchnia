package org.example;

/**
 * Enum stanow, w ktorych moze znalezc sie klient w trakcie trwania symulacji.
 * Kontroluje maszyne stanow klienta od przyjscia do kuchni do momentu jej opuszczenia
 */
public enum ClientStatus {
    /**
     * Klient oczekuje na zamowienie
     */
    WAITING,
    /**
     * Klient niecierpliwi sie po uplywie 15 minut
     */
    IMPATIENT,
    /**
     * Klient zostal obsluzony
     */
    SERVED,
    /**
     * Po uplywie 30 minut klient nie zostal obsluzony i opuscil restauracje
     */
    LEFT
}
