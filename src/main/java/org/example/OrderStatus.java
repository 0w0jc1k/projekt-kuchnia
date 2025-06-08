package org.example;

/**
 * Enum mozliwych stanow zamowionego dania, zalezne od czasu.
 * Kontroluje maszyne stanow od momentu zamowienia do dostarczenia dania.
 */
public enum OrderStatus {
    /**
     * Zamowienie zlozone ale jeszcze nie rozpoczete
     */
    PENDING,
    /**
     * Kucharz zaczyna przygotowywac zamowienie
     */
    IN_PROGRESS,
    /**
     * Zamowienie jest gotowe
     */
    READY,
    /**
     * Zamowienie zostalo usuniete, gdy klient ktory je zamowil
     * opuscil restauracje bez jego odbioru.
     */
    CANCELLED
}
