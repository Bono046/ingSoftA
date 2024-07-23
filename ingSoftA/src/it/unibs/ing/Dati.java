package it.unibs.ing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Dati implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4498646267228832155L;
	private List<Configuratore> configuratori;
    private List<ComprensorioGeografico> comprensori;
    private List<Categoria> gerarchie;
    private List<FattoreDiConversione> fattoriDiConversione;

    public Dati() {
        configuratori = new ArrayList<>();
        comprensori = new ArrayList<>();
        gerarchie = new ArrayList<>();
        fattoriDiConversione = new ArrayList<>();
    }

    public List<Configuratore> getConfiguratori() {
        return configuratori;
    }

    public void setConfiguratori(List<Configuratore> configuratori) {
        this.configuratori = configuratori;
    }

    public List<ComprensorioGeografico> getComprensori() {
        return comprensori;
    }

    public void setComprensori(List<ComprensorioGeografico> comprensori) {
        this.comprensori = comprensori;
    }

    public List<Categoria> getGerarchie() {
        return gerarchie;
    }

    public void setGerarchie(List<Categoria> gerarchie) {
        this.gerarchie = gerarchie;
    }

    public List<FattoreDiConversione> getFattoriDiConversione() {
        return fattoriDiConversione;
    }

    public void setFattoriDiConversione(List<FattoreDiConversione> fattoriDiConversione) {
        this.fattoriDiConversione = fattoriDiConversione;
    }

    public boolean userValido(String user) {
        for (Configuratore conf : configuratori) {
            if (conf.getUsername().equals(user)) {
                return false;
            }
        }
        return true;
    }
}
