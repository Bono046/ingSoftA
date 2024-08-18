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
    private ArrayList<FattoreConversione> fattoriDiConversione;
    private ArrayList<GerarchiaCategorie> gerarchie;
    private ArrayList<Fruitore> fruitori;

    public Dati() {
        configuratori = new ArrayList<>();
        comprensori = new ArrayList<>();
        fattoriDiConversione = new ArrayList<>();
        gerarchie = new ArrayList<>();
        fruitori = new ArrayList<>();
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
    
    public ArrayList<FattoreConversione> getFattoriDiConversione() {
        return fattoriDiConversione;
    }

    public void setFattoriDiConversione(ArrayList<FattoreConversione> fattoriDiConversione) {
        this.fattoriDiConversione = fattoriDiConversione;
    }


	public ArrayList<GerarchiaCategorie> getGerarchie() {
		return gerarchie;
	}

	public void setGerarchie(ArrayList<GerarchiaCategorie> gerarchie) {
		this.gerarchie = gerarchie;
	}

	public ArrayList<Fruitore> getFruitori() {
		return fruitori;
	}

	public void setFruitori(ArrayList<Fruitore> fruitori) {
		this.fruitori = fruitori;
	}

	@Override
	public String toString() {
		return "Dati [configuratori=" + configuratori + ", comprensori=" + comprensori + ", fattoriDiConversione="
				+ fattoriDiConversione + ", gerarchie=" + gerarchie + ", fruitori=" + fruitori + "]";
	}

}