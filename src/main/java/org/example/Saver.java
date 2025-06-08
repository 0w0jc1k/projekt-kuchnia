package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Saver {
    public void saveSimulationResults(List<Client> clients) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("wyniksymulacji.txt"))) {
            writer.write("=== Obsłużeni klienci ===");
            writer.newLine();
            writer.write("id,name,dish,status,satisfaction,actualWaitTime");
            writer.newLine();
            for (Client c : clients) {
                if (c.getStatus() == ClientStatus.SERVED) {
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
            for (Client c : clients) {
                if (c.getStatus() != ClientStatus.SERVED) {
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
            System.out.println("Wyniki symulacji zostały zapisane do pliku.");
        } catch (IOException e) {
            System.err.println("Błąd zapisu wyników symulacji: " + e.getMessage());
        }
    }
}
