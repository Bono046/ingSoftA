package it.unibs.ing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class GerarchiaCategorie {
	private Categoria categoriaCorrente;
    private Stack<Categoria> percorso;
    private ArrayList<CategoriaFoglia> listaFoglie = new ArrayList<>();
 

	public GerarchiaCategorie(Categoria radice) {
        this.categoriaCorrente = radice;
        this.percorso = new Stack<>();
    }
	
	
   public ArrayList<CategoriaFoglia> getListaFoglie() {
		return listaFoglie;
	}

	public void setListaFoglie(ArrayList<CategoriaFoglia> c) {
		this.listaFoglie = c;
	}
	
	public void addToListaFoglie(CategoriaFoglia c) {
		this.listaFoglie.add(c);
	}
	
	

    public Categoria getCategoriaCorrente() {
        return categoriaCorrente;
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
}
