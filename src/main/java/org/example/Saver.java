package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Zajmuje sie zapisywaniem wynikow symulacji.
 * Zapisuje dane klientow i statystyki oraz srednia ocene koncowa wyniku symulacji.
 */
public class Saver {
    /**
     * Zapisuje kompletne wyniki symulacji
     * @param allClients dane wszystkich klientow restauracji w przebiegu symulacji
     */
    public void saveSimulationResults(List<Client> allClients) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("wyniksymulacji.txt"))) {
            int totalRating =0;
            int ratedCount = 0;

            writer.write("=== Obsłużeni klienci ===");
            writer.newLine();
            writer.write("id,name,dish,status,satisfaction,actualWaitTime");
            writer.newLine();
            for (Client c : allClients) {
                if (c.getStatus() == ClientStatus.SERVED) {
                    totalRating += c.getSatisfactionRating();
                    ratedCount++;
                    String dish = c.getOrder() != null ? c.getOrder().getDish().getName() : "-";
                    writer.write(
                            c.getId() + "," +
                                    c.getName() + "," +
                                    dish + "," +
                                    c.getStatus() + "," +
                                    c.getSatisfactionRating() + "," +
                                    c.getActualWaitTime()
                    );
                    writer.newLine();
                }
            }
            writer.newLine();
            writer.write("=== Nieobsłużeni klienci ===");
            writer.newLine();
            writer.write("id,name,dish,status,satisfaction,actualWaitTime");
            writer.newLine();
            for (Client c : allClients) {
                if (c.getStatus() != ClientStatus.SERVED) {
                    String dish = c.getOrder() != null ? c.getOrder().getDish().getName() : "-";
                    writer.write(
                            c.getId() + "," +
                                    c.getName() + "," +
                                    dish + "," +
                                    c.getStatus() + "," +
                                    "-" + "," +
                                    c.getActualWaitTime()
                    );
                    writer.newLine();
                }
            }
            writer.newLine();
            writer.write("==== Statystyki ====");
            writer.newLine();

            if (ratedCount > 0) {
                double avgRating = (double) totalRating / ratedCount;
                writer.write("Średnia ocena obsłużonych klientów: " + String.format("%.2f", avgRating));
            } else {
                writer.write("Brak obsłużonych klientów.");
            }

            writer.newLine();
            System.out.println();
            System.out.println("**Wyniki symulacji zostały zapisane do pliku.**");
        } catch (IOException e) {
            System.err.println("Błąd zapisu wyników symulacji: " + e.getMessage());
        }
    }
}