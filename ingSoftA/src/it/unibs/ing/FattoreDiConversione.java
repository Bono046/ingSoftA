package it.unibs.ing;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FattoreDiConversione implements Serializable {
    private Map<String, Map<String, Double>> fattoriConversione;

    public FattoreDiConversione() {
        this.fattoriConversione = new HashMap<>();
    }

    public void aggiungiFattore(String categoriaOrigine, String categoriaDestinazione, double fattore) {
        if (fattore < 0.5 || fattore > 2.0) {
            throw new IllegalArgumentException("Il fattore di conversione deve essere compreso tra 0.5 e 2.0");
        }

        fattoriConversione.putIfAbsent(categoriaOrigine, new HashMap<>());
        fattoriConversione.putIfAbsent(categoriaDestinazione, new HashMap<>());

        fattoriConversione.get(categoriaOrigine).put(categoriaDestinazione, fattore);
        fattoriConversione.get(categoriaDestinazione).put(categoriaOrigine, 1 / fattore);

        aggiornaFattori(categoriaOrigine, categoriaDestinazione);
    }

    private void aggiornaFattori(String c1, String c2) {
        for (String c3 : fattoriConversione.keySet()) {
            if (fattoriConversione.get(c2).containsKey(c3)) {
                double f12 = fattoriConversione.get(c1).get(c2);
                double f23 = fattoriConversione.get(c2).get(c3);
                double f13 = f12 * f23;
                fattoriConversione.get(c1).put(c3, f13);
                fattoriConversione.get(c3).put(c1, 1 / f13);
            }
        }
    }

    public double getFattore(String categoriaOrigine, String categoriaDestinazione) {
        return fattoriConversione.getOrDefault(categoriaOrigine, new HashMap<>()).getOrDefault(categoriaDestinazione, -1.0);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String origine : fattoriConversione.keySet()) {
            for (String destinazione : fattoriConversione.get(origine).keySet()) {
                sb.append(origine).append(" -> ").append(destinazione).append(" : ").append(fattoriConversione.get(origine).get(destinazione)).append("\n");
            }
        }
        return sb.toString();
    }
}
