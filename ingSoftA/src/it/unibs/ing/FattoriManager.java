package it.unibs.ing;

import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class FattoriManager {

    private class Coppia {
        private final CategoriaFoglia foglia1;
        private final CategoriaFoglia foglia2;

        public Coppia(CategoriaFoglia prima, CategoriaFoglia seconda) {
            this.foglia1 = prima;
            this.foglia2 = seconda;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coppia pair = (Coppia) o;
            return foglia1.equals(pair.foglia1) && foglia2.equals(pair.foglia2);
        }

        @Override
        public int hashCode() {
            return Objects.hash(foglia1, foglia2);
        }
    }

    private HashMap<Coppia, Double> fattoriConversione;

    public FattoriManager() {
        this.fattoriConversione = new HashMap<>();
    }

    public void setFattoreConversione(CategoriaFoglia c1, CategoriaFoglia c2, double factor) {
        if (factor < 0.5 || factor > 2.0) {
            throw new IllegalArgumentException("Il fattore deve essere compreso tra 0.5 e 2.0");
        }
        Coppia pair = new Coppia(c1, c2);
        fattoriConversione.put(pair, factor);
        Coppia inversePair = new Coppia(c2, c1);
        fattoriConversione.put(inversePair, 1 / factor);
    }

    public double getFattoreConversione(CategoriaFoglia c1, CategoriaFoglia c2) {
        Coppia pair = new Coppia(c1, c2);
        if (fattoriConversione.containsKey(pair)) {
            return fattoriConversione.get(pair);
        }
        for (Coppia key : fattoriConversione.keySet()) {
            if (key.foglia1.equals(c1)) {
                CategoriaFoglia intermediate = key.foglia2;
                double f12 = fattoriConversione.get(key);
                double f23 = getFattoreConversione(intermediate, c2);
                if (f23 != -1) {
                    double f13 = f12 * f23;
                    setFattoreConversione(c1, c2, f13);
                    return f13;
                }
            }
        }
        return -1;
    }

    
}
