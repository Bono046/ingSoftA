package it.unibs.ing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Dati implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4498646267228832155L;
	private ArrayList<Configuratore> configuratori;
    private ArrayList<ComprensorioGeografico> comprensori;
    private ArrayList<Categoria> gerarchie;
    private ArrayList<FattoreDiConversione> fattoriDiConversione;

    public Dati() {
        configuratori = new ArrayList<>();
        comprensori = new ArrayList<>();
        gerarchie = new ArrayList<>();
        fattoriDiConversione = new ArrayList<>();
    }

    public ArrayList<Configuratore> getConfiguratori() {
        return configuratori;
    }

    public void setConfiguratori(ArrayList<Configuratore> configuratori) {
        this.configuratori = configuratori;
    }

    public ArrayList<ComprensorioGeografico> getComprensori() {
        return comprensori;
    }

    public void setComprensori(ArrayList<ComprensorioGeografico> comprensori) {
        this.comprensori = comprensori;
    }
    
    public void addComprensorio(ComprensorioGeografico comprensorio) {
        this.comprensori.add(comprensorio);
    }
    

    public ArrayList<Categoria> getGerarchie() {
        return gerarchie;
    }

    public void setGerarchie(ArrayList<Categoria> gerarchie) {
        this.gerarchie = gerarchie;
    }

    public ArrayList<FattoreDiConversione> getFattoriDiConversione() {
        return fattoriDiConversione;
    }

    public void setFattoriDiConversione(ArrayList<FattoreDiConversione> fattoriDiConversione) {
        this.fattoriDiConversione = fattoriDiConversione;
    }



	@Override
	public String toString() {
		return "Dati [configuratori=" + configuratori + ", comprensori=" + comprensori + ", gerarchie=" + gerarchie
				+ ", fattoriDiConversione=" + fattoriDiConversione + "]";
	}
}
