package it.unibs.ing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class GerarchiaCategorie {
	private Categoria radice;
	private Categoria categoriaCorrente;
    private ArrayList<CategoriaFoglia> listaFoglie = new ArrayList<>();
    private Stack<Categoria> percorso;

    private static ArrayList<GerarchiaCategorie> listaOggettiGerarchia = new ArrayList<>();

    
    
    
	public GerarchiaCategorie(Categoria radice) {
		this.radice = radice;
        this.percorso = new Stack<>();
    }
	
	public Categoria getCategoriaRadice() {
		return this.radice;
	}
	
   public ArrayList<CategoriaFoglia> getListaFoglie() {
		return listaFoglie;
	}

	public void setListaFoglie(ArrayList<CategoriaFoglia> c) {
		
		for(CategoriaFoglia foglia: c)
			this.listaFoglie.add(foglia);
	}
	
	public void addToListaFoglie(CategoriaFoglia c) {
		this.listaFoglie.add(c);
	}
	


    public Categoria getCategoriaCorrente() {
        return categoriaCorrente;
    }
    
    
    public void setCategoriaCorrente() { 	//inizializzazione della categoriaCorrente: serve per non salvare due volte la radice nel file
    		categoriaCorrente = radice;
    }
    
    
    // Metodo per spostarsi in una sottocategoria
    public boolean vaiASottocategoria(Categoria sottocategoria) {
        if (categoriaCorrente.getSottocategorie() != null &&
            categoriaCorrente.getSottocategorie().containsValue(sottocategoria)) {
            percorso.push(categoriaCorrente);
            categoriaCorrente = sottocategoria;
            return true;
        }
        return false; // Sottocategoria non trovata
    }
    
    
    public Stack<Categoria> getPercorso() {
		return percorso;
	}

	// Metodo per tornare alla categoria superiore
    public boolean tornaIndietro() {
        if (!percorso.isEmpty()) {
            categoriaCorrente = percorso.pop();
            return true;
        }
        return false; // Non ci sono categorie superiori
    }

	public static ArrayList<GerarchiaCategorie> getListaOggettiGerarchia() {
		return listaOggettiGerarchia;
	}


	public static void setListaOggettiGerarchia(ArrayList<GerarchiaCategorie> gerarchie) {
		GerarchiaCategorie.listaOggettiGerarchia = gerarchie;
	}
	
	public static void addGerarchia(GerarchiaCategorie g) {
		GerarchiaCategorie.listaOggettiGerarchia.add(g);
	}
    
    public static ArrayList<Categoria> getListaRadici() {
    	ArrayList<Categoria> listaRadici = new ArrayList<>(); 
    	
    	for(GerarchiaCategorie c : listaOggettiGerarchia) {
    		listaRadici.add(c.getCategoriaRadice());
    	}
    	return listaRadici;
    }
    
    public CategoriaFoglia getFogliaDaNome(String nome) {
    	for(CategoriaFoglia c: listaFoglie) {
    		if(c.getNome().equals(nome))
    			return c;
    	}
    	return null;
    }
    
    public static boolean checkNomeGerarchia(String nome) {
    	for(Categoria c : getListaRadici()) {
    		if(c.getNome().equals(nome))
    			return false;
    	}
    	return true;
    }
    
    
    
}
